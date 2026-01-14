package service;

import java.util.List;
import service.validation.ValidationStrategy;

public abstract class ServiceTemplate<T> {
    public final ServiceResult<T> execute() {
        for (ValidationStrategy validation : validations()) {
            ServiceResult<Void> result = validation.validate();
            if (result.getStatus() == ServiceResult.Status.ERROR) {
                return ServiceResult.error();
            }
            if (result.getStatus() == ServiceResult.Status.FAILURE) {
                return ServiceResult.failure(result.getMessage(), result.getField());
            }
        }
        return perform();
    }

    protected abstract List<ValidationStrategy> validations();

    protected abstract ServiceResult<T> perform();
}
