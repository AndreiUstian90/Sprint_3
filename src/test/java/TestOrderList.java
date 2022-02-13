import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import ru.yandex.praktikum.scooter.api.SendGetRequest;
import ru.yandex.praktikum.scooter.api.model.OrderCredentials;

import java.util.List;

import static org.hamcrest.Matchers.notNullValue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestOrderList {

    private SendGetRequest sendGetRequest;

    @BeforeAll
    public void setUp() {
        SendGetRequest sendPostRequest = new SendGetRequest();
    }

    @Test
    @DisplayName("Check status code of getting order's list")
    public void getOrderList() {
        List<OrderCredentials> orders = SendGetRequest.OrderList();
        MatcherAssert.assertThat(orders, notNullValue());
    }

}
