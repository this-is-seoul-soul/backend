package com.seouldata.auth.global.exception;

import com.seouldata.common.exception.BusinessException;
import com.seouldata.common.exception.ErrorCode;

public class AuthException extends BusinessException {

    public AuthException(ErrorCode errorCode) {
        super(errorCode);
    }

}
