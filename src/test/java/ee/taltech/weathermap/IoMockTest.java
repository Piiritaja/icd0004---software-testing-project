package ee.taltech.weathermap;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import ee.taltech.weathermap.api.WeatherApi;
import ee.taltech.weathermap.model.CurrentWeatherData;
import ee.taltech.weathermap.model.MainWeatherData;
import ee.taltech.weathermap.model.WeatherReport;
import ee.taltech.weathermap.model.response.*;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Locale;

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

    private WeatherDetailsResponse loadMock(String mockCity) throws FileNotFoundException {
        String mockFile = mockCity.toLowerCase(Locale.ROOT) + "Mock.json";
        JsonReader reader = new JsonReader(new FileReader("mockData/" + mockFile));
        return new Gson().fromJson(reader, WeatherDetailsResponse.class);
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

    @Test
    @SneakyThrows
    public void shouldHaveCityNameInMainDetails_whenCityNameKeila() {
        String cityName = "Keila";
        WeatherDetailsResponse weatherDetails = loadMock(cityName);
        when(weatherApi.getWeatherData(cityName)).thenReturn(weatherDetails);
        WeatherReport weatherReport = io.getWeatherReport(cityName);
        assertEquals(cityName, weatherReport.getMainDetails().getCity());

    }

    @Test
    @SneakyThrows
    public void shouldHaveCoordinatesInMainDetails_whenCityNameKeila(){
        String cityName = "Keila";
        WeatherDetailsResponse weatherDetails = loadMock(cityName);
        when(weatherApi.getWeatherData(cityName)).thenReturn(weatherDetails);
        WeatherReport weatherReport = io.getWeatherReport(cityName);
        assertEquals("59.30,24.41",weatherReport.getMainDetails().getCoordinates());
    }

    @Test
    @SneakyThrows
    public void shouldHaveTemperatureUnitInMainDetails_whenCityNameKeila(){
        String cityName = "Keila";
        WeatherDetailsResponse weatherDetails = loadMock(cityName);
        when(weatherApi.getWeatherData(cityName)).thenReturn(weatherDetails);
        WeatherReport weatherReport = io.getWeatherReport(cityName);
        assertEquals("celsius", weatherReport.getMainDetails().getTemperatureUnit());
    }
}
