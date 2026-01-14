package service;

import dao.CustomerDao;
import dao.RoomDao;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import model.Customer;
import service.validation.LengthValidation;
import service.validation.NumericValidation;
import service.validation.RequiredFieldValidation;
import service.validation.ValidationStrategy;

public class CheckInService {
    private final RoomDao roomDao = new RoomDao();
    private final CustomerDao customerDao = new CustomerDao();

    public List<String> findAvailableRoomNumbers(String roomType, String bed) {
        try {
            return roomDao.findAvailableRoomNumbers(roomType, bed);
        } catch (SQLException ex) {
            return Collections.emptyList();
        }
    }

    public String findRoomPrice(String roomNumber) {
        try {
            return roomDao.findPriceByRoomNumber(roomNumber);
        } catch (SQLException ex) {
            return null;
        }
    }

    public ServiceResult<Void> checkInCustomer(String name, String mobile, String email, String gender,
                                               String nationality, String idNumber, String address,
                                               String date, String roomNumber, String bed, String roomType,
                                               String price) {
        return new ServiceTemplate<Void>() {
            @Override
            protected List<ValidationStrategy> validations() {
                return Arrays.asList(
                        new RequiredFieldValidation(name, "All Field is Requied", "name"),
                        new RequiredFieldValidation(mobile, "All Field is Requied", "mobile"),
                        new RequiredFieldValidation(email, "All Field is Requied", "email"),
                        new RequiredFieldValidation(nationality, "All Field is Requied", "nationality"),
                        new RequiredFieldValidation(idNumber, "All Field is Requied", "idNumber"),
                        new RequiredFieldValidation(address, "All Field is Requied", "address"),
                        new RequiredFieldValidation(price, "Sorry, This type of Room Not avaible Select another room ", "address"),
                        new LengthValidation(mobile, 10, "Mobile Number Should be 10 Digit.", null),
                        new LengthValidation(idNumber, 12, "Adhar Number Should be 12 Digit.", null),
                        new NumericValidation(mobile, "Either Mobile Number or Adhar Number Not valied", null),
                        new NumericValidation(idNumber, "Either Mobile Number or Adhar Number Not valied", null)
                );
            }

            @Override
            protected ServiceResult<Void> perform() {
                Customer customer = new Customer();
                customer.setName(name);
                customer.setMobile(mobile);
                customer.setEmail(email.toLowerCase());
                customer.setGender(gender);
                customer.setAddress(address);
                customer.setIdNumber(idNumber);
                customer.setNationality(nationality);
                customer.setCheckInDate(date);
                customer.setRoomNumber(roomNumber);
                customer.setBed(bed);
                customer.setRoomType(roomType);
                customer.setPrice(price);
                customer.setStatus("NULL");

                try {
                    customerDao.insert(customer);
                    roomDao.updateStatus(roomNumber, "Booked");
                    return ServiceResult.success(null);
                } catch (SQLException ex) {
                    return ServiceResult.error();
                }
            }
        }.execute();
    }
}
