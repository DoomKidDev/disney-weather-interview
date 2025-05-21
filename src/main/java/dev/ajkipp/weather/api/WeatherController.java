package dev.ajkipp.weather.api;

import dev.ajkipp.weather.model.GetForecastResponseBody;
import dev.ajkipp.weather.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping("forecast")
    public GetForecastResponseBody getForecast() {
        GetForecastResponseBody body = new GetForecastResponseBody();
        body.setDaily(weatherService.getForecast());
        return body;
    }
}
