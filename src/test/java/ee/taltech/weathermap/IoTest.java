package ee.taltech.weathermap;

import ee.taltech.weathermap.exception.InvalidCityNameException;
import ee.taltech.weathermap.model.CurrentWeatherData;
import ee.taltech.weathermap.model.MainWeatherData;
import ee.taltech.weathermap.model.WeatherReport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IoTest {
    private Io io;

    @BeforeEach
    void setUp() {
        io = new Io();
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
        assertEquals(WeatherReport.class, io.getWeatherReport("Keila").getClass());

    }

    @Test
    public void shouldIncludeMainDetailsInWeatherReport_whenInputNameIsKeila() {
        assertEquals(MainWeatherData.class, io.getWeatherReport("Keila").getMainDetails().getClass());
    }

    @Test
    public void shouldIncludeCurrentWeatherDataInWeatherData_whenInputNameIsKeila() {
        assertEquals(CurrentWeatherData.class, io.getWeatherReport("Keila").getCurrentWeatherData().getClass());
    }
}