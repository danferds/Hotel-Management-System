package controller;

public class CheckOutInfo {
    private final String name;
    private final String mobile;
    private final String email;
    private final String checkInDate;
    private final String roomNumber;
    private final String price;
    private final String days;
    private final String amount;

    public CheckOutInfo(String name, String mobile, String email, String checkInDate,
                        String roomNumber, String price, String days, String amount) {
        this.name = name;
        this.mobile = mobile;
        this.email = email;
        this.checkInDate = checkInDate;
        this.roomNumber = roomNumber;
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

    public String getCheckInDate() {
        return checkInDate;
    }

    public String getRoomNumber() {
        return roomNumber;
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
