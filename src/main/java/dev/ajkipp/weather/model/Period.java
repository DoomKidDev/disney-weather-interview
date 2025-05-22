package dev.ajkipp.weather.model;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Builder
public class Period {
    private String name;
    private String shortForecast;
    private Integer temperature;
    private OffsetDateTime startTime;
}
