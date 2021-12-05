package ee.taltech.weathermap.api;

import org.junit.jupiter.api.Test;


public class OpenWeatherMapTests {
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather";

    /*
    SMOKE tests
     */
    @Test
    public void whenCalledWithoutApiKeyReturnHttpUnauthorized() {
        //TODO
    }

    @Test
    public void shouldReturnHttpOkWhenCityNameIsGiven() {
        //TODO
    }

    @Test
    public void shouldReturnCityNameWhenCityNameIsGiven() {
        //TODO
    }

    @Test
    public void shouldHaveCoordinatesBlockWhenCorrectCityNameIsGiven() {
        //TODO
    }
}
