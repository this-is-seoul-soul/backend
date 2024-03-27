package com.seouldata.fest.domain.review.entity;

import com.seouldata.fest.domain.fest.entity.Fest;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "review")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_seq")
    private Long reviewSeq;

    @Column(name = "mem_seq")
    @NotNull
    private Long memSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fest_seq", nullable = false)
    private Fest fest;

    @Column(columnDefinition = "smallint(6)")
    @NotNull
    @Max(5)
    private int point;

    @Column(length = 150)
    @NotNull
    private String content;

    @Column(name = "is_deleted", columnDefinition = "tinyint(1)")
    @NotNull
    @ColumnDefault("0")
    private boolean isDeleted;

    @Builder
    public Review(Long memSeq, Fest fest, int point, String content, boolean isDeleted) {
        this.memSeq = memSeq;
        this.fest = fest;
        this.point = point;
        this.content = content;
        this.isDeleted = isDeleted;
    }

    public void modify(String content, int point) {
        this.content = content;
        this.point = point;
    }

}
