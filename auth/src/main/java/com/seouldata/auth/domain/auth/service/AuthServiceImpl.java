package com.seouldata.auth.domain.auth.service;

import com.seouldata.auth.domain.auth.dto.request.JoinMemberReq;
import com.seouldata.auth.domain.auth.dto.request.ModifyMbtiReq;
import com.seouldata.auth.domain.auth.dto.request.ModifyNicknameReq;
import com.seouldata.auth.domain.auth.dto.response.*;
import com.seouldata.auth.domain.auth.entity.Member;
import com.seouldata.auth.domain.auth.enums.Adjective;
import com.seouldata.auth.domain.auth.enums.MemberStatus;
import com.seouldata.auth.domain.auth.enums.Noun;
import com.seouldata.auth.domain.auth.repository.AuthRepository;
import com.seouldata.auth.global.jwt.JwtProvider;
import com.seouldata.common.exception.BusinessException;
import com.seouldata.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthRepository authRepository;
    private final AwsService awsService;

    private final JwtProvider jwtProvider;

    @Override
    public JoinMemberRes join(JoinMemberReq joinMemberReq, MultipartFile profile) throws IOException {
        Member member = Member.builder()
                .email(joinMemberReq.getEmail() != null ? joinMemberReq.getNickname() : null)
                .nickname(joinMemberReq.getNickname() != null ? joinMemberReq.getNickname() : null)
                .googleId(joinMemberReq.getGoogleId())
                .image(profile != null ? saveProfileImage(profile) : null)
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
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        member.setNickname(modifyNicknameReq.getNickname());

        authRepository.save(member);
    }

    @Override
    public GoogleLoginRes googleLogin(String googleId) {
        Member member = authRepository.findByGoogleId(googleId)
                .orElse(Member.builder()
                        .googleId(googleId)
                        .notification(false)
                        .build());

        Member savedMember = authRepository.save(member);

        return GoogleLoginRes.builder()
                .googleId(savedMember.getGoogleId())
                .accessToken(generateAccessToken(savedMember.getMemSeq().toString()))
                .refreshToken(generateRefreshToken(savedMember.getMemSeq().toString()))
                .build();
    }

    @Override
    public GetMemberInfoRes getMemberInfo(long memberSeq) {
        Member member = authRepository.findById(memberSeq)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        return GetMemberInfoRes.builder()
                .email(member.getEmail())
                .nickname(member.getNickname())
                .profile(member.getImage())
                .mbti(member.getMbti())
                .notification(member.getNotification())
                .build();
    }

    @Override
    public GetReviewWriterInfo getReviewWriterInfo(long memberSeq) {
        Member member = authRepository.findById(memberSeq)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        return GetReviewWriterInfo.builder()
                .nickname(member.getNickname())
                .mbti(member.getMbti())
                .build();
    }

    public void modifyMbti(long memberSeq, ModifyMbtiReq mbti) {
        Member member = authRepository.findById(memberSeq)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        member.setMbti(mbti.getMbti());

        authRepository.save(member);
    }

    @Override
    public void logout(String token) {
        Member member = findMemberByToken(token);
        jwtProvider.storeBlacklist(token, String.valueOf(member.getMemSeq()));
    }

    @Override
    public void quit(String token) {
        Member member = findMemberByToken(token);
        jwtProvider.storeBlacklist(token, String.valueOf(member.getMemSeq()));
        authRepository.delete(member);
    }

    @Override
    public GetMemberStatusRes checkStatus(String token, String googleId) {
        Optional<Member> foundMember = Optional.empty();
        if (token == null) {
            foundMember = authRepository.findByGoogleId(googleId);
        } else {
            foundMember = Optional.of(findMemberByToken(token));
        }

        return foundMember
                .map(member -> {
                    if (member.getNickname() == null || member.getNickname().isBlank()) { // 닉네임이 없는 경우
                        return GetMemberStatusRes.builder()
                                .status(MemberStatus.NICKNAME.getLabel())
                                .build();
                    }
                    if (member.getMbti() == null || member.getMbti().isBlank()) { // MBTI가 없는 경우
                        return GetMemberStatusRes.builder()
                                .status(MemberStatus.FESTI.getLabel())
                                .build();
                    }
                    return GetMemberStatusRes.builder()
                            .status(MemberStatus.COMPLETE.getLabel())
                            .build(); // 회원 가입이 완료된 경우
                })
                .orElse(GetMemberStatusRes.builder()
                        .status(MemberStatus.INIT.getLabel())
                        .build());
    }

    @Override
    public GetNewAccessTokenRes generateNewToken(String token) {
        return GetNewAccessTokenRes.builder()
                .accessToken(jwtProvider.reIssue(token))
                .build();
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

    private Member findMemberByToken(String token) {
        long memSeq = Long.parseLong(jwtProvider.extractMemberId(token));

        return authRepository.findById(memSeq)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    }

}
