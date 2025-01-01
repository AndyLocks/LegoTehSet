package org.lts.profile_hub.repository;

import org.lts.profile_hub.configuration.redis.templates.DiscordUserTemplate;
import org.lts.profile_hub.entity.jpa.DiscordUser;
import org.lts.profile_hub.entity.jpa.Set;
import org.lts.profile_hub.entity.dto.DiscordUserDto;
import org.lts.profile_hub.repository.jpa.DiscordUserJpaRepository;
import org.lts.profile_hub.repository.jpa.SetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class DiscordUserCacheableRepository {

    private final RedisTemplate<String, DiscordUserDto> userRedisTemplate;
    private final DiscordUserJpaRepository userJpaRepository;
    private final SetRepository setRepository;
    private final Logger LOG = LoggerFactory.getLogger(DiscordUserCacheableRepository.class);

    @Autowired
    public DiscordUserCacheableRepository(RedisTemplate<String, DiscordUserDto> userRedisTemplate, DiscordUserJpaRepository userJpaRepository, SetRepository setRepository) {
        this.userRedisTemplate = userRedisTemplate;
        this.userJpaRepository = userJpaRepository;
        this.setRepository = setRepository;
    }

    public DiscordUser find(String discordId) {
        LOG.debug("Searching for user [{}]", discordId);

        var key = DiscordUserTemplate.USER_KEY_GENERATOR.apply(discordId);
        LOG.debug("Redis key: {}", key);

        var cache = userRedisTemplate.opsForValue().get(key);
        LOG.debug("Cache was loaded [{}] {}", discordId, cache);

        if (cache != null)
            return cache.toJpa();

        LOG.debug("Cache was not found [{}]", discordId);

        var user = userJpaRepository.findOne(DiscordUser.exampleWithDiscordId(discordId));

        LOG.debug("User from database [{}]: {}", discordId, user);

        if (user.isEmpty()) {
            LOG.debug("User was not found [{}]. Creating a new one...", discordId);

            var savedUser = userJpaRepository.save(DiscordUser.builder()
                    .discordId(discordId)
                    .build());

            LOG.debug("New user was created [{}]: {}", discordId, savedUser);

            userRedisTemplate.opsForValue().set(key, DiscordUserDto.fromJpa(savedUser), DiscordUserTemplate.TTL_DURATION);
            LOG.debug("User was cached [{}]: {}", discordId, savedUser);

            return savedUser;
        }

        return user.get();
    }

    public AddUserAction addFavourite(Set set) {
        return new AddUserAction(set, this);
    }

    public void addFavourite(Set set, String discordId) {
        LOG.debug("Adding new set [{}] by Discord id [{}]", set, discordId);

        var user = find(discordId);
        var key = DiscordUserTemplate.USER_KEY_GENERATOR.apply(discordId);

        LOG.debug("User: {}", user);
        LOG.debug("Redis key: {}", key);

        if (user == null) return;

        if (user.getFavouriteSets() == null)
            user.setFavouriteSets(new ArrayList<>());

        if (user.getFavouriteSets().stream()
                .anyMatch(set1 -> set1.getNumber().equals(set.getNumber()))) {
            return;
        }

        setRepository.save(set);
        LOG.debug("Set was saved to database with [{}]: {}", setRepository, set);

        user.getFavouriteSets().add(set);

        userJpaRepository.save(user);
        LOG.debug("User was saved to database with [{}]: {}", userJpaRepository, user);

        userRedisTemplate.opsForValue().set(key, DiscordUserDto.fromJpa(user), DiscordUserTemplate.TTL_DURATION);
        LOG.debug("User was cached with [{}]: {}", userRedisTemplate, user);
    }

    /// @return empty list ({@link List#of()}) if user was not find.
    public List<Set> getFavourites(String discordId) {
        LOG.debug("Searching for favourite sets with id [{}]", discordId);

        var key = DiscordUserTemplate.USER_KEY_GENERATOR.apply(discordId);
        LOG.debug("Redis key: {}", key);

        var cache = userRedisTemplate.opsForValue().get(key);
        LOG.debug("Cache was loaded [{}] {}", discordId, cache);

        if (cache != null)
            return cache.toJpa().getFavouriteSets();

        LOG.debug("Cache was not found [{}]", discordId);

        var user = userJpaRepository.findOne(DiscordUser.exampleWithDiscordId(discordId));

        LOG.debug("User from database [{}]: {}", discordId, user);

        user.ifPresent(u -> userRedisTemplate.opsForValue()
                .set(key, DiscordUserDto.fromJpa(u), DiscordUserTemplate.TTL_DURATION));

        user.ifPresent(u -> LOG.debug("User was cached [{}]: {}", discordId, u));

        return user.isEmpty() ? List.of() : user.get().getFavouriteSets();
    }

    public record AddUserAction(Set set, DiscordUserCacheableRepository repository) {

        public void forUser(String discordId) {
            repository.addFavourite(set, discordId);
        }

    }

}
