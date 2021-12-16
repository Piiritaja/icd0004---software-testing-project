package ee.taltech.weathermap;

import ee.taltech.weathermap.api.WeatherApi;
import ee.taltech.weathermap.model.CurrentWeatherData;
import ee.taltech.weathermap.model.MainWeatherData;
import ee.taltech.weathermap.model.WeatherReport;
import ee.taltech.weathermap.model.response.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class IoMockTest {

    @Mock
    private WeatherApi weatherApi;

    private Io io;

    @BeforeEach
    void setup() {
        io = new Io(weatherApi);
    }

    @Test
    public void shouldReturnMainAndCurrentWeatherDataInReport() {
        String cityName = "Narva";
        WeatherDetailsResponse weatherDetailsResponse = new WeatherDetailsResponse();
        weatherDetailsResponse.setName(cityName);
        weatherDetailsResponse.setMain(new Main());
        weatherDetailsResponse.setCoord(new Coordinates());
        weatherDetailsResponse.setSys(new Sys());
        weatherDetailsResponse.setWind(new Wind());
        weatherDetailsResponse.setClouds(new Clouds());
        when(weatherApi.getWeatherData(cityName)).thenReturn(weatherDetailsResponse);
        WeatherReport weatherReport = io.getWeatherReport(cityName);
        assertEquals(cityName, weatherReport.getMainDetails().getCity());
    }
}
