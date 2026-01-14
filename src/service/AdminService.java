package service;

import dao.UserDao;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import model.User;

public class AdminService {
    private final UserDao userDao = new UserDao();

    public List<User> listUsers() {
        try {
            return userDao.findAll();
        } catch (SQLException ex) {
            return Collections.emptyList();
        }
    }

    public ServiceResult<List<User>> searchByEmail(String email) {
        try {
            User user = userDao.findByEmail(email);
            if (user == null) {
                return ServiceResult.success(Collections.emptyList());
            }
            return ServiceResult.success(Collections.singletonList(user));
        } catch (SQLException ex) {
            return ServiceResult.error();
        }
    }

    public boolean updateStatus(String email, String status) {
        try {
            userDao.updateStatus(email, status);
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }
}
