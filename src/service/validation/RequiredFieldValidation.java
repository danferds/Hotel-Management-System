package service.validation;

import service.ServiceResult;

public class RequiredFieldValidation implements ValidationStrategy {
    private final String value;
    private final String message;
    private final String field;

    public RequiredFieldValidation(String value, String message, String field) {
        this.value = value;
        this.message = message;
        this.field = field;
    }

    @Override
    public ServiceResult<Void> validate() {
        if (value == null || value.isEmpty()) {
            return ServiceResult.failure(message, field);
        }
        return ServiceResult.success(null);
    }
}
