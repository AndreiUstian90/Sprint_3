package ru.yandex.praktikum.scooter.api.model;

public class CourierCredentialsWithoutLogin {

    public String password;

    public CourierCredentialsWithoutLogin(String password) {
        this.password = password;
    }

    public static CourierCredentialsWithoutLogin from(Courier courier) {
        return new CourierCredentialsWithoutLogin(courier.password);
    }

}
