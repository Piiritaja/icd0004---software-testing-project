package ee.taltech.weathermap;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import ee.taltech.weathermap.api.WeatherApi;
import ee.taltech.weathermap.model.*;
import ee.taltech.weathermap.model.response.*;
import ee.taltech.weathermap.model.response.forecast.WeatherForecastResponse;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class IoMockTest {

    @Mock
    private WeatherApi weatherApi;

    private Io io;

    @BeforeEach
    void setup() {
        io = new Io(weatherApi,"test_output_files/");
    }

    private WeatherDetailsResponse loadMock(String mockCity) throws FileNotFoundException {
        String mockFile = mockCity.toLowerCase(Locale.ROOT) + "Mock.json";
        JsonReader reader = new JsonReader(new FileReader("mockData/" + mockFile));
        return new Gson().fromJson(reader, WeatherDetailsResponse.class);
    }

    @SneakyThrows
    private WeatherForecastResponse loadForecastMock(String mockCity) throws FileNotFoundException {
        String mockFile = mockCity.toLowerCase(Locale.ROOT) + "ForecastMock.json";
        JsonReader reader = new JsonReader(new FileReader("mockData/" + mockFile));
        return new Gson().fromJson(reader, WeatherForecastResponse.class);
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
    public void shouldHaveCoordinatesInMainDetails_whenCityNameKeila() {
        String cityName = "Keila";
        WeatherDetailsResponse weatherDetails = loadMock(cityName);
        when(weatherApi.getWeatherData(cityName)).thenReturn(weatherDetails);
        WeatherReport weatherReport = io.getWeatherReport(cityName);
        assertEquals("59.30,24.41", weatherReport.getMainDetails().getCoordinates());
    }

    @Test
    @SneakyThrows
    public void shouldHaveTemperatureUnitInMainDetails_whenCityNameKeila() {
        String cityName = "Keila";
        WeatherDetailsResponse weatherDetails = loadMock(cityName);
        when(weatherApi.getWeatherData(cityName)).thenReturn(weatherDetails);
        WeatherReport weatherReport = io.getWeatherReport(cityName);
        assertEquals("celsius", weatherReport.getMainDetails().getTemperatureUnit());
    }

    @Test
    @SneakyThrows
    public void shouldHaveForecastInWeatherReport_whenCityNameKeila() {
        String cityName = "Keila";
        WeatherDetailsResponse weatherDetails = loadMock(cityName);
        WeatherForecastResponse weatherForecastResponse = loadForecastMock(cityName);
        when(weatherApi.getWeatherData(cityName)).thenReturn(weatherDetails);
        when(weatherApi.getWeatherForecast(cityName)).thenReturn(weatherForecastResponse);
        WeatherReport weatherReport = io.getWeatherReport(cityName);
        assertNotNull(weatherReport.getForecastReport());
        assertEquals(ForecastReport.class, weatherReport.getForecastReport().getClass());
    }

    @Test
    @SneakyThrows
    public void shouldHaveThreeDaysInForecast_whenCityNameKeila() {
        String cityName = "Keila";
        WeatherDetailsResponse weatherDetails = loadMock(cityName);
        WeatherForecastResponse weatherForecastResponse = loadForecastMock(cityName);
        when(weatherApi.getWeatherData(cityName)).thenReturn(weatherDetails);
        when(weatherApi.getWeatherForecast(cityName)).thenReturn(weatherForecastResponse);
        WeatherReport weatherReport = io.getWeatherReport(cityName);
        assertEquals(3, weatherReport.getForecastReport().getWeatherData().size());
    }

    @Test
    @SneakyThrows
    public void shouldHaveForecastDatesInAscendingOrder_whenCityNameKeila() {
        String cityName = "Keila";
        WeatherDetailsResponse weatherDetails = loadMock(cityName);

        LocalDate mockLocalDate = LocalDate.of(2021, 12, 29);
        try (MockedStatic<LocalDate> topDateTimeUtilMock = Mockito.mockStatic(LocalDate.class)) {
            topDateTimeUtilMock.when(LocalDate::now).thenReturn(mockLocalDate);
            WeatherForecastResponse weatherForecastResponse = loadForecastMock(cityName);
            when(weatherApi.getWeatherData(cityName)).thenReturn(weatherDetails);
            when(weatherApi.getWeatherForecast(cityName)).thenReturn(weatherForecastResponse);
            WeatherReport weatherReport = io.getWeatherReport(cityName);
            assertEquals("2021-12-30", weatherReport.getForecastReport().getWeatherData().get(0).getDate());
            assertEquals("2021-12-31", weatherReport.getForecastReport().getWeatherData().get(1).getDate());
            assertEquals("2022-01-01", weatherReport.getForecastReport().getWeatherData().get(2).getDate());
        }
    }


}
