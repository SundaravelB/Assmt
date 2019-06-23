import org.junit.After;
import org.junit.Test;
import ratpack.http.client.ReceivedResponse;
import ratpack.test.CloseableApplicationUnderTest;
import ratpack.test.MainClassApplicationUnderTest;
import ratpack.test.http.TestHttpClient;

import static org.junit.Assert.assertEquals;

public class MainTest {

    private final CloseableApplicationUnderTest aut = new MainClassApplicationUnderTest(Main.class);
    private final TestHttpClient httpClient = aut.getHttpClient();

    @After
    public void tearDown() throws Exception {
        aut.close();
    }

    @Test
    public void defaultRequest() {
        final ReceivedResponse response = httpClient.get();
        assertEquals(200, response.getStatusCode());
        assertEquals(response.getBody().getText(), "Ratpack Application!");
    }

    @Test
    public void itemGetRequest() {
        final ReceivedResponse response = httpClient.get("item");
        assertEquals(200, response.getStatusCode());
        assertEquals(response.getBody().getText(), "{\"item\":\"Pizza\"}");
    }

    @Test
    public void postRequest() {
        final ReceivedResponse response = httpClient.post("item/22");
        assertEquals(200, response.getStatusCode());
        assertEquals(response.getBody().getText(), "Post Request");
    }

    @Test
    public void putRequest() {
        final ReceivedResponse response = httpClient.put("item/21");
        assertEquals(405, response.getStatusCode());
        assertEquals(response.getBody().getText(), "Put Request");
    }

    @Test
    public void itemIdGetRequest() {
        final ReceivedResponse response = httpClient.get("item/Pizza");
        assertEquals(200, response.getStatusCode());
        assertEquals(response.getBody().getText(), "Item Pizza");
    }
}
