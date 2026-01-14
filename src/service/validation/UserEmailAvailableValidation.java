package service.validation;

import dao.UserDao;
import java.sql.SQLException;
import service.ServiceResult;

public class UserEmailAvailableValidation implements ValidationStrategy {
    private final UserDao userDao;
    private final String email;

    public UserEmailAvailableValidation(UserDao userDao, String email) {
        this.userDao = userDao;
        this.email = email;
    }

    @Override
    public ServiceResult<Void> validate() {
        try {
            if (userDao.findByEmail(email) != null) {
                return ServiceResult.failure("Use Another Email ID", "email");
            }
            return ServiceResult.success(null);
        } catch (SQLException ex) {
            return ServiceResult.error();
        }
    }
}
