package ee.taltech.weathermap.api;

import ee.taltech.weathermap.model.response.WeatherDetailsResponse;
import ee.taltech.weathermap.model.response.forecast.WeatherForecastResponse;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

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
    void shouldReturnWeatherInWeatherDetailsResponseWhenCityNameIsKeila() {
        String cityName = "Keila";

        WeatherDetailsResponse weatherData = api.getWeatherData(cityName);

        assertNotNull(weatherData.getWeather().get(0));
        assertNotNull(weatherData.getWeather().get(0).getMain());
        assertNotNull(weatherData.getWeather().get(0).getDescription());
        assertNotNull(weatherData.getWeather().get(0).getIcon());
    }

    /*
    Forecast data
     */

    @Test
    void shouldReturnCityNameInForecastResponseWhenCityNameIsKeila() {
        String cityName = "Keila";

        WeatherForecastResponse weatherData = api.getWeatherForecast(cityName);

        assertEquals("Keila", weatherData.getCity().getName());
    }

    @Test
    void sohuldReturnForecastDataInForecastResponseWhenCityNameIsKeila() {
        String cityName = "Keila";

        WeatherForecastResponse weatherData = api.getWeatherForecast(cityName);

        assertNotNull(weatherData.getCity());
        assertNotNull(weatherData.getList());
    }

    @Test
    void shouldReturnMinimumThreeDayForecastInForecastResponseWhenCityNameIsKeila() {
        String cityName = "Keila";

        WeatherForecastResponse weatherData = api.getWeatherForecast(cityName);

        assertTrue(weatherData.getList().size() > (3));
    }

    @Test
    void shouldReturnNextThreeDayForecastInForecastResponseWhenCityNameIsKeila() {
        String cityName = "Keila";

        WeatherForecastResponse weatherData = api.getWeatherForecast(cityName);

        Set<Date> dates = new HashSet<>();

        // Generate next 3 days
        for (int i = 1; i <= 3; i++) {
            Date dt = new Date();
            Calendar c = Calendar.getInstance();
            c.setTime(dt);
            c.add(Calendar.DATE, i);
            dates.add(DateUtils.truncate(c.getTime(), Calendar.DAY_OF_MONTH));
        }

        // Response contains next 3 days?
        for (var item : weatherData.getList()) {
            Date formDate = DateUtils.truncate(item.getDate(), Calendar.DAY_OF_MONTH);
            dates.remove(formDate);
            if (dates.isEmpty()) {
                assertTrue(true);
            }
        }
        assertTrue(dates.isEmpty());
    }
}