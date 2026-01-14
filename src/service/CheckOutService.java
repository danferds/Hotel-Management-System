package service;

import dao.CustomerDao;
import dao.RoomDao;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import model.Customer;

public class CheckOutService {
    private final CustomerDao customerDao = new CustomerDao();
    private final RoomDao roomDao = new RoomDao();

    public List<Customer> listCheckedInCustomers() {
        try {
            return customerDao.findByStatus("NULL");
        } catch (SQLException ex) {
            return Collections.emptyList();
        }
    }

    public ServiceResult<Customer> findCheckedInCustomer(String roomNumber) {
        try {
            Customer customer = customerDao.findByRoomNumberAndStatus(roomNumber, "NULL");
            if (customer == null) {
                return ServiceResult.failure("Record Not Found.", null);
            }
            return ServiceResult.success(customer);
        } catch (SQLException ex) {
            return ServiceResult.failure("Record Not Found.", null);
        }
    }

    public StayCalculation calculateStay(String checkInDate, String price, boolean showDaysWhenZero) {
        try {
            ZoneId zone = ZoneId.of("Asia/Colombo");
            LocalDate today = LocalDate.now(zone);
            String currentDate = today.toString();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date start = dateFormat.parse(checkInDate);
            Date end = dateFormat.parse(currentDate);
            long diff = end.getTime() - start.getTime();
            int days = (int) (diff / (1000 * 24 * 60 * 60));
            double daily = Double.parseDouble(price);
            String daysText;
            String amountText;
            if (days == 0) {
                daysText = showDaysWhenZero ? "1" : "";
                amountText = String.valueOf(daily);
            } else {
                daysText = String.valueOf(days);
                amountText = String.valueOf(days * daily);
            }
            return new StayCalculation(daysText, amountText);
        } catch (Exception ex) {
            return new StayCalculation("", "");
        }
    }

    public ServiceResult<Void> checkOut(String name, String roomNumber, String amount, String outDate,
                                        String days, String checkInDate) {
        if (name == null || name.isEmpty()) {
            return ServiceResult.failure("Please Enter Room Number And Search it,Then Check Out Customer", null);
        }

        try {
            customerDao.updateStatusByRoomNumber(roomNumber, "check out");
            customerDao.updateCheckoutDetails(roomNumber, checkInDate, amount, outDate, days);
            roomDao.updateStatus(roomNumber, "Not Booked");
            return ServiceResult.success(null);
        } catch (SQLException ex) {
            return ServiceResult.error();
        }
    }
}
