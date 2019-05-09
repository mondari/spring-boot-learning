package com.mondari.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * HTTP 请求返回结果
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseResult<T> {
    /**
     * 状态码
     */
    @JsonAlias({"status", "statusCode"})
    private int code;

    /**
     * 信息
     */
    @JsonAlias({"msg"})
    private String message;

    /**
     * 数据
     */
    private T data;

    public ResponseResult(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
