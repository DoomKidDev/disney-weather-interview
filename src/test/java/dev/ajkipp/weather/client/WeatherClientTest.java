package dev.ajkipp.weather.client;

import dev.ajkipp.weather.configuration.IntegrationWeatherConfiguration;
import dev.ajkipp.weather.model.WeatherClientResponseBody;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static util.TestUtils.readJsonData;

class WeatherClientTest {

    private WeatherClient weatherClient;

    private static MockWebServer mockBackEnd;

    @BeforeAll
    static void setUp() throws IOException {
        mockBackEnd = new MockWebServer();
        mockBackEnd.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockBackEnd.shutdown();
    }

    @BeforeEach
    void initialize() {
        String baseUrl = "http://localhost:" + mockBackEnd.getPort();
        IntegrationWeatherConfiguration configuration = new IntegrationWeatherConfiguration()
                .setUrl(baseUrl);
        weatherClient = new WeatherClient(configuration);
    }

    @Test
    void getWeather() {
        mockBackEnd.enqueue(new MockResponse()
                .setBody(readJsonData())
                .addHeader("Content-Type", "application/json"));

        Mono<WeatherClientResponseBody> responseMono = weatherClient.getWeather();

        StepVerifier.create(responseMono)
                .assertNext(responseBody ->
                        assertAll(
                                () -> assertTrue(responseBody.getProperties().getPeriods().size() > 1),
                                () -> responseBody.getProperties().getPeriods()
                                        .forEach(period -> assertAll(
                                                () -> assertNotNull(period.getName()),
                                                () -> assertNotNull(period.getTemperature()),
                                                () -> assertNotNull(period.getShortForecast())))))
                .verifyComplete();
    }


}