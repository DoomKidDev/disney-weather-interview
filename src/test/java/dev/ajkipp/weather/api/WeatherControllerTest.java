package dev.ajkipp.weather.api;

import dev.ajkipp.weather.model.Period;
import dev.ajkipp.weather.model.Properties;
import dev.ajkipp.weather.model.WeatherClientResponseBody;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WeatherController.class)
class WeatherControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void getForecast() throws Exception {

        WeatherClientResponseBody weatherClientResponseBody = new WeatherClientResponseBody();
        Properties properties = new Properties();
        Period period = new Period();
        period.setTemperature(81);
        period.setShortForecast("Partly Sunny");

        mvc.perform(get("/forecast"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.daily[0].day_name").value(LocalDate.now().getDayOfWeek().name()))
                .andExpect(jsonPath("$.daily[0].temp_high_celsius").value(27.2))
                .andExpect(jsonPath("$.daily[0].forecast_blurp").value("Partly Sunny"));
    }
}