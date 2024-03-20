package com.seouldata.fest.domain.heart.entity;

import com.seouldata.fest.domain.fest.entity.Fest;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "heart")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Heart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "heart_seq")
    private Long heartSeq;

    @Column(name = "mem_seq")
    @NotNull
    private Long memSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fest_seq", nullable = false)
    private Fest fest;

    @Builder
    public Heart(Long memSeq, Fest fest) {
        this.memSeq = memSeq;
        this.fest = fest;
    }

}
