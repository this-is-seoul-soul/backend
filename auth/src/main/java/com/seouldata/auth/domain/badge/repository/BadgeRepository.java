package com.seouldata.auth.domain.badge.repository;

import com.seouldata.auth.domain.badge.entity.Badge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BadgeRepository extends JpaRepository<Badge, Long> {
}
