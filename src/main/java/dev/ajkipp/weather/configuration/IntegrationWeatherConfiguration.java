package dev.ajkipp.weather.configuration;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("integration.weather")
@Data
@Accessors(chain = true)
public class IntegrationWeatherConfiguration {
    private String url;
}
