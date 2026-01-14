package model;

public class Room {
    private String roomNumber;
    private String roomType;
    private String bed;
    private String price;
    private String status;

    public Room() {
    }

    public Room(String roomNumber, String roomType, String bed, String price, String status) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.bed = bed;
        this.price = price;
        this.status = status;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getBed() {
        return bed;
    }

    public void setBed(String bed) {
        this.bed = bed;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
