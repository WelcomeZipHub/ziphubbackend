package com.ziphub.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    USERNAME_DUPLICATED(HttpStatus.CONFLICT, ""),
    USERNAME_NOT_FOUND(HttpStatus.NOT_FOUND, ""),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, ""),
    INVALID_PHOTOS(HttpStatus.BAD_REQUEST,""),
    NON_EXIST_PHOTOS(HttpStatus.NO_CONTENT, "");

    private HttpStatus httpStatus;
    private String message;

}
