package dev.ajkipp.weather.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Period {
    private String name;
    private String shortForecast;
    private Integer temperature;
}
