package ee.taltech.weathermap.api;

import ee.taltech.weathermap.model.response.Coordinates;
import ee.taltech.weathermap.model.response.WeatherDetailsResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WeatherApiTest {

    private WeatherApi api;

    @BeforeEach
    void setUp() {
        api = new WeatherApi();
    }

    @Test
    void shouldReturnCityNameInWeatherDetailsResponseWhenCityNameIsCorrect() {
        String cityName = "Keila";

        WeatherDetailsResponse weatherData = api.getWeatherData(cityName);

        assertEquals(cityName, weatherData.getName());
    }

    @Test
    void shouldReturnWeatherInWeatherDetailsResponse_whenCityNameIsKeila() {
        String cityName = "Keila";

        WeatherDetailsResponse weatherData = api.getWeatherData(cityName);

        assertNotNull(weatherData.getWeather().get(0));
        assertNotNull(weatherData.getWeather().get(0).getMain());
        assertNotNull(weatherData.getWeather().get(0).getDescription());
        assertNotNull(weatherData.getWeather().get(0).getIcon());
    }
}