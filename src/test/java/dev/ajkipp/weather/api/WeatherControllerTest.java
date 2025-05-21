package dev.ajkipp.weather.api;

import dev.ajkipp.weather.api.model.Daily;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WeatherController.class)
class TestWeatherController {

    @Autowired
    private MockMvc mvc;

    @Test
    void getForecast() throws Exception {
        List<Daily> mockForecast = List.of(
                Daily.builder()
                        .dayName("Monday")
                        .tempHighCelsius(27.2)
                        .forecastBlurp("Partly Sunny")
                        .build());

        mvc.perform(get("/forecast"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.daily[0].day_name").value("Monday"))
                .andExpect(jsonPath("$.daily[0].temp_high_celsius").value(27.2))
                .andExpect(jsonPath("$.daily[0].forecast_blurp").value("Partly Sunny"));
    }
}