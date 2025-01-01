package org.lts.profile_hub.repository.jpa;

import org.lts.profile_hub.entity.jpa.DiscordUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DiscordUserJpaRepository extends JpaRepository<DiscordUser, UUID> {
}
