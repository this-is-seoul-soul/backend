package com.seouldata.auth.domain.auth.service;

import com.seouldata.auth.domain.auth.dto.request.JoinMemberReq;
import com.seouldata.auth.domain.auth.dto.request.ModifyMbtiReq;
import com.seouldata.auth.domain.auth.dto.request.ModifyNicknameReq;
import com.seouldata.auth.domain.auth.dto.response.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AuthService {

    JoinMemberRes join(JoinMemberReq joinMemberReq, MultipartFile profile) throws IOException;

    void modifyNickname(long memberSeq, ModifyNicknameReq modifyNicknameReq);
  
    Boolean checkNicknameDuplicate(String nickname);

    CreateNicknameRes createRandomNickname();

    GoogleLoginRes googleLogin(String googleId);

    GetMemberInfoRes getMemberInfo(long memberSeq);

    GetReviewWriterInfo getReviewWriterInfo(long memberSeq);

    void modifyMbti(long memberSeq, ModifyMbtiReq mbti);

    void logout(String token);

    GetNewAccessTokenRes generateNewToken(String token);

    void quit(String token);

    GetMemberStatusRes checkStatus(String googleId);

}
