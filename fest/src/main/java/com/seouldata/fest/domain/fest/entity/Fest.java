package com.seouldata.fest.domain.fest.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Entity
@Table(name = "fest")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Fest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fest_seq")
    private Long festSeq;

    @Column(length = 100)
    @NotNull
    private String title;

    @Column
    @NotNull
    private int codename;

    @Transient
    @Enumerated(EnumType.STRING)
    private Codename codeType;

    @Column(length = 4)
    @NotNull
    private String guname;

    @Column(length = 160)
    @NotNull
    private String place;

    @Column(name = "use_trgt", length = 90)
    @NotNull
    private String useTrgt;

    @Column(name = "is_free", length = 2)
    private String isFree;

    @Column(name = "use_fee", length = 270)
    @NotNull
    private String useFee;

    @Column(name = "start_date")
    @NotNull
    private LocalDateTime startDate;

    @Column(name = "end_date")
    @NotNull
    private LocalDateTime endDate;

    @Column
    private double lot;

    @Column
    private double lat;

    @Column(name = "org_link", length = 550)
    private String orgLink;

    @Column(name = "main_img", length = 120)
    private String mainImg;

    @Column(name = "is_deleted", nullable = false, columnDefinition = "tinyint(1)")
    @ColumnDefault("0")
    private boolean isDeleted;

    @Column(name = "is_public", nullable = false, columnDefinition = "tinyint(1)")
    @ColumnDefault("0")
    private boolean isPublic;

    @Column(name = "creator")
    private Long creator;

    @Builder
    public Fest(String title, int codename, String guname, String place, String useTrgt, String isFree, String useFee, LocalDateTime startDate, LocalDateTime endDate, double lot, double lat, String orgLink, String mainImg, boolean isDeleted, boolean isPublic, Long creator) {
        this.title = title;
        this.codename = codename;
        this.guname = guname;
        this.place = place;
        this.useTrgt = useTrgt;
        this.isFree = isFree;
        this.useFee = useFee;
        this.startDate = startDate;
        this.endDate = endDate;
        this.lot = lot;
        this.lat = lat;
        this.orgLink = orgLink;
        this.mainImg = mainImg;
        this.isDeleted = isDeleted;
        this.isPublic = isPublic;
        this.creator = creator;
    }

    public void modify(String title, int codename, String guname, String place, String useTrgt, String isFree, String useFee, LocalDateTime startDate, LocalDateTime endDate, double lot, double lat, String orgLink, String mainImg) {
        this.title = title;
        this.codename = codename;
        this.guname = guname;
        this.place = place;
        this.useTrgt = useTrgt;
        this.isFree = isFree;
        this.useFee = useFee;
        this.startDate = startDate;
        this.endDate = endDate;
        this.lot = lot;
        this.lat = lat;
        this.orgLink = orgLink;
        this.mainImg = mainImg;
    }

}
