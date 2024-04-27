package com.seouldata.auth.global.exception.handler;

import com.seouldata.auth.global.exception.AuthException;
import com.seouldata.common.exception.BusinessException;
import com.seouldata.common.exception.ErrorCode;
import com.seouldata.common.response.EnvelopResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AuthExceptionHandler {

    @ExceptionHandler(AuthException.class)
    protected ResponseEntity<EnvelopResponse> handleBusinessException(BusinessException e) {
        ErrorCode errorCode = e.getErrorCode();

        return ResponseEntity.status(errorCode.getCode())
                .body(
                        EnvelopResponse.builder()
                                .code(errorCode.getCode())
                                .message(errorCode.getMessage())
                                .build()
                );
    }

}
