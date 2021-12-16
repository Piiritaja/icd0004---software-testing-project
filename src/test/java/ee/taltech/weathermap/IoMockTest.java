package ee.taltech.weathermap;

import ee.taltech.weathermap.api.WeatherApi;
import org.junit.jupiter.api.BeforeEach;

public class IoMockTest {
    private WeatherApi weatherApi;

    @BeforeEach
    void setup(){
        weatherApi = new WeatherApi();
    }
}
