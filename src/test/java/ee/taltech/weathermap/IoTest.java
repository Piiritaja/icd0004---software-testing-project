package ee.taltech.weathermap;

import com.google.gson.Gson;
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
import java.io.FileReader;
import java.io.IOException;
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
        clearFolder(new File("test_output_files"));
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
        String inputFileName = "input_files/testCity.txt";
        io.generateWeatherReport(inputFileName);
        File f1 = new File("test_output_files/keila_report.json");
        assertTrue(f1.exists());
    }

    @Test
    public void shouldWriteKeilaReportToJson_whenInputFileContainsKeila() {
        String inputFileName = "input_files/testCity.txt";
        io.generateWeatherReport(inputFileName);
        Gson gson = new Gson();
        try (FileReader fileReader = new FileReader("test_output_files/keila_report.json")) {
            WeatherReport weatherReport = gson.fromJson(fileReader, WeatherReport.class);
            assertEquals("Keila", weatherReport.getMainDetails().getCity());
        } catch (IOException ignore) {
            fail();
        }
    }

    @Test
    public void shouldGenerateSameFileFromFileAsInputString_whenInputFileAndInputStringKeila() {
        String inputFileName = "input_files/testCity.txt";
        io.generateWeatherReport(inputFileName);
        WeatherReport originalReport = io.getWeatherReport("Keila");
        Gson gson = new Gson();
        try (FileReader fileReader = new FileReader("test_output_files/keila_report.json")) {
            WeatherReport weatherReport = gson.fromJson(fileReader, WeatherReport.class);
            assertEquals(originalReport.getMainDetails(), weatherReport.getMainDetails());
        } catch (IOException ignore) {
            fail();
        }
    }

    @Test
    public void shouldGenerateMultipleFiles_whenInputFileContainsMultipleCities() {
        String inputFileName = "input_files/testCities.txt";
        io.generateWeatherReport(inputFileName);
        File f1 = new File("test_output_files/tallinn_report.json");
        File f2 = new File("test_output_files/tartu_report.json");
        File f3 = new File("test_output_files/narva_report.json");
        assertTrue(f1.exists());
        assertTrue(f2.exists());
        assertTrue(f3.exists());
    }

    @Test
    public void shouldGenerateSameFileFromFileAsInputString_whenMultipleNamesInFile() {
        String inputFileName = "input_files/testCities.txt";
        io.generateWeatherReport(inputFileName);
        WeatherReport tallinnReport = io.getWeatherReport("Tallinn");
        WeatherReport tartuReport = io.getWeatherReport("Tartu");
        WeatherReport narvaReport = io.getWeatherReport("Narva");

        Gson gson = new Gson();
        try {
            WeatherReport tallinnFileReport = gson.fromJson(new FileReader("test_output_files/tallinn_report.json"), WeatherReport.class);
            WeatherReport tartuFileReport = gson.fromJson(new FileReader("test_output_files/tartu_report.json"), WeatherReport.class);
            WeatherReport narvaFileReport = gson.fromJson(new FileReader("test_output_files/narva_report.json"), WeatherReport.class);
            assertEquals(tallinnReport.getMainDetails(), tallinnFileReport.getMainDetails());
            assertEquals(tartuReport.getMainDetails(), tartuFileReport.getMainDetails());
            assertEquals(narvaReport.getMainDetails(), narvaFileReport.getMainDetails());
        } catch (IOException ignore) {
            fail();
        }
    }

    private static void clearFolder(File folder) {
        File[] files = folder.listFiles();
        if (files != null){
            for (File f : files) {
                if (f.isDirectory()) {
                    clearFolder(f);
                } else {
                    f.delete();
                }
            }
        }
    }
}