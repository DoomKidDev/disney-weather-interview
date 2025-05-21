package dev.ajkipp.weather.api;

import dev.ajkipp.weather.api.model.Daily;
import dev.ajkipp.weather.api.model.GetForecastResponseBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WeatherController {
    @GetMapping("forecast")
    public GetForecastResponseBody getForecast() {
        GetForecastResponseBody body = new GetForecastResponseBody();
        Daily daily = Daily.builder()
                .dayName("Monday")
                .tempHighCelsius(27.2)
                .forecastBlurp("Partly Sunny")
                .build();
        body.setDaily(List.of(daily));
        return body;
    }
}
