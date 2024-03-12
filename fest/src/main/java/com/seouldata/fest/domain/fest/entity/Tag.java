package com.seouldata.fest.domain.fest.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "tag")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_seq")
    private Long tagSeq;

    @Column(name = "mem_seq")
    @NotNull
    private Long memSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_seq", nullable = false)
    private Review review;

    @Column(name = "tag_no", columnDefinition = "tinyint(4)")
    @NotNull
    private int tagNo;

    @Column(name = "is_deleted", columnDefinition = "tinyint(1)")
    @NotNull
    @ColumnDefault("0")
    private boolean isDeleted;

    @Builder
    public Tag(Long memSeq, Review review, int tagNo, boolean isDeleted) {
        this.memSeq = memSeq;
        this.review = review;
        this.tagNo = tagNo;
        this.isDeleted = isDeleted;
    }

}
