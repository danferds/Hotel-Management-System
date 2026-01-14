package controller;

import java.util.ArrayList;
import java.util.List;
import model.Customer;
import service.CheckOutService;
import service.ServiceResult;
import service.StayCalculation;

public class CheckOutController {
    private final CheckOutService checkOutService = new CheckOutService();

    public List<Object[]> listCheckedInRows() {
        List<Customer> customers = checkOutService.listCheckedInCustomers();
        List<Object[]> rows = new ArrayList<>();
        for (Customer customer : customers) {
            rows.add(new Object[]{
                customer.getName(),
                customer.getMobile(),
                customer.getEmail(),
                customer.getCheckInDate(),
                customer.getNationality(),
                customer.getGender(),
                customer.getIdNumber(),
                customer.getAddress(),
                customer.getRoomNumber(),
                customer.getBed(),
                customer.getRoomType(),
                customer.getPrice()
            });
        }
        return rows;
    }

    public ActionResult<CheckOutInfo> findByRoomNumber(String roomNumber) {
        ServiceResult<Customer> result = checkOutService.findCheckedInCustomer(roomNumber);
        if (result.getStatus() == ServiceResult.Status.FAILURE) {
            return ActionResult.failure(result.getMessage());
        }
        if (result.getStatus() == ServiceResult.Status.ERROR) {
            return ActionResult.failure("Record Not Found.");
        }
        Customer customer = result.getData();
        StayCalculation calculation = checkOutService.calculateStay(
                customer.getCheckInDate(),
                customer.getPrice(),
                false
        );
        CheckOutInfo info = new CheckOutInfo(
                customer.getName(),
                customer.getMobile(),
                customer.getEmail(),
                customer.getCheckInDate(),
                roomNumber,
                customer.getPrice(),
                calculation.getDays(),
                calculation.getAmount()
        );
        return ActionResult.success(info);
    }

    public StayInfo calculateForSelection(String checkInDate, String price) {
        StayCalculation calculation = checkOutService.calculateStay(checkInDate, price, true);
        return new StayInfo(calculation.getDays(), calculation.getAmount());
    }

    public ActionResult<Void> checkOut(String name, String roomNumber, String amount, String outDate,
                                       String days, String checkInDate) {
        ServiceResult<Void> result = checkOutService.checkOut(name, roomNumber, amount, outDate, days, checkInDate);
        if (result.getStatus() == ServiceResult.Status.FAILURE) {
            return ActionResult.failure(result.getMessage());
        }
        if (result.getStatus() == ServiceResult.Status.ERROR) {
            return ActionResult.failure(null);
        }
        return ActionResult.success(null);
    }
}
