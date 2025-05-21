package dev.ajkipp.weather.model;

import lombok.Data;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.context.annotation.Lazy;

import java.util.List;

@Data
@Accessors(chain = true)
public class GetForecastResponseBody {
    @Lazy
    private List<Daily> daily;
}
