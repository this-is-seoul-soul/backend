package com.seouldata.auth.domain.auth.service;

import com.seouldata.auth.domain.auth.dto.request.JoinMemberReq;
import com.seouldata.auth.domain.auth.dto.request.ModifyNicknameReq;
import com.seouldata.auth.domain.auth.dto.response.JoinMemberRes;
import com.seouldata.auth.domain.auth.entity.Member;
import com.seouldata.auth.domain.auth.repository.AuthRepository;
import com.seouldata.auth.global.exception.AuthErrorCode;
import com.seouldata.auth.global.exception.AuthException;
import com.seouldata.auth.global.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthRepository authRepository;
    private final AwsService awsService;

    private final JwtProvider jwtProvider;

    @Override
    public JoinMemberRes join(JoinMemberReq joinMemberReq, MultipartFile profile) throws IOException {
        // email과 nickname 중복 검사
        authRepository.findByEmail(joinMemberReq.getEmail()).ifPresent(member -> {
            throw new AuthException(AuthErrorCode.USER_ALREADY_EXISTS);
        });

        authRepository.findByNickname(joinMemberReq.getEmail()).ifPresent(member -> {
            throw new AuthException(AuthErrorCode.NICKNAME_ALREADY_EXISTS);
        });

        Member member = Member.builder()
                .email(joinMemberReq.getEmail())
                .nickname(joinMemberReq.getNickname())
                .googleId(joinMemberReq.getGoogleId())
                .image(saveProfileImage(profile))
                .notification(false)
                .build();
        authRepository.save(member);

        return JoinMemberRes.builder()
                .accessToken(jwtProvider.generateAccessToken(member.getGoogleId()))
                .refreshToken(jwtProvider.generateRefreshToken(member.getGoogleId()))
                .build();
    }

    @Override
    public void modifyNickname(long memberSeq, ModifyNicknameReq modifyNicknameReq) {
        Member member = authRepository.findById(memberSeq)
                .orElseThrow(() -> new AuthException(AuthErrorCode.USER_NOT_FOUND));

        member.setNickname(modifyNicknameReq.getNickname());

        authRepository.save(member);
    }

    private String saveProfileImage(MultipartFile profile) throws IOException {
        return awsService.saveFile(profile);
    }

}
