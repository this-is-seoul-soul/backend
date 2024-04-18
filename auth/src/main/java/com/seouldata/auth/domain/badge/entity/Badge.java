package com.seouldata.auth.domain.badge.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "badge")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Badge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "badge_seq")
    private Long badgeSeq;

    @Column(name = "title")
    @Length(max = 20)
    private String title;

    @Column(name = "badge_url_color")
    @Length(max = 250)
    private String badgeUrl;

}
