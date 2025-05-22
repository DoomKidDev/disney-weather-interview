package dev.ajkipp.weather.service;

import dev.ajkipp.weather.client.WeatherClient;
import dev.ajkipp.weather.model.Daily;
import dev.ajkipp.weather.model.Period;
import dev.ajkipp.weather.model.Properties;
import dev.ajkipp.weather.model.WeatherClientResponseBody;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WeatherServiceTest {

    @Mock
    private WeatherClient weatherClient;
    @InjectMocks
    private WeatherService weatherService;

    @Test
    void getForecast_ExpectTodayConvertedTempMatchingBlurb() {
        Mono<WeatherClientResponseBody> responseMono = Mono.just(
                WeatherClientResponseBody.builder()
                        .properties(Properties.builder()
                                .periods(List.of(Period.builder()
                                        .name("Tonight")
                                        .temperature(81)
                                        .shortForecast("Sunny")
                                        .build()))
                                .build())
                        .build());

        when(weatherClient.getWeather())
                .thenReturn(responseMono);

        Mono<List<Daily>> dailiesMono = weatherService.getForecast();

        StepVerifier.create(dailiesMono)
                .assertNext(dailies ->
                        assertAll(
                                () -> assertEquals(1, dailies.size()),
                                () -> dailies.forEach(daily ->
                                        assertAll(
                                                () -> assertEquals(LocalDate.now().getDayOfWeek().name(), daily.getDayName()),
                                                () -> assertEquals(27.2, daily.getTempHighCelsius()),
                                                () -> assertEquals("Sunny", daily.getForecastBlurp())))))
                .verifyComplete();
    }

    @Test
    void getTempHighCelsius_ExpectMaxConvertedWithOneDecimal() {
        Period periodLow = Period.builder()
                .name("This Afternoon")
                .temperature(78)
                .shortForecast("Sunny")
                .build();

        Period periodHigh = Period.builder()
                .name("Tonight")
                .temperature(81)
                .shortForecast("Partly Cloudy")
                .build();

        double tempC = weatherService.getTempHighCelsius(List.of(periodLow, periodHigh));
        assertEquals(27.2, tempC);
    }
}