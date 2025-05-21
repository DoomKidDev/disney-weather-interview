package dev.ajkipp.weather.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Daily {
    @JsonProperty("day_name")
    private String dayName;
    @JsonProperty("temp_high_celsius")
    private double tempHighCelsius;
    @JsonProperty("forecast_blurp")
    private String forecastBlurp;
}
