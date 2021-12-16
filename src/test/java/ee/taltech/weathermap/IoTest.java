package ee.taltech.weathermap;

import com.google.gson.Gson;
import ee.taltech.weathermap.exception.InvalidCityNameException;
import ee.taltech.weathermap.model.CurrentWeatherData;
import ee.taltech.weathermap.model.MainWeatherData;
import ee.taltech.weathermap.model.WeatherReport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IoTest {
    private Io io;
    private Gson gson;

    @BeforeEach
    void setUp() {
        io = new Io();
        gson = new Gson();
    }

    @Test
    public void whenCalledWithBlankCityName_throwsInvalidCityNameException() {
        assertThrows(InvalidCityNameException.class, () -> {
            io.getWeatherReport("");
        });

    }

    @Test
    public void whenCalledWithFictionalCityName_throwsInvalidCityNameException() {
        assertThrows(InvalidCityNameException.class, () -> {
            io.getWeatherReport("xoxo");
        });
    }

    @Test
    public void whenCalledWithCorrectCityName_returnsWeatherReport() {
        assertEquals(WeatherReport.class, gson.fromJson(io.getWeatherReport("Keila"), WeatherReport.class).getClass());

    }

    @Test
    public void shouldIncludeMainDetailsInWeatherReport_whenInputNameIsKeila() {
        assertEquals(MainWeatherData.class, gson.fromJson(io.getWeatherReport("Keila"), WeatherReport.class).getMainDetails().getClass());
    }

    @Test
    public void shouldIncludeCurrentWeatherDataInWeatherData_whenInputNameIsKeila() {
        assertEquals(CurrentWeatherData.class, gson.fromJson(io.getWeatherReport("Keila"), WeatherReport.class).getCurrentWeatherData().getClass());
    }
}