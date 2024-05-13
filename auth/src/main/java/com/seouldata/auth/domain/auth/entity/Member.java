package com.seouldata.auth.domain.auth.entity;

import com.seouldata.auth.domain.badge.entity.Badge;
import com.seouldata.auth.domain.badge.entity.Own;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

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
    private String email;

    @Column(length = 20)
    private String nickname;

    @Column(length = 200)
    private String image;

    @Column(length = 100, name = "google_id")
    private String googleId;

    @Column(length = 4)
    private String mbti;

    @Column(length = 165)
    private String token;

    @Column(columnDefinition = "tinyint(1)")
    @ColumnDefault("false")
    @NotNull
    private Boolean notification;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "badge_seq")
    private Badge badgeSeq;

    @OneToMany(mappedBy = "member")
    private List<Own> owns;

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

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setMbti(String mbti) {
        this.mbti = mbti;
    }

    public void setBadgeSeq(Badge badgeSeq) {
        this.badgeSeq = badgeSeq;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
