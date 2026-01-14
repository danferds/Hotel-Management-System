package controller;

import service.AuthService;
import service.ServiceResult;
import service.UserService;

public class AuthController {
    private final AuthService authService = new AuthService();
    private final UserService userService = new UserService();

    public ActionResult<LoginStatus> login(String email, String password) {
        AuthService.Status status = authService.login(email, password);
        switch (status) {
            case ADMIN:
                return ActionResult.success(LoginStatus.ADMIN);
            case APPROVED:
                return ActionResult.success(LoginStatus.APPROVED_USER);
            case PENDING:
                return ActionResult.failure(LoginStatus.PENDING_USER, "Wait for Addmin Approval", "email");
            case INVALID:
                return ActionResult.failure(LoginStatus.INVALID, "Incorrect Email ID or Password", "email");
            case ERROR:
            default:
                return ActionResult.failure(LoginStatus.ERROR, null, null);
        }
    }

    public ActionResult<Void> registerUser(String name, String email, String password, String question, String answer,
                                           String successMessage) {
        ServiceResult<Void> result = userService.registerUser(name, email, password, question, answer);
        if (result.getStatus() == ServiceResult.Status.FAILURE) {
            return ActionResult.failure(result.getMessage(), result.getField());
        }
        if (result.getStatus() == ServiceResult.Status.ERROR) {
            return ActionResult.failure(null);
        }
        return ActionResult.success(null, successMessage);
    }

    public ActionResult<String> findSecurityQuestion(String email) {
        ServiceResult<String> result = userService.findSecurityQuestion(email);
        if (result.getStatus() == ServiceResult.Status.FAILURE) {
            return ActionResult.failure(result.getMessage());
        }
        if (result.getStatus() == ServiceResult.Status.ERROR) {
            return ActionResult.failure(null);
        }
        return ActionResult.success(result.getData());
    }

    public ActionResult<Void> resetPasswordWithEmail(String email, String securityQuestion, String answer,
                                                     String newPassword, String emptyPasswordMessage,
                                                     String successMessage) {
        ServiceResult<Void> result = userService.resetPasswordWithEmail(
                email, securityQuestion, answer, newPassword, emptyPasswordMessage);
        if (result.getStatus() == ServiceResult.Status.FAILURE) {
            return ActionResult.failure(result.getMessage(), result.getField());
        }
        if (result.getStatus() == ServiceResult.Status.ERROR) {
            return ActionResult.failure(null);
        }
        return ActionResult.success(null, successMessage);
    }

    public ActionResult<Void> resetPasswordByAnswerOnly(String email, String securityQuestion, String answer,
                                                        String newPassword, String emptyPasswordMessage,
                                                        String successMessage) {
        ServiceResult<Void> result = userService.resetPasswordByAnswerOnly(
                email, securityQuestion, answer, newPassword, emptyPasswordMessage);
        if (result.getStatus() == ServiceResult.Status.FAILURE) {
            return ActionResult.failure(result.getMessage(), result.getField());
        }
        if (result.getStatus() == ServiceResult.Status.ERROR) {
            return ActionResult.failure(null);
        }
        return ActionResult.success(null, successMessage);
    }
}
