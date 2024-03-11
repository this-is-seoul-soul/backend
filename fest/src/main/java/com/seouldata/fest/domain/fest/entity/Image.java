package com.seouldata.fest.domain.fest.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "image")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "img_seq")
    private Long imgSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_seq", nullable = false)
    private Review review;

    @Column(name = "img_url", length = 200)
    @NotNull
    private String imgUrl;

    @Column(name = "is_deleted", columnDefinition = "tinyint(1)")
    @NotNull
    @ColumnDefault("0")
    private boolean isDeleted;

    @Builder
    public Image(Review review, String imgUrl, boolean isDeleted) {
        this.review = review;
        this.imgUrl = imgUrl;
        this.isDeleted = isDeleted;
    }

}
