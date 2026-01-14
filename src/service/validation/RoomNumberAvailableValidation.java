package service.validation;

import dao.RoomDao;
import java.sql.SQLException;
import service.ServiceResult;

public class RoomNumberAvailableValidation implements ValidationStrategy {
    private final RoomDao roomDao;
    private final String roomNumber;

    public RoomNumberAvailableValidation(RoomDao roomDao, String roomNumber) {
        this.roomDao = roomDao;
        this.roomNumber = roomNumber;
    }

    @Override
    public ServiceResult<Void> validate() {
        try {
            if (roomDao.findByNumber(roomNumber) != null) {
                return ServiceResult.failure("Room Number Already Exist", null);
            }
            return ServiceResult.success(null);
        } catch (SQLException ex) {
            return ServiceResult.error();
        }
    }
}
