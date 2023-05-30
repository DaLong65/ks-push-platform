package com.works.dto;

import com.works.enums.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author DaLong
 * @date 2023/5/29 11:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DubboResponse <T extends Serializable> implements Serializable{
    /**
     * 响应码
     */
    private int code;
    /**
     * 消息
     */
    private String message = "";
    /**
     * 业务数据
     */
    private T data;

    public DubboResponse(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public DubboResponse(T data) {
        this.data = data;
    }

    public static <T extends Serializable> DubboResponse<T> success() {
        return new DubboResponse<>();
    }

    public static <T extends Serializable> DubboResponse<T> success(T data) {
        return new DubboResponse<>(data);
    }

    public static <T extends Serializable> DubboResponse<T> success(String message, T data) {
        return new DubboResponse<>(message, data);
    }

    public static <T extends Serializable> DubboResponse<T> failure() {
        return new DubboResponse<>(ResponseCode.FAILURE.getCode(), ResponseCode.FAILURE.getMessage(), null);
    }

    public static <T extends Serializable> DubboResponse<T> failure(int code) {
        return new DubboResponse<>(code, ResponseCode.FAILURE.getMessage(), null);
    }

    public static <T extends Serializable> DubboResponse<T> failure(String message) {
        return new DubboResponse<>(ResponseCode.FAILURE.getCode(), message, null);
    }

    public static <T extends Serializable> DubboResponse<T> failure(int code, String message) {
        return new DubboResponse<>(code, message, null);
    }

    public boolean isSuccess() {
        return code == ResponseCode.SUCCESS.getCode();
    }
}
