import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.*;
import ru.yandex.praktikum.scooter.api.SendDeleteRequest;
import ru.yandex.praktikum.scooter.api.SendPostRequest;
import ru.yandex.praktikum.scooter.api.model.OrderCredentials;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestOrderCreation {

    private SendPostRequest sendPostRequest;
    private int orderTrackId;

    @BeforeAll
    public void setUp() {
        SendPostRequest sendPostRequest = new SendPostRequest();
    }

    @AfterAll
    public void tearDown() {
        SendDeleteRequest.delete(orderTrackId);
    }

    @Test
    @DisplayName("Check status code of order making with one color - BLACK")
    public void orderCanBeMadeWithOneColorBlack() {
        OrderCredentials orderCredentials = new OrderCredentials(RandomStringUtils.randomAlphabetic(10), RandomStringUtils.randomAlphabetic(10), new String[]{"BLACK"});
        int orderTrack = SendPostRequest.makeOrder(orderCredentials);
        assertThat(orderTrack, notNullValue());
        orderTrackId = orderTrack;
    }

    @Test
    @DisplayName("Check status code of order making with two colors - BLACK and GREY")
    public void orderCanBeMadeWithTwoColorsBlackAndGrey() {
        OrderCredentials orderCredentials = new OrderCredentials(RandomStringUtils.randomAlphabetic(10), RandomStringUtils.randomAlphabetic(10), new String[]{"BLACK", "GREY"});
        int orderTrack = SendPostRequest.makeOrder(orderCredentials);
        assertThat(orderTrack, notNullValue());
        orderTrackId = orderTrack;
    }

    @Test
    @DisplayName("Check status code of order making without colors")
    public void orderCanBeMadeWithoutColors() {
        OrderCredentials orderCredentials = new OrderCredentials(RandomStringUtils.randomAlphabetic(10), RandomStringUtils.randomAlphabetic(10), null);
        int orderTrack = SendPostRequest.makeOrder(orderCredentials);
        assertThat(orderTrack, notNullValue());
        orderTrackId = orderTrack;
    }

}

