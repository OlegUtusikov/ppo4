import org.junit.*;
import org.testcontainers.containers.FixedHostPortGenericContainer;
import org.testcontainers.containers.GenericContainer;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Test {
    @Rule
    public GenericContainer simpleWebServer
            = new FixedHostPortGenericContainer("app:1.0-SNAPSHOT")
            .withFixedExposedPort(8080, 8080)
            .withExposedPorts(8080);


    @org.junit.Test
    public void testPing() throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8080/ping"))
                .GET()
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        assert response.statusCode() == 200;
        assert response.body().equals("ping-pong");
    }

    @org.junit.Test
    public void testCreateUser() throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8080/createUser?id=1&money=22"))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        assert response.body().equals("true");
        request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8080/createUser?id=1&money=22"))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();
        response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        assert response.body().equals("false");
    }

    @org.junit.Test
    public void testCreateStock() throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8080/createStock?id=1&price=22&cnt=1"))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        assert response.body().equals("true");
        request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8080/createStock?id=2&price=-1&cnt=1"))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();
        response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        assert response.body().equals("false");
        request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8080/createStock?id=2&price=1&cnt=-12"))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();
        response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        assert response.body().equals("false");
    }

    @org.junit.Test
    public void testUseCase() throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8080/createStock?id=1&price=22&cnt=10"))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        assert response.body().equals("true");

        request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8080/createUser?id=1&money=220"))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();
        response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        assert response.body().equals("true");

        request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8080/user?id=1"))
                .GET()
                .build();
        response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        assert response.body().equals("User{id: 1, money: 220}");

        request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8080/stock?id=1"))
                .GET()
                .build();
        response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        assert response.body().equals("Stock{id: 1, price: 22, cnt: 10}");

        request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8080/buyStock?userId=1&stockId=1&cnt=4"))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();
        response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        assert response.body().equals("true");

        request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8080/buyStock?userId=1&stockId=1&cnt=10"))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();
        response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        assert response.body().equals("false");

        request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8080/user?id=1"))
                .GET()
                .build();
        response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        assert response.body().equals("User{id: 1, money: 132}");

        request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8080/stock?id=1"))
                .GET()
                .build();
        response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        assert response.body().equals("Stock{id: 1, price: 22, cnt: 6}");

        request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8080/changePrice?id=1&price=30"))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();
        response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        assert response.body().equals("true");

        request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8080/balance?userId=1"))
                .GET()
                .build();
        response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        assert response.body().equals("252");
        request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8080/sellStock?userId=1&stockId=1&cnt=4"))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();
        response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        assert response.body().equals("true");

        request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8080/sellStock?userId=1&stockId=1&cnt=1"))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();
        response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        assert response.body().equals("false");

        request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8080/withdrawUserMoney?userId=1&money=100"))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();
        response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        assert response.body().equals("true");
    }

    @org.junit.Test
    public void testAbsentUserAndStock() throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8080/user?id=1"))
                .GET()
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        assert response.body().equals("__null__");
        request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8080/stock?id=1"))
                .GET()
                .build();
        response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        assert response.body().equals("__null__");
    }
}
