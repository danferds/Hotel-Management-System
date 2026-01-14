package controller;

import java.util.ArrayList;
import java.util.List;
import model.Room;
import service.RoomService;
import service.ServiceResult;

public class RoomController {
    private final RoomService roomService = new RoomService();

    public List<Object[]> listRooms() {
        List<Room> rooms = roomService.listRooms();
        List<Object[]> rows = new ArrayList<>();
        for (Room room : rooms) {
            rows.add(new Object[]{
                room.getRoomNumber(),
                room.getRoomType(),
                room.getBed(),
                room.getPrice(),
                room.getStatus()
            });
        }
        return rows;
    }

    public ActionResult<Void> addRoom(String roomNumber, String roomType, String bed, String price) {
        ServiceResult<Void> result = roomService.addRoom(roomNumber, roomType, bed, price);
        if (result.getStatus() == ServiceResult.Status.FAILURE) {
            return ActionResult.failure(result.getMessage(), result.getField());
        }
        if (result.getStatus() == ServiceResult.Status.ERROR) {
            return ActionResult.failure(null);
        }
        return ActionResult.success(null, "Room Added");
    }

    public ActionResult<Void> deleteRoom(String roomNumber, String status) {
        ServiceResult<Void> result = roomService.deleteRoom(roomNumber, status);
        if (result.getStatus() == ServiceResult.Status.FAILURE) {
            return ActionResult.failure(result.getMessage());
        }
        if (result.getStatus() == ServiceResult.Status.ERROR) {
            return ActionResult.failure(null);
        }
        return ActionResult.success(null);
    }

    public ActionResult<Void> canUpdateRoom(String status) {
        ServiceResult<Void> result = roomService.canUpdateRoom(status);
        if (result.getStatus() == ServiceResult.Status.FAILURE) {
            return ActionResult.failure(result.getMessage());
        }
        return ActionResult.success(null);
    }

    public ActionResult<Void> updateRoom(String roomNumber, String roomType, String bed, String price, String status) {
        ServiceResult<Void> result = roomService.updateRoom(roomNumber, roomType, bed, price, status);
        if (result.getStatus() == ServiceResult.Status.FAILURE) {
            return ActionResult.failure(result.getMessage());
        }
        if (result.getStatus() == ServiceResult.Status.ERROR) {
            return ActionResult.failure(null);
        }
        return ActionResult.success(null, "Room Updated");
    }
}
