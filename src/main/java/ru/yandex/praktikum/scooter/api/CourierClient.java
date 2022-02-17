package ru.yandex.praktikum.scooter.api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.yandex.praktikum.scooter.api.model.Courier;
import ru.yandex.praktikum.scooter.api.model.CourierCredentials;
import ru.yandex.praktikum.scooter.api.model.CourierCredentialsWithoutLogin;

import static io.restassured.RestAssured.given;

public class CourierClient extends ScooterRestClient {

    private final static String PATH = BASE_URL + "courier/";

    @Step("Create courier")
    public static Response createCourier(Courier courier) {
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(PATH)
                .then()
                .extract()
                .response();
    }

    @Step("Login courier")
    public static Response loginCourier(CourierCredentials courierCredentials) {
        return given()
                .spec(getBaseSpec())
                .body(courierCredentials)
                .when()
                .post(PATH + "login/")
                .then()
                .extract()
                .response();
    }

    @Step("Login without required field login")
    public static Response loginCourierWithoutLogin(CourierCredentialsWithoutLogin courierCredentialsWithoutLogin) {
        return given()
                .spec(getBaseSpec())
                .body(courierCredentialsWithoutLogin)
                .when()
                .post(PATH + "login/")
                .then()
                .extract()
                .response();
    }

    @Step("Get courier ID, needed to delete courier after tests")
    public static int getCourierId(CourierCredentials courierCredentials) {
        return given()
                .spec(getBaseSpec())
                .body(courierCredentials)
                .when()
                .post(PATH + "login/")
                .then()
                .extract()
                .path("id");
    }

    @Step("Delete courier")
    public static Response deleteCourier(int courierId) {
        return given()
                .spec(getBaseSpec())
                .when()
                .delete(PATH + courierId)
                .then()
                .extract()
                .response();
    }

}
