package com.works.push.service.pipeline;

import com.works.enums.ProcessRespStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author DaLong
 * @date 2023/5/29 10:41
 */
@Data
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class ProcessResult<T> {

    private Integer status;

    private String message;

    private T data;

    public ProcessResult(ProcessRespStatusEnum status, String msg, T data) {
        this.status = status.getCode();
        this.message = msg;
        this.data = data;
    }

    public ProcessResult(ProcessRespStatusEnum status, T data) {
        this(status, status.getDesc(), data);
    }


    public ProcessResult(ProcessRespStatusEnum status) {
        this(status, status.getDesc(), null);
    }

    public static <T> ProcessResult<T> fail(ProcessRespStatusEnum statusEnum) {
        return fail(statusEnum, statusEnum.getDesc());
    }

    public static <T> ProcessResult<T> fail(ProcessRespStatusEnum statusEnum, String msg) {
        return new ProcessResult<>(statusEnum, msg, null);
    }

    public static <T> ProcessResult<T> fail(String msg) {
        return fail(ProcessRespStatusEnum.FAIL, msg);
    }

    public static <T> ProcessResult<T> success(T data) {
        return new ProcessResult<>(ProcessRespStatusEnum.SUCCESS, data);
    }

    public static <T> ProcessResult<T> success() {
        return new ProcessResult<>(ProcessRespStatusEnum.SUCCESS);
    }
}
