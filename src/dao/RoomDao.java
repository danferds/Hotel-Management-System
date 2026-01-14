package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Room;

public class RoomDao {
    public List<Room> findAll() throws SQLException {
        String sql = "select * from room";
        List<Room> rooms = new ArrayList<>();
        try (Connection con = ConnectionProvider.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                rooms.add(mapRoom(rs));
            }
        }
        return rooms;
    }

    public Room findByNumber(String roomNumber) throws SQLException {
        String sql = "select * from room where roomnumber=?";
        try (Connection con = ConnectionProvider.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, roomNumber);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return mapRoom(rs);
                }
                return null;
            }
        }
    }

    public boolean insert(Room room) throws SQLException {
        String sql = "insert into room(roomnumber,roomtype,bed,price,status)values(?,?,?,?,?)";
        try (Connection con = ConnectionProvider.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, room.getRoomNumber());
            pst.setString(2, room.getRoomType());
            pst.setString(3, room.getBed());
            pst.setString(4, room.getPrice());
            pst.setString(5, room.getStatus());
            return pst.executeUpdate() > 0;
        }
    }

    public boolean update(Room room) throws SQLException {
        String sql = "update room set price=?,roomtype=?,bed=? where roomnumber=?";
        try (Connection con = ConnectionProvider.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, room.getPrice());
            pst.setString(2, room.getRoomType());
            pst.setString(3, room.getBed());
            pst.setString(4, room.getRoomNumber());
            return pst.executeUpdate() > 0;
        }
    }

    public boolean deleteByNumber(String roomNumber) throws SQLException {
        String sql = "delete from room where roomnumber=?";
        try (Connection con = ConnectionProvider.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, roomNumber);
            return pst.executeUpdate() > 0;
        }
    }

    public boolean updateStatus(String roomNumber, String status) throws SQLException {
        String sql = "update room set status=? where roomnumber=?";
        try (Connection con = ConnectionProvider.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, status);
            pst.setString(2, roomNumber);
            return pst.executeUpdate() > 0;
        }
    }

    public List<String> findAvailableRoomNumbers(String roomType, String bed) throws SQLException {
        String sql = "select roomnumber from room where status=? AND roomtype=? AND bed=?";
        List<String> roomNumbers = new ArrayList<>();
        try (Connection con = ConnectionProvider.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, "Not Booked");
            pst.setString(2, roomType);
            pst.setString(3, bed);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    roomNumbers.add(rs.getString("roomnumber"));
                }
            }
        }
        return roomNumbers;
    }

    public String findPriceByRoomNumber(String roomNumber) throws SQLException {
        String sql = "select price from room where roomnumber=?";
        try (Connection con = ConnectionProvider.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, roomNumber);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("price");
                }
                return null;
            }
        }
    }

    private Room mapRoom(ResultSet rs) throws SQLException {
        Room room = new Room();
        room.setRoomNumber(rs.getString("roomnumber"));
        room.setRoomType(rs.getString("roomtype"));
        room.setBed(rs.getString("bed"));
        room.setPrice(rs.getString("price"));
        room.setStatus(rs.getString("status"));
        return room;
    }
}
