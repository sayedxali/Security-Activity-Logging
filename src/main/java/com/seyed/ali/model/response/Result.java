package com.seyed.ali.model.response;

import lombok.Builder;

@Builder
public record Result(boolean flag,
                     Integer code,
                     String message,
                     Object data) {


    public Result(boolean flag, Integer code, String message) {
        this(flag, code, message, null);
    }

}
