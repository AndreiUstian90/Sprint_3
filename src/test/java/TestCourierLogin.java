import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.*;
import ru.yandex.praktikum.scooter.api.SendDeleteRequest;
import ru.yandex.praktikum.scooter.api.SendPostRequest;
import ru.yandex.praktikum.scooter.api.model.Courier;
import ru.yandex.praktikum.scooter.api.model.CourierCredentials;
import ru.yandex.praktikum.scooter.api.model.CourierCredentialsWithoutLogin;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestCourierLogin {

    private SendPostRequest sendPostRequest;
    private int courierId;

    @BeforeAll
    public void setUp() {
        SendPostRequest sendPostRequest = new SendPostRequest();
    }

    @AfterAll
    public void tearDown() {
        SendDeleteRequest.deleteCourier(courierId);
    }

    @Test
    @DisplayName("Check status code of courier login with valid data")
    public void courierCanLoginWithValidData() {
        Courier courier = Courier.getRandom();
        SendPostRequest.createValidCourier(courier);
        courierId = SendPostRequest.loginCourier(CourierCredentials.from(courier));
        assertThat(courierId, notNullValue());
    }

    @Test
    @DisplayName("Check status code and response body of courier login without required field - login")
    public void courierCanNotLoginWithoutRequiredFieldLogin() {
        Courier courier = new Courier(RandomStringUtils.randomAlphabetic(10), RandomStringUtils.randomAlphabetic(10), null);
        SendPostRequest.createCourier(courier);
        courierId = SendPostRequest.loginCourier(CourierCredentials.from(courier));
        Response response = SendPostRequest.loginCourierWithoutRequiredData(CourierCredentialsWithoutLogin.from(courier));
        response.then().assertThat().statusCode(400)
                .and().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Check status code and response body of courier login with Incorrect Login and Password")
    public void courierCanNotLoginWithIncorrectLoginOrPassword() {
        CourierCredentials courierCredentials = new CourierCredentials(RandomStringUtils.randomAlphabetic(10), RandomStringUtils.randomAlphabetic(10));
        Response response = SendPostRequest.loginCourierWithError(courierCredentials);
        response.then().assertThat().statusCode(404)
                .and().body("message", equalTo("Учетная запись не найдена"));
    }

}
