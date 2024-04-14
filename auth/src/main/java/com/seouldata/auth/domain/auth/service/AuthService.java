package com.seouldata.auth.domain.auth.service;

import com.seouldata.auth.domain.auth.dto.request.JoinMemberReq;
import com.seouldata.auth.domain.auth.dto.request.ModifyMbtiReq;
import com.seouldata.auth.domain.auth.dto.request.ModifyNicknameReq;
import com.seouldata.auth.domain.auth.dto.response.CreateNicknameRes;
import com.seouldata.auth.domain.auth.dto.response.GoogleLoginRes;
import com.seouldata.auth.domain.auth.dto.response.JoinMemberRes;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AuthService {

    JoinMemberRes join(JoinMemberReq joinMemberReq, MultipartFile profile) throws IOException;

    void modifyNickname(long memberSeq, ModifyNicknameReq modifyNicknameReq);
  
    Boolean checkNicknameDuplicate(String nickname);

    CreateNicknameRes createRandomNickname();

    GoogleLoginRes googleLogin(String googleId);

    void modifyMbti(long memberSeq, ModifyMbtiReq mbti);

}
