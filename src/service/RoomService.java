package service;

import dao.RoomDao;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import model.Room;
import service.validation.DecimalValidation;
import service.validation.RequiredFieldValidation;
import service.validation.RoomNumberAvailableValidation;
import service.validation.ValidationStrategy;

public class RoomService {
    private final RoomDao roomDao = new RoomDao();

    public List<Room> listRooms() {
        try {
            return roomDao.findAll();
        } catch (SQLException ex) {
            return Collections.emptyList();
        }
    }

    public ServiceResult<Void> addRoom(String roomNumber, String roomType, String bed, String price) {
        return new ServiceTemplate<Void>() {
            @Override
            protected List<ValidationStrategy> validations() {
                return Arrays.asList(
                        new RequiredFieldValidation(roomNumber, "All Field is Requied", "roomNumber"),
                        new RequiredFieldValidation(price, "All Field is Requied", "price"),
                        new RoomNumberAvailableValidation(roomDao, roomNumber),
                        new DecimalValidation(price, "Price is not valied", null)
                );
            }

            @Override
            protected ServiceResult<Void> perform() {
                try {
                    Room room = new Room(roomNumber, roomType, bed, price, "Not Booked");
                    roomDao.insert(room);
                    return ServiceResult.success(null);
                } catch (SQLException ex) {
                    return ServiceResult.error();
                }
            }
        }.execute();
    }

    public ServiceResult<Void> deleteRoom(String roomNumber, String status) {
        if (status != null && "booked".equalsIgnoreCase(status)) {
            return ServiceResult.failure("Sorry Room is Booked So unable to delete it", null);
        }
        try {
            roomDao.deleteByNumber(roomNumber);
            return ServiceResult.success(null);
        } catch (SQLException ex) {
            return ServiceResult.error();
        }
    }

    public ServiceResult<Void> canUpdateRoom(String status) {
        if (status != null && "booked".equalsIgnoreCase(status)) {
            return ServiceResult.failure("Sorry Room is Booked So unable to Update it", null);
        }
        return ServiceResult.success(null);
    }

    public ServiceResult<Void> updateRoom(String roomNumber, String roomType, String bed, String price, String status) {
        if (price == null || price.isEmpty()) {
            return ServiceResult.failure("All Field id Required", null);
        }
        if (status != null && "booked".equalsIgnoreCase(status)) {
            return ServiceResult.failure("Sorry Room is Booked So unable to Update it", null);
        }

        try {
            Room room = new Room(roomNumber, roomType, bed, price, status);
            roomDao.update(room);
            return ServiceResult.success(null);
        } catch (SQLException ex) {
            return ServiceResult.error();
        }
    }
}
