package controller;

import java.util.ArrayList;
import java.util.List;
import model.User;
import service.AdminService;
import service.ServiceResult;

public class AdminController {
    private final AdminService adminService = new AdminService();

    public List<Object[]> listAllUsers() {
        List<User> users = adminService.listUsers();
        return toTableRows(users);
    }

    public ActionResult<List<Object[]>> searchByEmail(String email) {
        if (email == null || email.isEmpty()) {
            return ActionResult.failure("Record Not Found");
        }
        ServiceResult<List<User>> result = adminService.searchByEmail(email);
        if (result.getStatus() == ServiceResult.Status.ERROR) {
            return ActionResult.failure("Record Not Found");
        }
        return ActionResult.success(toTableRows(result.getData()));
    }

    public void updateStatus(String email, String status) {
        adminService.updateStatus(email, status);
    }

    private List<Object[]> toTableRows(List<User> users) {
        List<Object[]> rows = new ArrayList<>();
        for (User user : users) {
            rows.add(new Object[]{
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getSecurityQuestion(),
                user.getAnswer(),
                user.getStatus()
            });
        }
        return rows;
    }
}
