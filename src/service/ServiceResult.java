package service;

public class ServiceResult<T> {
    public enum Status {
        SUCCESS,
        FAILURE,
        ERROR
    }

    private final Status status;
    private final T data;
    private final String message;
    private final String field;

    private ServiceResult(Status status, T data, String message, String field) {
        this.status = status;
        this.data = data;
        this.message = message;
        this.field = field;
    }

    public static <T> ServiceResult<T> success(T data) {
        return new ServiceResult<>(Status.SUCCESS, data, null, null);
    }

    public static <T> ServiceResult<T> failure(String message, String field) {
        return new ServiceResult<>(Status.FAILURE, null, message, field);
    }

    public static <T> ServiceResult<T> error() {
        return new ServiceResult<>(Status.ERROR, null, null, null);
    }

    public Status getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public String getField() {
        return field;
    }
}
