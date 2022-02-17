package ru.yandex.praktikum.scooter.api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.yandex.praktikum.scooter.api.model.OrderCredentials;

import static io.restassured.RestAssured.given;

public class OrdersClient extends ScooterRestClient {

    @Step("Make order")
    public static Response makeOrder(OrderCredentials orderCredentials) {
        return given()
                .spec(getBaseSpec())
                .body(orderCredentials)
                .when()
                .post(BASE_URL + "orders/")
                .then()
                .extract()
                .response();
    }

    @Step("Get order list")
    public static Response OrderList() {
        return given()
                .spec(getBaseSpec())
                .get("orders/")
                .then()
                .log().all()
                .extract()
                .response();
    }

}
