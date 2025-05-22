package dev.ajkipp.weather.service;

import dev.ajkipp.weather.model.Period;
import dev.ajkipp.weather.model.Properties;
import dev.ajkipp.weather.model.WeatherClientResponseBody;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
class WeatherServiceTest {
    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private WeatherService weatherService;

    @Test
    void testGetAPIResponse_ExpectNotEmpty() {


//        WeatherClientResponseBody weatherClientResponseBody = new WeatherClientResponseBody();
//        Properties properties = new Properties();
//        Period periodLow = new Period();
//        periodLow.setName("This Afternoon");
//        periodLow.setTemperature(78);
//        periodLow.setShortForecast("Sunny");
//        properties.setPeriods(List.of(periodLow));
//        weatherClientResponseBody.setProperties(properties);
//
//
//        Mockito.when(restTemplate.getForObject(
//                anyString(), eq(WeatherClientResponseBody.class)))
//                .thenReturn(weatherClientResponseBody);
//
//
//        WeatherClientResponseBody apiResponse = weatherService.getAPIResponse();
//        assertFalse(apiResponse.getProperties().getPeriods().get(0).getName().isEmpty());
//        assertFalse(apiResponse.getProperties().getPeriods().get(0).getShortForecast().isEmpty());
//        assertNotEquals(0, apiResponse.getProperties().getPeriods().get(0).getTemperature());
    }

    @Test
    void testGetTempHighCelsius_ExpectMaxConvertedWithOneDecimal() {
        Period periodLow = new Period();
        periodLow.setName("This Afternoon");
        periodLow.setTemperature(78);
        periodLow.setShortForecast("Sunny");

        Period periodHigh = new Period();
        periodHigh.setName("Tonight");
        periodHigh.setTemperature(81);
        periodHigh.setShortForecast("Partly Cloudy");

        double tempC = weatherService.getTempHighCelsius(List.of(periodLow, periodHigh));
        assertEquals(27.2, Math.round(tempC * 10) / 10.0);
    }
}