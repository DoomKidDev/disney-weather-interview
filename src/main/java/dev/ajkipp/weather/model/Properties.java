package dev.ajkipp.weather.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Properties {
    private List<Period> periods;
}
