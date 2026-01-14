package service;

import dao.UserDao;
import java.sql.SQLException;
import model.User;

public class AuthService {
    public enum Status {
        ADMIN,
        APPROVED,
        PENDING,
        INVALID,
        ERROR
    }

    private final UserDao userDao = new UserDao();

    public Status login(String email, String password) {
        if (email != null && password != null
                && "admin".equalsIgnoreCase(email)
                && "admin".equalsIgnoreCase(password)) {
            return Status.ADMIN;
        }

        try {
            User user = userDao.findByEmailAndPassword(email, password);
            if (user == null) {
                return Status.INVALID;
            }
            User approved = userDao.findByEmailAndStatus(email, "approved");
            if (approved != null) {
                return Status.APPROVED;
            }
            return Status.PENDING;
        } catch (SQLException ex) {
            return Status.ERROR;
        }
    }
}
