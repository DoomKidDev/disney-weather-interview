package dev.ajkipp.weather.api;

import dev.ajkipp.weather.model.GetForecastResponseBody;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static util.TestUtils.readJsonData;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WeatherRouterIT {

    @Autowired
    private WebTestClient webTestClient;

    private static MockWebServer mockBackEnd;

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry r) {
        r.add("integration.weather.url",
                () -> "http://localhost:" + mockBackEnd.getPort());
    }

    @BeforeAll
    static void setUp() throws IOException {
        mockBackEnd = new MockWebServer();
        mockBackEnd.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockBackEnd.shutdown();
    }

    @Test
    void getForecast() {
        mockBackEnd.enqueue(new MockResponse()
                .setBody(readJsonData())
                .addHeader("Content-Type", "application/json"));

        webTestClient
                .get()
                .uri("/forecast")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(GetForecastResponseBody.class).value(body ->
                        assertAll(
                                () -> assertEquals(1, body.getDaily().size()),
                                () -> body.getDaily().forEach(daily ->
                                        assertAll(
                                                () -> assertNotNull(daily.getDayName()),
                                                () -> assertNotNull(daily.getTempHighCelsius()),
                                                () -> assertNotNull(daily.getForecastBlurp())))));
    }
}