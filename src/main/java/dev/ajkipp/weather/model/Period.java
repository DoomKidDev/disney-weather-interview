package dev.ajkipp.weather.model;

import lombok.Data;

@Data
public class Period {
    private String name;
    private String shortForecast;
    private int temperature;
}
