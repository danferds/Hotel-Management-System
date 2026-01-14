package controller;

public class BillInfo {
    private final String name;
    private final String mobile;
    private final String email;
    private final String roomNumber;
    private final String bed;
    private final String roomType;
    private final String checkInDate;
    private final String checkOutDate;
    private final String price;
    private final String days;
    private final String amount;

    public BillInfo(String name, String mobile, String email, String roomNumber, String bed, String roomType,
                    String checkInDate, String checkOutDate, String price, String days, String amount) {
        this.name = name;
        this.mobile = mobile;
        this.email = email;
        this.roomNumber = roomNumber;
        this.bed = bed;
        this.roomType = roomType;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.price = price;
        this.days = days;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public String getMobile() {
        return mobile;
    }

    public String getEmail() {
        return email;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public String getBed() {
        return bed;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public String getPrice() {
        return price;
    }

    public String getDays() {
        return days;
    }

    public String getAmount() {
        return amount;
    }
}
