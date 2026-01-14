package controller;

public class StayInfo {
    private final String days;
    private final String amount;

    public StayInfo(String days, String amount) {
        this.days = days;
        this.amount = amount;
    }

    public String getDays() {
        return days;
    }

    public String getAmount() {
        return amount;
    }
}
