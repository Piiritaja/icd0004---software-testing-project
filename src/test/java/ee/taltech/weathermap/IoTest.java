package ee.taltech.weathermap;

import ee.taltech.weathermap.api.WeatherApi;
import ee.taltech.weathermap.exception.FileTypeNotSupportedException;
import ee.taltech.weathermap.exception.InvalidCityNameException;
import ee.taltech.weathermap.model.*;
import ee.taltech.weathermap.model.response.Weather;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IoTest {
    private Io io;

    @BeforeEach
    void setUp() {
        io = new Io(new WeatherApi(), "test_output_files/");
    }

    @Test
    public void whenCalledWithBlankCityName_throwsInvalidCityNameException() {
        assertThrows(InvalidCityNameException.class, () -> io.getWeatherReport(""));

    }

    @Test
    public void whenCalledWithFictionalCityName_throwsInvalidCityNameException() {
        assertThrows(InvalidCityNameException.class, () -> io.getWeatherReport("xoxo"));
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
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate current = LocalDate.now();
        for (int i = 0; i < 3; i++) {
            LocalDate date = current.plusDays(i + 1);
            assertEquals(date.format(dateFormat), weather.get(i).getDate());
        }
    }

    @Test
    public void shouldThrowFileTypeNotSupportedException_whenInputFileJson() {
        String inputFileName = "input_files/wrongType.json";
        assertThrows(FileTypeNotSupportedException.class, () -> io.generateWeatherReport(inputFileName));
    }

    @Test
    public void shouldThrowFileNotFoundException_whenInputFileIsMissing() {
        String inputFileName = "input_files/notFound.txt";
        assertThrows(FileNotFoundException.class, () -> io.generateWeatherReport(inputFileName));
    }

    @Test
    public void shouldWriteWeatherReportToJsonFile_whenInputFileIsTxt() {
        clearFolder(new File("test_output_files"));
        String inputFileName = "input_files/testCity.txt";
        io.generateWeatherReport(inputFileName);
        File f1 = new File("test_output_files/keila_report.json");
        assertTrue(f1.exists());
    }

    private static void clearFolder(File folder) {
        File[] files = folder.listFiles();
        assert files != null;
        for (File f : files) {
            if (f.isDirectory()) {
                clearFolder(f);
            } else {
                f.delete();
            }
        }
    }
}