package ee.taltech.weathermap;

import ee.taltech.weathermap.api.WeatherApi;
import ee.taltech.weathermap.exception.InvalidCityNameException;
import ee.taltech.weathermap.model.*;
import ee.taltech.weathermap.model.response.Weather;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IoTest {
    private Io io;

    @BeforeEach
    void setUp() {
        io = new Io(new WeatherApi());
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
    public void shouldIncludeCurrentDateInCurrentWeatherData_whenCityNameIsKeila() {
        WeatherReport weatherReport = io.getWeatherReport("Keila");
        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        assertEquals(dateFormat.format(dt), weatherReport.getCurrentWeatherData().getWeatherData().getDate());
    }


    @Test
    public void shouldIncludeMainDetailsInWeatherReport_whenInputNameIsKeila() {
        assertNotNull(io.getWeatherReport("Keila").getMainDetails());
        assertEquals(MainWeatherData.class, io.getWeatherReport("Keila").getMainDetails().getClass());
    }

    @Test
    public void shouldIncludeCurrentWeatherDataInWeatherData_whenInputNameIsKeila() {
        assertNotNull(io.getWeatherReport("Keila").getCurrentWeatherData());
        assertEquals(CurrentWeatherData.class, io.getWeatherReport("Keila").getCurrentWeatherData().getClass());
    }

    @Test
    public void shouldIncludeForecastRepostInForecastReport_whenInputNameIsKeila() {
        assertNotNull(io.getWeatherReport("Keila").getForecastReport());
        assertEquals(ForecastReport.class, io.getWeatherReport("Keila").getForecastReport().getClass());
    }

    @Test
    public void shouldIncludeThreeDayForecastInForecastReport_whenInputNameIsKeila() {
        List<WeatherData> weather = io.getWeatherReport("Keila").getForecastReport().getWeatherData();
        assertTrue(weather.size() >= 3);
        for (int i = 0; i < 3; i++) {
            WeatherData weatherData = weather.get(i);
            Date dt = new Date();
            Calendar c = Calendar.getInstance();
            c.setTime(dt);
            c.add(Calendar.DATE, i + 1);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            assertEquals(dateFormat.format(dt), weatherData.getDate());
        }
    }
}