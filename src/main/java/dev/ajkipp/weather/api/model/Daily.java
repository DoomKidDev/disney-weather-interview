package dev.ajkipp.weather.api.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Daily {
    @JsonAlias("day_name")
    private String dayName;
    @JsonAlias("temp_high_celsius")
    private double tempHighCelsius;
    @JsonAlias("forecast_blurp")
    private String forecastBlurp;
}
