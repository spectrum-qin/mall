package com.spectrum.mall.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.spectrum.mall.common.IExceptionCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author oe_qinzuopu
 */
@ApiModel(
        value = "DataResponse",
        description = "返回的据类型"
)
@EqualsAndHashCode
@ToString
@Data
public class DataResponse<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final String SUCCESS = "0000000";
    private static final String SUCCESS_MSG = "操作成功";
    public static final String ERROR = "0000001";
    private static final String ERROR_MSG = "操作失败";
    @ApiModelProperty("返回码")
    private String code;
    @ApiModelProperty("返回消息")
    private String message;
    @JsonInclude(Include.NON_EMPTY)
    @ApiModelProperty("返回业务对象")
    private T data;
    @ApiModelProperty("分页信息")
    @JsonInclude(Include.NON_EMPTY)
    private Page pagination;

    public DataResponse() {
    }

    public DataResponse(DataResponse.Builder builder) {
        this.code = builder.code;
        this.message = builder.message;
    }

    public DataResponse(T object) {
        this.code = "0000000";
        this.message = "操作成功";
        this.data = object;
    }

    public DataResponse(T object, int current, int pageSize, long total, long pages) {
        this(object);
        this.pagination = new Page(current, pageSize, total, pages);
    }

    public static DataResponse succeed() {
        return builder().code("0000000").message("操作成功").build();
    }

    public static <T> DataResponse<T> succeed(T data) {
        DataResponse<T> defaultResponse = succeed();
        defaultResponse.setData(data);
        return defaultResponse;
    }

    public static <T> DataResponse<T> succeed(T data, int current, int pageSize, long total, long pages) {
        DataResponse<T> defaultResponse = succeed();
        defaultResponse.setData(data);
        Page pagination = new Page(current, pageSize, total, pages);
        defaultResponse.setPagination(pagination);
        return defaultResponse;
    }

    public static <T> DataResponse<T> failed() {
        return builder().code("0000001").message("操作失败").build();
    }

    public static <T> DataResponse<T> failed(String code, String msg, T data) {
        DataResponse<T> response = builder().code(code).message(msg).build();
        response.setData(data);
        return response;
    }

    public static <T> DataResponse<T> failed(String code, String msg) {
        return (DataResponse<T>) failed(code, msg, (Object)null);
    }

    public static <T> DataResponse<T> failed(IExceptionCode abstractEnum) {
        return (DataResponse<T>) failed((String)abstractEnum.getValue(), abstractEnum.getMessage(), (Object)null);
    }

    public static <T> DataResponse<T> failed(IExceptionCode abstractEnum, T data) {
        return failed((String)abstractEnum.getValue(), abstractEnum.getMessage(), data);
    }

    public static DataResponse.Builder builder() {
        return new DataResponse.Builder();
    }

    public static class Builder {
        private String code;
        private String message;

        Builder() {
        }

        public DataResponse build() {
            return new DataResponse(this);
        }

        public String getCode() {
            return this.code;
        }

        public DataResponse.Builder code(String code) {
            this.code = code;
            return this;
        }

        public String getMessage() {
            return this.message;
        }

        public DataResponse.Builder message(String message) {
            this.message = message;
            return this;
        }
    }
}
