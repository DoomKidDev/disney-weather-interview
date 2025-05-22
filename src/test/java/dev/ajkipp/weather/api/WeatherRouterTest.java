package dev.ajkipp.weather.api;

import dev.ajkipp.weather.model.Daily;
import dev.ajkipp.weather.model.GetForecastResponseBody;
import dev.ajkipp.weather.service.WeatherService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WeatherRouterTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private WeatherService weatherService;

    @Test
    void getForecast() {
        List<Daily> dailies = List.of(Daily.builder()
                .dayName("Monday")
                .tempHighCelsius(27.2)
                .forecastBlurp("Sunny")
                .build());

        Mockito.when(weatherService.getForecast())
                .thenReturn(dailies);

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
                                                () -> assertEquals("Monday", daily.getDayName()),
                                                () -> assertEquals(27.2, daily.getTempHighCelsius()),
                                                () -> assertEquals("Sunny", daily.getForecastBlurp())))));
    }
}