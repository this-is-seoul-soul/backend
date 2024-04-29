package com.seouldata.auth.domain.badge.entity;

import com.seouldata.auth.domain.auth.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "own")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Own {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "own_seq")
    private Long ownSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mem_seq")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "badge_seq")
    private Badge badge;

}
