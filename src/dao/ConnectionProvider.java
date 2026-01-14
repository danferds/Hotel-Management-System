/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Sudhir Kushwaha
 */
public class ConnectionProvider {
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            throw new SQLException("JDBC driver not found", ex);
        }
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/hotel",
                "root",
                "Sudhir@123"
        );
    }
}
