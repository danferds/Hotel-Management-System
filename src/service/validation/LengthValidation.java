package service.validation;

import service.ServiceResult;

public class LengthValidation implements ValidationStrategy {
    private final String value;
    private final int length;
    private final String message;
    private final String field;

    public LengthValidation(String value, int length, String message, String field) {
        this.value = value;
        this.length = length;
        this.message = message;
        this.field = field;
    }

    @Override
    public ServiceResult<Void> validate() {
        if (value == null || value.length() != length) {
            return ServiceResult.failure(message, field);
        }
        return ServiceResult.success(null);
    }
}
