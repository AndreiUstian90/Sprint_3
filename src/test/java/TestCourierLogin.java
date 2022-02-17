import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.*;
import ru.yandex.praktikum.scooter.api.CourierClient;
import ru.yandex.praktikum.scooter.api.model.Courier;
import ru.yandex.praktikum.scooter.api.model.CourierCredentials;
import ru.yandex.praktikum.scooter.api.model.CourierCredentialsWithoutLogin;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestCourierLogin {

    private CourierClient courierClient;
    private int courierId;

    @BeforeAll
    public void setUp() {
        courierClient = new CourierClient();
    }

    @AfterAll
    public void tearDown() {
        Response response = CourierClient.deleteCourier(courierId);
        Assertions.assertEquals(200, response.statusCode());
    }

    @Test
    @DisplayName("Check status code of courier login with valid data")
    public void courierCanLoginWithValidData() {
        Courier courier = Courier.getRandom();
        CourierClient.createCourier(courier);
        courierId = CourierClient.getCourierId(CourierCredentials.from(courier));
        Response response = CourierClient.loginCourier(CourierCredentials.from(courier));
        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertNotNull(response.getContentType());
    }

    @Test
    @DisplayName("Check status code and response body of courier login without required field - login")
    public void courierCanNotLoginWithoutRequiredFieldLogin() {
        Courier courier = new Courier(RandomStringUtils.randomAlphabetic(10), RandomStringUtils.randomAlphabetic(10), null);
        CourierClient.createCourier(courier);
        courierId = CourierClient.getCourierId(CourierCredentials.from(courier));
        Response response = CourierClient.loginCourierWithoutLogin(CourierCredentialsWithoutLogin.from(courier));
        Assertions.assertEquals(400, response.statusCode());
        Assertions.assertEquals("Недостаточно данных для входа", response.jsonPath().getString("message"));
    }

    @Test
    @DisplayName("Check status code and response body of courier login with Incorrect Login and Password")
    public void courierCanNotLoginWithIncorrectLoginOrPassword() {
        CourierCredentials courierCredentials = new CourierCredentials(RandomStringUtils.randomAlphabetic(10), RandomStringUtils.randomAlphabetic(10));
        Response response = CourierClient.loginCourier(courierCredentials);
        Assertions.assertEquals(404, response.statusCode());
        Assertions.assertEquals("Учетная запись не найдена", response.jsonPath().getString("message"));
    }

}