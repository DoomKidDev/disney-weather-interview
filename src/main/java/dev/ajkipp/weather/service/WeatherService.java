package dev.ajkipp.weather.service;

import dev.ajkipp.weather.model.Daily;
import dev.ajkipp.weather.model.Period;
import dev.ajkipp.weather.model.WeatherClientResponseBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class WeatherService {
    public static final String URL = "https://api.weather.gov/gridpoints/MLB/33,70/forecast";
    private final RestTemplate restTemplate;

    public List<Daily> getForecast() {
        WeatherClientResponseBody response = getAPIResponse();
        log.info("Response from URL {} is {}", URL, response.toString());
        List<Period> periods = response.getProperties().getPeriods();
        Daily forecast = Daily.builder()
                .dayName(LocalDate.now().getDayOfWeek().name())
                .tempHighCelsius(getTempHighCelsius(periods))
                .forecastBlurp(getForecastShortDescription(periods))
                .build();
        return List.of(forecast);
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
                .orElseThrow(() -> new RuntimeException("No forecast temps found"));
    }

    static double convertToCelsius(double tempFahrenheit) {
        return (tempFahrenheit - 32) * 5 / 9;
    }

    WeatherClientResponseBody getAPIResponse() {
        return restTemplate.getForObject(URL, WeatherClientResponseBody.class);
    }
}
