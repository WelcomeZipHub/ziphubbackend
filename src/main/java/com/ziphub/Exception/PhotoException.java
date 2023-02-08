package com.ziphub.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.IOException;

@Getter
@AllArgsConstructor
public class PhotoException extends IOException {
    private ErrorCode errorCode;
    private String message;
}
