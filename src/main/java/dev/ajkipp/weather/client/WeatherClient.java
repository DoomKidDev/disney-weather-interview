package dev.ajkipp.weather.client;

import dev.ajkipp.weather.configuration.IntegrationWeatherConfiguration;
import dev.ajkipp.weather.model.WeatherClientResponseBody;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class WeatherClient {
    private final WebClient client;

    public WeatherClient(IntegrationWeatherConfiguration config) {
        this.client = WebClient.create(config.getUrl());
    }

    public Mono<WeatherClientResponseBody> getWeather() {
        return this.client
                .get()
                .uri("/gridpoints/MLB/33,70/forecast")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(WeatherClientResponseBody.class);
    }

}
