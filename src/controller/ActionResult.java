package controller;

public class ActionResult<T> {
    private final boolean success;
    private final String message;
    private final String field;
    private final T data;

    private ActionResult(boolean success, T data, String message, String field) {
        this.success = success;
        this.data = data;
        this.message = message;
        this.field = field;
    }

    public static <T> ActionResult<T> success(T data, String message) {
        return new ActionResult<>(true, data, message, null);
    }

    public static <T> ActionResult<T> success(T data) {
        return new ActionResult<>(true, data, null, null);
    }

    public static <T> ActionResult<T> failure(String message, String field) {
        return new ActionResult<>(false, null, message, field);
    }

    public static <T> ActionResult<T> failure(T data, String message, String field) {
        return new ActionResult<>(false, data, message, field);
    }

    public static <T> ActionResult<T> failure(String message) {
        return new ActionResult<>(false, null, message, null);
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public boolean hasMessage() {
        return message != null && !message.isEmpty();
    }

    public String getField() {
        return field;
    }

    public T getData() {
        return data;
    }
}
