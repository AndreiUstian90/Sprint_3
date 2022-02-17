import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import ru.yandex.praktikum.scooter.api.OrdersClient;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestOrderList {

    private OrdersClient ordersClient;

    @BeforeAll
    public void setUp() {
        ordersClient = new OrdersClient();
    }

    @Test
    @DisplayName("Check status code of getting order's list")
    public void getOrderList1() {
        Response response = OrdersClient.OrderList();
        System.out.println(response);
        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertNotNull(response.jsonPath().getList("orders"));
    }

}
