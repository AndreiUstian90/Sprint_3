import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.*;
import ru.yandex.praktikum.scooter.api.SendDeleteRequest;
import ru.yandex.praktikum.scooter.api.SendPostRequest;
import ru.yandex.praktikum.scooter.api.model.Courier;
import ru.yandex.praktikum.scooter.api.model.CourierCredentials;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestCourierCreation {

    private SendPostRequest sendPostRequest;
    private int courierId;

    @BeforeAll
    public void setUp() {SendPostRequest sendPostRequest = new SendPostRequest();}

    @AfterAll
    public void tearDown() {SendDeleteRequest.deleteCourier(courierId);}

    @Test
    @DisplayName("Check status code of courier creation with valid data")
    public void courierCanBeCreatedWithValidData() {
        Courier courier = Courier.getRandom();
        boolean isCourierCreated = SendPostRequest.createValidCourier(courier);
        courierId = SendPostRequest.loginCourier(CourierCredentials.from(courier));
        assertTrue(isCourierCreated);
        assertThat(courierId, notNullValue());
    }

    @Test
    @DisplayName("Check status code of courier creation without firstName")
    public void courierCanBeCreatedWithoutFirstName() {
        Courier courier = new Courier(RandomStringUtils.randomAlphabetic(10), RandomStringUtils.randomAlphabetic(10), null);
        boolean isCourierCreated = SendPostRequest.createValidCourier(courier);
        courierId = SendPostRequest.loginCourier(CourierCredentials.from(courier));
        assertTrue(isCourierCreated);
        assertThat(courierId, notNullValue());
    }

    @Test
    @DisplayName("Check status code and response body of courier creation with the same Login")
    public void courierCanNotBeCreatedWithSameLogin() {
        Courier courier = Courier.getRandom();
        Response response = SendPostRequest.createCourier(courier);
        Response response1 = SendPostRequest.createCourier(courier);
        courierId = SendPostRequest.loginCourier(CourierCredentials.from(courier));
        response1.then().statusCode(409)
                .and().assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    @DisplayName("Check status code and response body of courier creation without required field - Login")
    public void courierCanNotBeCreatedWithoutRequiredFieldLogin() {
        Courier courier = new Courier(null, RandomStringUtils.randomAlphabetic(10), RandomStringUtils.randomAlphabetic(10));
        Response response = SendPostRequest.createCourier(courier);
        response.then().statusCode(400)
                .and().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Check status code and response body of courier creation without required field - Password")
    public void courierCanNotBeCreatedWithoutRequiredFieldPassword() {
        Courier courier = new Courier(RandomStringUtils.randomAlphabetic(10), null, RandomStringUtils.randomAlphabetic(10));
        Response response = SendPostRequest.createCourier(courier);
        response.then().statusCode(400)
                .and().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

}

