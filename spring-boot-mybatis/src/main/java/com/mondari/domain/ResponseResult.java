package com.mondari.domain;

import lombok.Data;

@Data
public class ResponseResult {
    /**
     * 状态码
     */
    private int code;
    /**
     * 信息
     */
    private String message;
    /**
     * 数据
     */
    private Object data;

    public ResponseResult(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
