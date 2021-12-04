package ee.taltech.weathermap.api;

import ee.taltech.weathermap.model.CurrentWeatherData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WeatherApiTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void shouldReturnCityNameInCurrentWeatherDataWhenCityNameIsCorrect() {
        String cityName = "Keila";
        WeatherApi weatherApi = new WeatherApi();

        CurrentWeatherData weatherData = weatherApi.getCurrentWeatherData(cityName);

        assertEquals(cityName, weatherData.getName());
    }
}