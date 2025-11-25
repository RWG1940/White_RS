package white.rs.common.response;

import java.io.Serializable;

/**
 * 统一响应封装类
 *
 * @param <T> 响应数据类型
 */
public class WhiteResponse<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 响应码
     */
    private Integer code;

    /**
     * 响应消息
     */
    private String message;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 时间戳
     */
    private Long timestamp;

    public WhiteResponse() {
        this.timestamp = System.currentTimeMillis();
    }

    public WhiteResponse(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }

    /**
     * 成功响应（无数据）
     */
    public static <T> WhiteResponse<T> success() {
        return new WhiteResponse<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), null);
    }

    /**
     * 成功响应（带数据）
     */
    public static <T> WhiteResponse<T> success(T data) {
        return new WhiteResponse<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), data);
    }

    /**
     * 成功响应（自定义消息）
     */
    public static <T> WhiteResponse<T> success(String message, T data) {
        return new WhiteResponse<>(ResponseCode.SUCCESS.getCode(), message, data);
    }

    /**
     * 失败响应
     */
    public static <T> WhiteResponse<T> fail() {
        return new WhiteResponse<>(ResponseCode.FAIL.getCode(), ResponseCode.FAIL.getMessage(), null);
    }

    /**
     * 失败响应（自定义消息）
     */
    public static <T> WhiteResponse<T> fail(String message) {
        return new WhiteResponse<>(ResponseCode.FAIL.getCode(), message, null);
    }

    /**
     * 失败响应（自定义码和消息）
     */
    public static <T> WhiteResponse<T> fail(Integer code, String message) {
        return new WhiteResponse<>(code, message, null);
    }

    /**
     * 失败响应（使用响应码枚举）
     */
    public static <T> WhiteResponse<T> fail(ResponseCode responseCode) {
        return new WhiteResponse<>(responseCode.getCode(), responseCode.getMessage(), null);
    }

    // Getters and Setters
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}

