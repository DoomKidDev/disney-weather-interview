package dev.ajkipp.weather.api.model;

import lombok.Data;

import java.util.List;

@Data
public class GetForecastResponseBody {
    private List<Daily> daily;
}
