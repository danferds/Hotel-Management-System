package service;

import dao.UserDao;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import model.User;
import service.validation.RequiredFieldValidation;
import service.validation.UserEmailAvailableValidation;
import service.validation.ValidationStrategy;

public class UserService {
    private final UserDao userDao = new UserDao();

    public ServiceResult<Void> registerUser(String name, String email, String password, String question, String answer) {
        return new ServiceTemplate<Void>() {
            @Override
            protected List<ValidationStrategy> validations() {
                return Arrays.asList(
                        new RequiredFieldValidation(name, "All Field is required", "name"),
                        new RequiredFieldValidation(email, "All Field is required", "email"),
                        new RequiredFieldValidation(password, "All Field is required", "password"),
                        new RequiredFieldValidation(answer, "All Field is required", "answer"),
                        new UserEmailAvailableValidation(userDao, email)
                );
            }

            @Override
            protected ServiceResult<Void> perform() {
                try {
                    User user = new User(name, email.toLowerCase(), password, question, answer, null);
                    userDao.insert(user);
                    return ServiceResult.success(null);
                } catch (SQLException ex) {
                    return ServiceResult.error();
                }
            }
        }.execute();
    }

    public ServiceResult<String> findSecurityQuestion(String email) {
        try {
            String question = userDao.findSecurityQuestionByEmail(email);
            if (question == null) {
                return ServiceResult.failure("Email id not exist", null);
            }
            return ServiceResult.success(question);
        } catch (SQLException ex) {
            return ServiceResult.error();
        }
    }

    public ServiceResult<Void> resetPasswordWithEmail(String email, String securityQuestion, String answer,
                                                      String newPassword, String emptyPasswordMessage) {
        return new ServiceTemplate<Void>() {
            @Override
            protected List<ValidationStrategy> validations() {
                return Arrays.asList(
                        new RequiredFieldValidation(securityQuestion, "Enter Email and Search it", "email"),
                        new RequiredFieldValidation(answer, "Enter vaild Answer", "answer"),
                        new RequiredFieldValidation(newPassword, emptyPasswordMessage, "password")
                );
            }

            @Override
            protected ServiceResult<Void> perform() {
                try {
                    if (userDao.existsByAnswerAndEmail(answer, email)) {
                        userDao.updatePassword(email, newPassword);
                        return ServiceResult.success(null);
                    }
                    return ServiceResult.failure("Wrong Answer Entery", null);
                } catch (SQLException ex) {
                    return ServiceResult.error();
                }
            }
        }.execute();
    }

    public ServiceResult<Void> resetPasswordByAnswerOnly(String email, String securityQuestion, String answer,
                                                         String newPassword, String emptyPasswordMessage) {
        return new ServiceTemplate<Void>() {
            @Override
            protected List<ValidationStrategy> validations() {
                return Arrays.asList(
                        new RequiredFieldValidation(securityQuestion, "Enter Email and Search it", "email"),
                        new RequiredFieldValidation(newPassword, emptyPasswordMessage, "password")
                );
            }

            @Override
            protected ServiceResult<Void> perform() {
                try {
                    if (userDao.existsByAnswer(answer)) {
                        userDao.updatePassword(email, newPassword);
                        return ServiceResult.success(null);
                    }
                    return ServiceResult.failure("Wrong Answer Entery", "answer");
                } catch (SQLException ex) {
                    return ServiceResult.error();
                }
            }
        }.execute();
    }
}
