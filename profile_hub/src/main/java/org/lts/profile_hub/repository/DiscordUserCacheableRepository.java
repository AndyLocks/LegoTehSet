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
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/// Manages user data ([DiscordUser]) in the database and also caches data
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

    /// Finds a user, otherwise creates a new one and returns it.
    ///
    /// Also caches results from database.
    ///
    /// @param discordId discord user id
    /// @return never null
    public DiscordUser findOrCreate(String discordId) {
        LOG.debug("Searching for user [{}]", discordId);

        var key = DiscordUserTemplate.USER_KEY_GENERATOR.apply(discordId);
        LOG.debug("Redis key: {}", key);

        var cache = userRedisTemplate.opsForValue().get(key);
        LOG.debug("Cache was loaded [{}] {}", discordId, cache);

        if (cache != null)
            return cache.toJpa();

        LOG.debug("Cache was not found [{}]", discordId);

        var user = userJpaRepository.findOne(DiscordUser.exampleWithDiscordId(discordId));

        LOG.debug("User from database [{}]: {}", discordId, user.orElse(null));

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

    /// Finds a user.
    ///
    /// Also caches results from database.
    ///
    /// @param discordId discord user id
    /// @return null if user was not found
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

        LOG.debug("User from database [{}]: {}", discordId, user.orElse(null));

        user.ifPresent(u -> userRedisTemplate.opsForValue().set(key, DiscordUserDto.fromJpa(u), DiscordUserTemplate.TTL_DURATION));

        return user.orElse(null);
    }

    /// Returns an object for interacting with the `set`.
    ///
    /// Through this object you can work with the `set` by performing operations on it
    /// and adding it to the favorites of different users.
    ///
    /// ### Example
    ///
    /// ```java
    /// discordUserCacheableRepository.addFavourite(set).forUsers("123", "321");
    /// ```
    ///
    /// @param set must not be null
    /// @throws IllegalArgumentException if `set` is null
    public FavouriteSetAction addFavourite(Set set) {
        Assert.notNull(set, "Set cannot be null");

        return new FavouriteSetAction(set, this);
    }

    public void addFavourite(Set set, String discordId) {
        LOG.debug("Adding new set [{}] by Discord id [{}]", set, discordId);

        var user = findOrCreate(discordId);
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

    /// @return an empty unmodifiable list if user was not find.
    public List<Set> getFavourites(String discordId) {
        var user = find(discordId);

        return user == null ? List.of() : user.getFavouriteSets();
    }

    /// The `FavouriteSetAction` class provides an interface for performing operations on a
    /// set of favourites (`Set`) associated with a user
    /// in a specified repository.
    ///
    /// The primary purpose of the class is to add a set to the favourites list
    /// of one or more users identified by `discordId`.
    ///
    /// @param set        the set of favorites to operate on
    /// @param repository the repository for managing user data
    public record FavouriteSetAction(Set set, DiscordUserCacheableRepository repository) {

        /// Adds the specified set to one user's favorites.
        ///
        /// @param discordId unique user id in Discord
        public void forUser(String discordId) {
            repository.addFavourite(set, discordId);
        }

        /// Adds the specified set to favorites for multiple users.
        ///
        /// @param discordIds array of user ids in discord
        public void forUsers(String... discordIds) {
            for (var id : discordIds) {
                repository.addFavourite(set, id);
            }
        }

        /// Adds the specified set to favorites for multiple users, using {@link Iterable}.
        ///
        /// @param discordIds a `Iterable` of user ids in Discord
        public void forUsers(final Iterable<String> discordIds) {
            discordIds.forEach(id -> repository.addFavourite(set, id));
        }

    }

}
