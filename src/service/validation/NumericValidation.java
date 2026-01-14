package service.validation;

import service.ServiceResult;

public class NumericValidation implements ValidationStrategy {
    private final String value;
    private final String message;
    private final String field;

    public NumericValidation(String value, String message, String field) {
        this.value = value;
        this.message = message;
        this.field = field;
    }

    @Override
    public ServiceResult<Void> validate() {
        try {
            Long.parseLong(value);
            return ServiceResult.success(null);
        } catch (Exception ex) {
            return ServiceResult.failure(message, field);
        }
    }
}
