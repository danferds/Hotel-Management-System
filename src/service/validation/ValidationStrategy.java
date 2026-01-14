package service.validation;

import service.ServiceResult;

public interface ValidationStrategy {
    ServiceResult<Void> validate();
}
