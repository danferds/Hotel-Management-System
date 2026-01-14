package facade;

import controller.ActionResult;
import controller.AdminController;
import controller.AuthController;
import controller.BillInfo;
import controller.BillingController;
import controller.CheckInController;
import controller.CheckOutController;
import controller.CheckOutInfo;
import controller.LoginStatus;
import controller.RoomController;
import controller.StayInfo;
import java.util.List;

public class AppFacade {
    private final AuthController authController = new AuthController();
    private final AdminController adminController = new AdminController();
    private final RoomController roomController = new RoomController();
    private final CheckInController checkInController = new CheckInController();
    private final CheckOutController checkOutController = new CheckOutController();
    private final BillingController billingController = new BillingController();

    public ActionResult<LoginStatus> login(String email, String password) {
        return authController.login(email, password);
    }

    public ActionResult<Void> registerUser(String name, String email, String password, String question, String answer,
                                           String successMessage) {
        return authController.registerUser(name, email, password, question, answer, successMessage);
    }

    public ActionResult<String> findSecurityQuestion(String email) {
        return authController.findSecurityQuestion(email);
    }

    public ActionResult<Void> resetPasswordWithEmail(String email, String securityQuestion, String answer,
                                                     String newPassword, String emptyPasswordMessage,
                                                     String successMessage) {
        return authController.resetPasswordWithEmail(
                email, securityQuestion, answer, newPassword, emptyPasswordMessage, successMessage);
    }

    public ActionResult<Void> resetPasswordByAnswerOnly(String email, String securityQuestion, String answer,
                                                        String newPassword, String emptyPasswordMessage,
                                                        String successMessage) {
        return authController.resetPasswordByAnswerOnly(
                email, securityQuestion, answer, newPassword, emptyPasswordMessage, successMessage);
    }

    public List<Object[]> listAllUsers() {
        return adminController.listAllUsers();
    }

    public ActionResult<List<Object[]>> searchUserByEmail(String email) {
        return adminController.searchByEmail(email);
    }

    public void updateUserStatus(String email, String status) {
        adminController.updateStatus(email, status);
    }

    public List<Object[]> listRooms() {
        return roomController.listRooms();
    }

    public ActionResult<Void> addRoom(String roomNumber, String roomType, String bed, String price) {
        return roomController.addRoom(roomNumber, roomType, bed, price);
    }

    public ActionResult<Void> deleteRoom(String roomNumber, String status) {
        return roomController.deleteRoom(roomNumber, status);
    }

    public ActionResult<Void> canUpdateRoom(String status) {
        return roomController.canUpdateRoom(status);
    }

    public ActionResult<Void> updateRoom(String roomNumber, String roomType, String bed, String price, String status) {
        return roomController.updateRoom(roomNumber, roomType, bed, price, status);
    }

    public List<String> listAvailableRoomNumbers(String roomType, String bed) {
        return checkInController.listAvailableRoomNumbers(roomType, bed);
    }

    public String findRoomPrice(String roomNumber) {
        return checkInController.findRoomPrice(roomNumber);
    }

    public ActionResult<Void> checkInCustomer(String name, String mobile, String email, String gender,
                                              String nationality, String idNumber, String address,
                                              String date, String roomNumber, String bed, String roomType,
                                              String price) {
        return checkInController.checkInCustomer(
                name, mobile, email, gender, nationality, idNumber, address,
                date, roomNumber, bed, roomType, price
        );
    }

    public List<Object[]> listCheckedInRows() {
        return checkOutController.listCheckedInRows();
    }

    public ActionResult<CheckOutInfo> findCheckedInCustomer(String roomNumber) {
        return checkOutController.findByRoomNumber(roomNumber);
    }

    public StayInfo calculateForSelection(String checkInDate, String price) {
        return checkOutController.calculateForSelection(checkInDate, price);
    }

    public ActionResult<Void> checkOut(String name, String roomNumber, String amount, String outDate,
                                       String days, String checkInDate) {
        return checkOutController.checkOut(name, roomNumber, amount, outDate, days, checkInDate);
    }

    public List<Object[]> listCheckedOutRows() {
        return billingController.listCheckedOutRows();
    }

    public ActionResult<List<Object[]>> searchCheckedOutByDate(String outDate) {
        return billingController.searchCheckedOutByDate(outDate);
    }

    public BillInfo findBillInfo(String billId) {
        return billingController.findBillInfo(billId);
    }
}
