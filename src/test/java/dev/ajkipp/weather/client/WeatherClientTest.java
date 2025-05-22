package dev.ajkipp.weather.client;

import dev.ajkipp.weather.configuration.IntegrationWeatherConfiguration;
import dev.ajkipp.weather.model.WeatherClientResponseBody;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.*;
import org.springframework.core.io.ClassPathResource;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.nio.charset.Charset;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

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
    void getWeather() throws IOException {
        mockBackEnd.enqueue(new MockResponse()
                .setBody(readJsonData())
                .addHeader("Content-Type", "application/json"));

        Mono<WeatherClientResponseBody> responseMono = weatherClient.getWeather();

        StepVerifier.create(responseMono)
                .expectNextMatches(responseBody -> responseBody.getProperties().getPeriods().stream()
                        .anyMatch(period ->
                                period.getName().equals("Overnight")
                                && period.getShortForecast().equals("Partly Cloudy")
                                && null != period.getTemperature()))
                .verifyComplete();
    }

    @NotNull
    private static String readJsonData() {
        try {
            return new ClassPathResource("data/forecast-api-response.json").getContentAsString(Charset.defaultCharset());
        } catch (IOException e) {
            fail("Unable to read JSON data");
            return null;
        }
    }
}