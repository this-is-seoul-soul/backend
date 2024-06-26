package com.seouldata.auth.domain.badge.service;

import com.seouldata.auth.domain.auth.entity.Member;
import com.seouldata.auth.domain.auth.repository.AuthRepository;
import com.seouldata.auth.domain.badge.dto.request.UpdateBadgeReq;
import com.seouldata.auth.domain.badge.dto.response.GetAllBadgesRes;
import com.seouldata.auth.domain.badge.entity.Badge;
import com.seouldata.auth.domain.badge.repository.BadgeRepository;
import com.seouldata.auth.global.exception.AuthErrorCode;
import com.seouldata.auth.global.exception.AuthException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BadgeServiceImpl implements BadgeService {

    private final AuthRepository authRepository;
    private final BadgeRepository badgeRepository;

    @Override
    public List<GetAllBadgesRes> getAllBadges(long memberSeq) {
        List<Badge> badges = badgeRepository.findAll();
        Set<Long> ownBadgeSeqs = authRepository.findById(memberSeq).get().getOwns().stream()
                .map(own -> own.getBadge().getBadgeSeq())
                .collect(Collectors.toSet());

        AtomicInteger seq = new AtomicInteger();

        return badges.stream()
                .map(badge -> GetAllBadgesRes.builder()
                        .seq(seq.incrementAndGet())
                        .title(badge.getTitle())
                        .image(badge.getBadgeUrl())
                        .isOwn(ownBadgeSeqs.contains(badge.getBadgeSeq()))
                        .build()
                )
                .toList();
    }

    @Override
    public void updateMyBadge(long memberSeq, UpdateBadgeReq updateMyBadgeReq) {
        Member member = authRepository.findById(memberSeq)
                .orElseThrow(() -> new AuthException(AuthErrorCode.USER_NOT_FOUND));

        Badge badge = badgeRepository.findById((long) updateMyBadgeReq.getSeq())
                .orElseThrow(() -> new AuthException(AuthErrorCode.BADGE_NOT_FOUND));

        member.setBadgeSeq(badge);
    }

}
