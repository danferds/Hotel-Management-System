package controller;

import java.util.ArrayList;
import java.util.List;
import model.Customer;
import service.BillingService;
import service.ServiceResult;

public class BillingController {
    private final BillingService billingService = new BillingService();

    public List<Object[]> listCheckedOutRows() {
        return toRows(billingService.listCheckedOutCustomers());
    }

    public ActionResult<List<Object[]>> searchCheckedOutByDate(String outDate) {
        ServiceResult<List<Customer>> result = billingService.listCheckedOutCustomersByDate(outDate);
        if (result.getStatus() == ServiceResult.Status.ERROR) {
            return ActionResult.failure("Record Not Found.");
        }
        return ActionResult.success(toRows(result.getData()));
    }

    public BillInfo findBillInfo(String billId) {
        Customer customer = billingService.findBillById(billId);
        if (customer == null) {
            return null;
        }
        return new BillInfo(
                customer.getName(),
                customer.getMobile(),
                customer.getEmail(),
                customer.getRoomNumber(),
                customer.getBed(),
                customer.getRoomType(),
                customer.getCheckInDate(),
                customer.getCheckOutDate(),
                customer.getPrice(),
                customer.getDays(),
                customer.getAmount()
        );
    }

    private List<Object[]> toRows(List<Customer> customers) {
        List<Object[]> rows = new ArrayList<>();
        for (Customer customer : customers) {
            rows.add(new Object[]{
                customer.getBillId(),
                customer.getRoomNumber(),
                customer.getName(),
                customer.getMobile(),
                customer.getNationality(),
                customer.getGender(),
                customer.getEmail(),
                customer.getIdNumber(),
                customer.getAddress(),
                customer.getCheckInDate(),
                customer.getCheckOutDate(),
                customer.getBed(),
                customer.getRoomType(),
                customer.getPrice(),
                customer.getDays(),
                customer.getAmount()
            });
        }
        return rows;
    }
}
