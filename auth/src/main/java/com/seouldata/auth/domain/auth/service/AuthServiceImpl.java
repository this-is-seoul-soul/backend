package com.seouldata.auth.domain.auth.service;

import com.seouldata.auth.domain.auth.dto.request.JoinMemberReq;
import com.seouldata.auth.domain.auth.dto.request.ModifyMbtiReq;
import com.seouldata.auth.domain.auth.dto.request.ModifyNicknameReq;
import com.seouldata.auth.domain.auth.dto.response.CreateNicknameRes;
import com.seouldata.auth.domain.auth.dto.response.GoogleLoginRes;
import com.seouldata.auth.domain.auth.dto.response.JoinMemberRes;
import com.seouldata.auth.domain.auth.entity.Member;
import com.seouldata.auth.domain.auth.enums.Adjective;
import com.seouldata.auth.domain.auth.enums.Noun;
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
        Member createdMember = authRepository.save(member);

        return JoinMemberRes.builder()
                .accessToken(generateAccessToken(createdMember.getMemSeq().toString()))
                .refreshToken(generateRefreshToken(createdMember.getMemSeq().toString()))
                .build();
    }

    @Override
    public void modifyNickname(long memberSeq, ModifyNicknameReq modifyNicknameReq) {
        Member member = authRepository.findById(memberSeq)
                .orElseThrow(() -> new AuthException(AuthErrorCode.USER_NOT_FOUND));

        member.setNickname(modifyNicknameReq.getNickname());

        authRepository.save(member);
    }

    @Override
    public GoogleLoginRes googleLogin(String googleId) {
        Member member = authRepository.findByGoogleId(googleId)
                .orElseThrow(() -> new AuthException(AuthErrorCode.USER_NOT_FOUND));

        return GoogleLoginRes.builder()
                .googleId(member.getGoogleId())
                .accessToken(generateAccessToken(member.getMemSeq().toString()))
                .refreshToken(generateRefreshToken(member.getMemSeq().toString()))
                .build();
    }

    @Override
    public void modifyMbti(long memberSeq, ModifyMbtiReq mbti) {
        Member member = authRepository.findById(memberSeq)
                .orElseThrow(() -> new AuthException(AuthErrorCode.USER_NOT_FOUND));

        member.setMbti(mbti.getMbti());

        authRepository.save(member);
    }

    @Override
    public CreateNicknameRes createRandomNickname() {
        Adjective adjective = Adjective.values()[getRandomNumber(Adjective.values().length)];
        Noun noun = Noun.values()[getRandomNumber(Noun.values().length)];
        String nickname = new StringBuilder()
                .append(adjective.getKorean())
                .append(" ")
                .append(noun.getKorean())
                .toString();

        return CreateNicknameRes.builder()
                .nickname(nickname)
                .build();
    }

    @Override
    public Boolean checkNicknameDuplicate(String nickname) {
        return authRepository.existsByNickname(nickname);
    }

    private String saveProfileImage(MultipartFile profile) throws IOException {
        return awsService.saveFile(profile);
    }

    private int getRandomNumber(int max) {
        return (int) (Math.random() * max);
    }
  
    private String generateAccessToken(String id) {
        return jwtProvider.generateAccessToken(id);
    }

    private String generateRefreshToken(String id) {
        return jwtProvider.generateRefreshToken(id);
    }

}
