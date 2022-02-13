package ru.yandex.praktikum.scooter.api.model;

public class OrderCredentials {

    public Integer id;
    public Integer courierId;
    public String firstName;
    public String lastName;
    public String[] color;
    public Integer track;
    public Integer status;

    public OrderCredentials(String firstName, String lastName, String[] color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.color = color;
    }

    public OrderCredentials(Integer id, Integer courierId, String firstName, String lastName, Integer track, Integer status) {
        this.id = id;
        this.courierId = courierId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.track = track;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public Integer getCourierId() {
        return courierId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Integer getTrack() {
        return track;
    }

    public Integer getStatus() {
        return status;
    }

}
