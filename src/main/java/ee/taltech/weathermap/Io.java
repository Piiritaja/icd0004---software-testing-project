package ee.taltech.weathermap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonWriter;
import ee.taltech.weathermap.api.WeatherApi;
import ee.taltech.weathermap.exception.FileTypeNotSupportedException;
import ee.taltech.weathermap.model.ForecastReport;
import ee.taltech.weathermap.model.WeatherReport;
import ee.taltech.weathermap.model.response.forecast.WeatherForecastResponse;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@AllArgsConstructor
@Slf4j
public class Io {

    private static final String FILE_TYPE_PATTERN = "^.*\\.txt$";

    private WeatherApi api;

    private String outputPath;

    public WeatherReport getWeatherReport(String cityName) {
        WeatherReport weatherReport = WeatherReport.from(api.getWeatherData(cityName));
        ForecastReport forecastReport = getWeatherForecastReport(cityName);
        if (forecastReport != null) {
            weatherReport.setForecastReport(forecastReport);
        }
        return weatherReport;
    }

    @SneakyThrows
    public WeatherReport generateWeatherReport(String fileName) {
        Pattern pattern = Pattern.compile(FILE_TYPE_PATTERN);
        Matcher matcher = pattern.matcher(fileName);
        if (!matcher.find()) {
            log.error("Wrong input file type");
            throw new FileTypeNotSupportedException();
        }
        List<String> cityNames = new ArrayList<>();
        log.info("Reading input file");
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String cityName;
            while ((cityName = br.readLine()) != null && !cityName.isBlank()) {
                log.debug("Read cityName " + cityName);
                if (!cityName.isBlank()) {
                    cityNames.add(cityName);
                    log.debug("Added " + cityName + " to city names");

                }
            }
        } catch (IOException e) {
            log.error("Could not read file");
            throw new FileNotFoundException();
        }

        List<WeatherReport> reports = cityNames.stream().map(this::getWeatherReport).collect(Collectors.toList());

        for (WeatherReport report : reports) {
            this.writeReportToFile(report);
        }

        return new WeatherReport();
    }

    private void writeReportToFile(WeatherReport weatherReport) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String outputFileName = outputPath + weatherReport.getMainDetails().getCity().toLowerCase(Locale.ROOT) + "_report.json";
        try (FileWriter fileWriter = new FileWriter(outputFileName)) {
            gson.toJson(weatherReport, fileWriter);
            log.info("Report in file " + outputFileName);
        } catch (IOException ignore) {

        }

    }

    public ForecastReport getWeatherForecastReport(String cityName) {
        return ForecastReport.from(api.getWeatherForecast(cityName));
    }
}
