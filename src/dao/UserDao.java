package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.User;

public class UserDao {
    public User findByEmail(String email) throws SQLException {
        String sql = "select * from signup where email=?";
        try (Connection con = ConnectionProvider.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, email);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return mapUser(rs);
                }
                return null;
            }
        }
    }

    public User findByEmailAndPassword(String email, String password) throws SQLException {
        String sql = "select * from signup where email=? AND password=?";
        try (Connection con = ConnectionProvider.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, email);
            pst.setString(2, password);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return mapUser(rs);
                }
                return null;
            }
        }
    }

    public User findByEmailAndStatus(String email, String status) throws SQLException {
        String sql = "select * from signup where status=? AND email=?";
        try (Connection con = ConnectionProvider.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, status);
            pst.setString(2, email);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return mapUser(rs);
                }
                return null;
            }
        }
    }

    public List<User> findAll() throws SQLException {
        String sql = "select * from signup";
        List<User> users = new ArrayList<>();
        try (Connection con = ConnectionProvider.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                users.add(mapUser(rs));
            }
        }
        return users;
    }

    public boolean insert(User user) throws SQLException {
        String sql = "insert into signup(name,email,password,sq,answer)values(?,?,?,?,?)";
        try (Connection con = ConnectionProvider.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, user.getName());
            pst.setString(2, user.getEmail());
            pst.setString(3, user.getPassword());
            pst.setString(4, user.getSecurityQuestion());
            pst.setString(5, user.getAnswer());
            return pst.executeUpdate() > 0;
        }
    }

    public boolean updateStatus(String email, String status) throws SQLException {
        String sql = "update signup set status=? where email=?";
        try (Connection con = ConnectionProvider.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, status);
            pst.setString(2, email);
            return pst.executeUpdate() > 0;
        }
    }

    public String findSecurityQuestionByEmail(String email) throws SQLException {
        String sql = "select sq from signup where email=?";
        try (Connection con = ConnectionProvider.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, email);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("sq");
                }
                return null;
            }
        }
    }

    public boolean existsByAnswerAndEmail(String answer, String email) throws SQLException {
        String sql = "select * from signup where answer=? and email=?";
        try (Connection con = ConnectionProvider.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, answer);
            pst.setString(2, email);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next();
            }
        }
    }

    public boolean existsByAnswer(String answer) throws SQLException {
        String sql = "select * from signup where answer=?";
        try (Connection con = ConnectionProvider.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, answer);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next();
            }
        }
    }

    public boolean updatePassword(String email, String password) throws SQLException {
        String sql = "update signup set password=? where email=?";
        try (Connection con = ConnectionProvider.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, password);
            pst.setString(2, email);
            return pst.executeUpdate() > 0;
        }
    }

    private User mapUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setName(rs.getString("name"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setSecurityQuestion(rs.getString("sq"));
        user.setAnswer(rs.getString("answer"));
        user.setStatus(rs.getString("status"));
        return user;
    }
}
