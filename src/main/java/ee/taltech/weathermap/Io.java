package ee.taltech.weathermap;

import com.google.gson.Gson;
import ee.taltech.weathermap.api.WeatherApi;
import ee.taltech.weathermap.exception.FileTypeNotSupportedException;
import ee.taltech.weathermap.model.ForecastReport;
import ee.taltech.weathermap.model.WeatherReport;
import ee.taltech.weathermap.model.response.forecast.WeatherForecastResponse;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@AllArgsConstructor
@Slf4j
public class Io {

    private static final String FILE_TYPE_PATTERN = "^.*\\.txt$";

    private WeatherApi api;

    public WeatherReport getWeatherReport(String cityName) {
        Gson gson = new Gson();
        WeatherReport weatherReport = WeatherReport.from(api.getWeatherData(cityName));
        ForecastReport forecastReport = getWeatherForecastReport(cityName);
        if (forecastReport != null) {
            weatherReport.setForecastReport(forecastReport);
        }
        System.out.println(gson.toJson(weatherReport));
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
            String cityName = br.readLine();
            log.debug("Read cityName " + cityName);
            if (!cityName.isBlank()){
                cityNames.add(cityName);
                log.debug("Added " + cityName + " to city names");

            }
        } catch (IOException e){
            log.error("Could not read file");
            throw new FileNotFoundException();
        }

        return new WeatherReport();
    }

    public ForecastReport getWeatherForecastReport(String cityName) {
        return ForecastReport.from(api.getWeatherForecast(cityName));
    }
}
