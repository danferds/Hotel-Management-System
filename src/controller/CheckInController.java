package controller;

import java.util.List;
import service.CheckInService;
import service.ServiceResult;

public class CheckInController {
    private final CheckInService checkInService = new CheckInService();

    public List<String> listAvailableRoomNumbers(String roomType, String bed) {
        return checkInService.findAvailableRoomNumbers(roomType, bed);
    }

    public String findRoomPrice(String roomNumber) {
        return checkInService.findRoomPrice(roomNumber);
    }

    public ActionResult<Void> checkInCustomer(String name, String mobile, String email, String gender,
                                              String nationality, String idNumber, String address,
                                              String date, String roomNumber, String bed, String roomType,
                                              String price) {
        ServiceResult<Void> result = checkInService.checkInCustomer(
                name, mobile, email, gender, nationality, idNumber, address,
                date, roomNumber, bed, roomType, price);
        if (result.getStatus() == ServiceResult.Status.FAILURE) {
            return ActionResult.failure(result.getMessage(), result.getField());
        }
        if (result.getStatus() == ServiceResult.Status.ERROR) {
            return ActionResult.failure(null);
        }
        return ActionResult.success(null, "Room Alloted");
    }
}
