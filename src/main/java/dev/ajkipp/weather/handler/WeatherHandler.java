package dev.ajkipp.weather.handler;

import dev.ajkipp.weather.model.GetForecastResponseBody;
import dev.ajkipp.weather.service.WeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class WeatherHandler {

    private final WeatherService weatherService;

    public Mono<ServerResponse> getForecast(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(weatherService.getForecast()
                                .map(daily -> new GetForecastResponseBody().setDaily(daily)),
                        GetForecastResponseBody.class);
    }
}
