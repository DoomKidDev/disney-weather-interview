package dev.ajkipp.weather.api;

import dev.ajkipp.weather.model.GetForecastResponseBody;
import dev.ajkipp.weather.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping("forecast")
    public Mono<GetForecastResponseBody> getForecast() {
        return Mono.just(new GetForecastResponseBody())
                .map(body ->
                        body.setDaily(weatherService.getForecast()));
    }
}
