package com.seyed.ali.model.response;

import org.springframework.http.HttpStatus;

public class StatusCode {

    public static int SUCCESS = HttpStatus.OK.value();
    public static int CREATED = HttpStatus.CREATED.value();
    public static int INVALID_ARGUMENT = HttpStatus.BAD_REQUEST.value();
    public static int BAD_REQUEST = HttpStatus.BAD_REQUEST.value();
    public static int UNAUTHORIZED = HttpStatus.UNAUTHORIZED.value();
    public static int FORBIDDEN = HttpStatus.FORBIDDEN.value();
    public static int NOT_FOUND = HttpStatus.NOT_FOUND.value();
    public static int INTERNAL_SERVER_ERROR = HttpStatus.INTERNAL_SERVER_ERROR.value();

}
