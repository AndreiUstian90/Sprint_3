package ru.yandex.praktikum.scooter.api;

import ru.yandex.praktikum.scooter.api.model.OrderCredentials;

import java.util.List;

import static io.restassured.RestAssured.given;

public class SendGetRequest extends ScooterRestClient{

    public static List<OrderCredentials> OrderList() {
        return given()
                .spec(getBaseSpec())
                .get("orders/")
                .then().statusCode(200)
                .log().all()
                .extract().body().jsonPath().getList("orders", OrderCredentials.class);
    }

}
