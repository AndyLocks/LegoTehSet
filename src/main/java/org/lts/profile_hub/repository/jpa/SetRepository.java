package org.lts.profile_hub.repository.jpa;

import org.lts.profile_hub.entity.jpa.Set;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SetRepository extends JpaRepository<Set, UUID> {
}
