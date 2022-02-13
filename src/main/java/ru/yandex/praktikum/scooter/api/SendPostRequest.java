package ru.yandex.praktikum.scooter.api;

import io.restassured.response.Response;
import ru.yandex.praktikum.scooter.api.model.Courier;
import ru.yandex.praktikum.scooter.api.model.CourierCredentials;
import ru.yandex.praktikum.scooter.api.model.CourierCredentialsWithoutLogin;
import ru.yandex.praktikum.scooter.api.model.OrderCredentials;

import static io.restassured.RestAssured.given;

public class SendPostRequest extends ScooterRestClient {

    private final static String PATH = BASE_URL + "courier/";

    public static boolean createValidCourier(Courier courier) {
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(PATH)
                .then().assertThat().statusCode(201)
                .and().extract().path("ok");
    }

    public static Response createCourier(Courier courier) {
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(PATH);
    }

    public static int loginCourier(CourierCredentials courierCredentials) {
        return given()
                .spec(getBaseSpec())
                .body(courierCredentials)
                .when()
                .post(PATH + "login/")
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("id");
    }

    public static Response loginCourierWithError(CourierCredentials courierCredentials) {
        return given()
                .spec(getBaseSpec())
                .body(courierCredentials)
                .when()
                .post(PATH + "login/");
    }

    public static Response loginCourierWithoutRequiredData(CourierCredentialsWithoutLogin courierCredentialsWithoutLogin) {
        return given()
                .spec(getBaseSpec())
                .body(courierCredentialsWithoutLogin)
                .when()
                .post(PATH + "login/");
    }

    public static int makeOrder(OrderCredentials orderCredentials) {
        return given()
                .spec(getBaseSpec())
                .body(orderCredentials)
                .when()
                .post(BASE_URL + "orders/")
                .then().assertThat().statusCode(201)
                .and().extract().path("track");
    }

}
