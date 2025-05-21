package dev.ajkipp.weather.api;

import dev.ajkipp.weather.model.Daily;
import dev.ajkipp.weather.service.WeatherService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WeatherControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private WeatherService weatherService;

    @Test
    void getForecast() throws Exception {
        List<Daily> dailies = List.of(Daily.builder()
                .dayName("Monday")
                .tempHighCelsius(27.2)
                .forecastBlurp("Sunny")
                .build());

        Mockito.when(weatherService.getForecast())
                .thenReturn(dailies);

        this.webTestClient.get()
                .uri("/forecast")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.daily[0].day_name").isEqualTo("Monday")
                .jsonPath("$.daily[0].temp_high_celsius").isEqualTo(27.2)
                .jsonPath("$.daily[0].forecast_blurp").isEqualTo("Sunny");
    }
}