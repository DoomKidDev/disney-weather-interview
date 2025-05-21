package dev.ajkipp.weather.model;

import lombok.Data;

import java.util.List;

@Data
public class GetForecastResponseBody {
    private List<Daily> daily;
}
