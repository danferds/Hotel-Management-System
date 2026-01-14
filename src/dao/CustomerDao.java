package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Customer;

public class CustomerDao {
    public boolean insert(Customer customer) throws SQLException {
        String sql = "insert into customer(name,mobile,email,gender,address,id,nationality,date,roomnumber,bed,roomtype,price,status)"
                + "values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try (Connection con = ConnectionProvider.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, customer.getName());
            pst.setString(2, customer.getMobile());
            pst.setString(3, customer.getEmail());
            pst.setString(4, customer.getGender());
            pst.setString(5, customer.getAddress());
            pst.setString(6, customer.getIdNumber());
            pst.setString(7, customer.getNationality());
            pst.setString(8, customer.getCheckInDate());
            pst.setString(9, customer.getRoomNumber());
            pst.setString(10, customer.getBed());
            pst.setString(11, customer.getRoomType());
            pst.setString(12, customer.getPrice());
            pst.setString(13, customer.getStatus());
            return pst.executeUpdate() > 0;
        }
    }

    public List<Customer> findByStatus(String status) throws SQLException {
        String sql = "Select * from customer where status=?";
        List<Customer> customers = new ArrayList<>();
        try (Connection con = ConnectionProvider.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, status);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    customers.add(mapCustomer(rs));
                }
            }
        }
        return customers;
    }

    public Customer findByRoomNumberAndStatus(String roomNumber, String status) throws SQLException {
        String sql = "select name,mobile,email,date,price from customer where roomnumber=? AND status=?";
        try (Connection con = ConnectionProvider.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, roomNumber);
            pst.setString(2, status);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    Customer customer = new Customer();
                    customer.setName(rs.getString("name"));
                    customer.setEmail(rs.getString("email"));
                    customer.setMobile(rs.getString("mobile"));
                    customer.setCheckInDate(rs.getString("date"));
                    customer.setPrice(rs.getString("price"));
                    return customer;
                }
                return null;
            }
        }
    }

    public boolean updateStatusByRoomNumber(String roomNumber, String status) throws SQLException {
        String sql = "update customer set status=? where roomnumber=?";
        try (Connection con = ConnectionProvider.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, status);
            pst.setString(2, roomNumber);
            return pst.executeUpdate() > 0;
        }
    }

    public boolean updateCheckoutDetails(String roomNumber, String checkInDate, String amount, String outDate, String days)
            throws SQLException {
        String sql = "update customer set amount=?,outdate=?,days=? where roomnumber=? AND date=?";
        try (Connection con = ConnectionProvider.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, amount);
            pst.setString(2, outDate);
            pst.setString(3, days);
            pst.setString(4, roomNumber);
            pst.setString(5, checkInDate);
            return pst.executeUpdate() > 0;
        }
    }

    public List<Customer> findByStatusAndOutDate(String status, String outDate) throws SQLException {
        String sql = "Select * from customer where status=? AND outdate=?";
        List<Customer> customers = new ArrayList<>();
        try (Connection con = ConnectionProvider.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, status);
            pst.setString(2, outDate);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    customers.add(mapCustomer(rs));
                }
            }
        }
        return customers;
    }

    public Customer findByBillId(String billId) throws SQLException {
        String sql = "select * from customer where billid=?";
        try (Connection con = ConnectionProvider.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, billId);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return mapCustomer(rs);
                }
                return null;
            }
        }
    }

    private Customer mapCustomer(ResultSet rs) throws SQLException {
        Customer customer = new Customer();
        customer.setBillId(rs.getString("billid"));
        customer.setName(rs.getString("name"));
        customer.setMobile(rs.getString("mobile"));
        customer.setEmail(rs.getString("email"));
        customer.setGender(rs.getString("gender"));
        customer.setAddress(rs.getString("address"));
        customer.setIdNumber(rs.getString("id"));
        customer.setNationality(rs.getString("nationality"));
        customer.setCheckInDate(rs.getString("date"));
        customer.setCheckOutDate(rs.getString("outdate"));
        customer.setRoomNumber(rs.getString("roomnumber"));
        customer.setBed(rs.getString("bed"));
        customer.setRoomType(rs.getString("roomtype"));
        customer.setPrice(rs.getString("price"));
        customer.setStatus(rs.getString("status"));
        customer.setDays(rs.getString("days"));
        customer.setAmount(rs.getString("amount"));
        return customer;
    }
}
