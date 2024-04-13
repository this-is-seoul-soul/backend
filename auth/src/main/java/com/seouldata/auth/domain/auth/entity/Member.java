package com.seouldata.auth.domain.auth.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mem_seq")
    private Long memSeq;

    @Column(length = 100)
    @NotNull
    private String email;

    @Column(length = 20)
    @NotNull
    private String nickname;

    @Column(length = 200)
    private String image;

    @Column(length = 30, name = "google_id")
    private String googleId;

    @Column(length = 4)
    private String mbti;

    @Column(length = 165)
    private String token;

    @Column(columnDefinition = "tinyint(1)")
    @ColumnDefault("false")
    @NotNull
    private Boolean notification;

    @Builder
    public Member(String email, String nickname, String image, String googleId, String mbti, String token, Boolean notification) {
        this.email = email;
        this.nickname = nickname;
        this.image = image;
        this.googleId = googleId;
        this.mbti = mbti;
        this.token = token;
        this.notification = notification;
    }

}