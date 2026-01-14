package service;

import dao.CustomerDao;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import model.Customer;

public class BillingService {
    private final CustomerDao customerDao = new CustomerDao();

    public List<Customer> listCheckedOutCustomers() {
        try {
            return customerDao.findByStatus("check out");
        } catch (SQLException ex) {
            return Collections.emptyList();
        }
    }

    public ServiceResult<List<Customer>> listCheckedOutCustomersByDate(String outDate) {
        try {
            return ServiceResult.success(customerDao.findByStatusAndOutDate("check out", outDate));
        } catch (SQLException ex) {
            return ServiceResult.error();
        }
    }

    public Customer findBillById(String billId) {
        try {
            return customerDao.findByBillId(billId);
        } catch (SQLException ex) {
            return null;
        }
    }
}
