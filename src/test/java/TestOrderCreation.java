import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.*;
import ru.yandex.praktikum.scooter.api.OrdersClient;
import ru.yandex.praktikum.scooter.api.model.OrderCredentials;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestOrderCreation {

    private OrdersClient ordersClient;

    @BeforeAll
    public void setUp() {
        ordersClient = new OrdersClient();
    }

    @Test
    @DisplayName("Check status code of order making with one color - BLACK")
    public void orderCanBeMadeWithOneColorBlack() {
        OrderCredentials orderCredentials = new OrderCredentials(RandomStringUtils.randomAlphabetic(10), RandomStringUtils.randomAlphabetic(10), new String[]{"BLACK"});
        Response response = OrdersClient.makeOrder(orderCredentials);
        Assertions.assertEquals(201, response.statusCode());
        Assertions.assertNotNull(response.getContentType());
    }

    @Test
    @DisplayName("Check status code of order making with two colors - BLACK and GREY")
    public void orderCanBeMadeWithTwoColorsBlackAndGrey() {
        OrderCredentials orderCredentials = new OrderCredentials(RandomStringUtils.randomAlphabetic(10), RandomStringUtils.randomAlphabetic(10), new String[]{"BLACK", "GREY"});
        Response response = OrdersClient.makeOrder(orderCredentials);
        Assertions.assertEquals(201, response.statusCode());
        Assertions.assertNotNull(response.getContentType());
    }

    @Test
    @DisplayName("Check status code of order making without colors")
    public void orderCanBeMadeWithoutColors() {
        OrderCredentials orderCredentials = new OrderCredentials(RandomStringUtils.randomAlphabetic(10), RandomStringUtils.randomAlphabetic(10), null);
        Response response = OrdersClient.makeOrder(orderCredentials);
        Assertions.assertEquals(201, response.statusCode());
        Assertions.assertNotNull(response.getContentType());
    }

}
