package com.seouldata.auth.domain.auth.repository;

import com.seouldata.auth.domain.auth.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<Member, Long> {

    Optional<Object> findByEmail(String email);

    Optional<Object> findByNickname(String nickname);

    Boolean existsByNickname(String nickname);

}
