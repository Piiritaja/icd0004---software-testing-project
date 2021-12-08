package ee.taltech.weathermap.api;

import ee.taltech.weathermap.model.CurrentWeatherData;
import ee.taltech.weathermap.model.response.Coordinates;
import ee.taltech.weathermap.model.response.WeatherDetailsResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WeatherApiTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void shouldReturnCityNameInWeatherDetailsResponseWhenCityNameIsCorrect() {
        String cityName = "Keila";
        WeatherApi weatherApi = new WeatherApi();

        WeatherDetailsResponse weatherData = weatherApi.getCurrentWeatherData(cityName);

        assertEquals(cityName, weatherData.getName());
    }

    @Test
    void shouldReturnCoordinatesInWeatherDetailsResponse_whenCityNameIsCorrect() {
        String cityName = "Keila";
        WeatherApi weatherApi = new WeatherApi();

        WeatherDetailsResponse weatherData = weatherApi.getCurrentWeatherData(cityName);

        Coordinates expectedCoordinates = Coordinates.builder().lon(24.4131f).lat(59.3036f).build();

        assertEquals(expectedCoordinates, weatherData.getCoord());
    }
}