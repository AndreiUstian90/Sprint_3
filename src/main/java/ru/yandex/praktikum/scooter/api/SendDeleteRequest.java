package ru.yandex.praktikum.scooter.api;

import static io.restassured.RestAssured.given;

public class SendDeleteRequest extends ScooterRestClient {

    private final static String PATH = BASE_URL + "courier/";

    public static boolean deleteCourier(int courierId) {
        return given()
                .spec(getBaseSpec())
                .when()
                .delete(PATH + courierId)
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("ok");
    }

    public static boolean delete(int orderTrackId) {
        return given()
                .spec(getBaseSpec())
                .when()
                .delete(BASE_URL + "orders/cancel/" + orderTrackId)
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("ok");
    }

}
