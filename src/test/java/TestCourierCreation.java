import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.*;
import ru.yandex.praktikum.scooter.api.CourierClient;
import ru.yandex.praktikum.scooter.api.model.Courier;
import ru.yandex.praktikum.scooter.api.model.CourierCredentials;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestCourierCreation {

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
    @DisplayName("Check status code of courier creation with valid data")
    public void courierCanBeCreatedWithValidData() {
        Courier courier = Courier.getRandom();
        Response response = CourierClient.createCourier(courier);
        courierId = CourierClient.getCourierId(CourierCredentials.from(courier));
        Assertions.assertEquals(201, response.statusCode());
        Assertions.assertTrue(true, response.getContentType());
    }

    @Test
    @DisplayName("Check status code of courier creation without firstName")
    public  void courierCanBeCreatedWithoutFirstName() {
        Courier courier = new Courier(RandomStringUtils.randomAlphabetic(10), RandomStringUtils.randomAlphabetic(10), null);
        Response response = CourierClient.createCourier(courier);
        courierId = CourierClient.getCourierId(CourierCredentials.from(courier));
        Assertions.assertEquals(201, response.statusCode());
        Assertions.assertTrue(true, response.getContentType());
    }

    @Test
    @DisplayName("Check status code and response body of courier creation with the same Login")
    public void courierCanNotBeCreatedWithSameLogin() {
        Courier courier = Courier.getRandom();
        Response response1 = CourierClient.createCourier(courier);
        Response response2 = CourierClient.createCourier(courier);
        courierId = CourierClient.getCourierId(CourierCredentials.from(courier));
        Assertions.assertEquals(409, response2.statusCode());
        Assertions.assertEquals("Этот логин уже используется. Попробуйте другой.", response2.jsonPath().getString("message"));
    }

    @Test
    @DisplayName("Check status code and response body of courier creation without required field - Login")
    public void courierCanNotBeCreatedWithoutRequiredFieldLogin() {
        Courier courier = new Courier(null, RandomStringUtils.randomAlphabetic(10), RandomStringUtils.randomAlphabetic(10));
        Response response = CourierClient.createCourier(courier);
        Assertions.assertEquals(400, response.statusCode());
        Assertions.assertEquals("Недостаточно данных для создания учетной записи", response.jsonPath().getString("message"));
    }

    @Test
    @DisplayName("Check status code and response body of courier creation without required field - Password")
    public void courierCanNotBeCreatedWithoutRequiredFieldPassword() {
        Courier courier = new Courier(RandomStringUtils.randomAlphabetic(10), null, RandomStringUtils.randomAlphabetic(10));
        Response response = CourierClient.createCourier(courier);
        Assertions.assertEquals(400, response.statusCode());
        Assertions.assertEquals("Недостаточно данных для создания учетной записи", response.jsonPath().getString("message"));
    }

}
