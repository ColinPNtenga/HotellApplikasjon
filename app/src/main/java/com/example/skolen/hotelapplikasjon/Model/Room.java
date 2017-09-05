package com.example.skolen.hotelapplikasjon.Model;

/**
 * Created by Skolen on 05.05.2017.
 */

public class Room {
    private String roomType;
    private int roomNumber;
    private String roomPrice;
    private String description;


    public Room() {

    }

    public Room(String roomType, int roomNumber, String roomPrice, String description) {
        this.roomType = roomType;
        this.roomNumber = roomNumber;
        this.roomPrice = roomPrice;
        this.description = description;

    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getRoomPrice() {
        return roomPrice;
    }

    public void setRoomPrice(String roomPrice) {
        this.roomPrice = roomPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
