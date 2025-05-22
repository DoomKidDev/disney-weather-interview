package dev.ajkipp.weather.service;

import dev.ajkipp.weather.client.WeatherClient;
import dev.ajkipp.weather.model.Daily;
import dev.ajkipp.weather.model.Period;
import dev.ajkipp.weather.model.WeatherClientResponseBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class WeatherService {

    private final WeatherClient weatherClient;

    public Mono<List<Daily>> getForecast() {
        WeatherClientResponseBody response = getAPIResponse();
        List<Period> periods = response.getProperties().getPeriods();
        Daily forecast = Daily.builder()
                .dayName(LocalDate.now().getDayOfWeek().name())
                .tempHighCelsius(getTempHighCelsius(periods))
                .forecastBlurp(getForecastShortDescription(periods))
                .build();
        return Mono.just(List.of(forecast));
    }

    WeatherClientResponseBody getAPIResponse() {
        return weatherClient.getWeather().block();
    }

    String getForecastShortDescription(List<Period> periods) {
        return periods.stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No forecast periods found"))
                .getShortForecast();
    }

    double getTempHighCelsius(List<Period> periods) {
        return periods.stream()
                .map(Period::getTemperature)
                .reduce(Math::max)
                .map(WeatherService::convertToCelsius)
                .map(WeatherService::truncateToOneDecimal)
                .orElseThrow(() -> new RuntimeException("No forecast temps found"));
    }

    static double truncateToOneDecimal(double temperature) {
        return Math.round(temperature * 10) / 10.0;
    }

    static double convertToCelsius(double tempFahrenheit) {
        return (tempFahrenheit - 32) * 5 / 9;
    }
}
