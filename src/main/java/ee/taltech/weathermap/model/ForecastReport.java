package ee.taltech.weathermap.model;

import ee.taltech.weathermap.model.response.Weather;
import ee.taltech.weathermap.model.response.WeatherDetailsResponse;
import ee.taltech.weathermap.model.response.forecast.WeatherForecastResponse;
import ee.taltech.weathermap.model.response.forecast.WeatherForecastWeatherResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ForecastReport {
    private List<WeatherData> weatherData;

    public static ForecastReport from(WeatherForecastResponse weatherDetails) {
        return ForecastReport.builder()
                .weatherData(getThreeDayForecastFromForecastResponse(weatherDetails))
                .build();
    }

    private static List<WeatherData> getThreeDayForecastFromForecastResponse(WeatherForecastResponse weatherDetails) {
        List<WeatherForecastWeatherResponse> weatherData = weatherDetails.getList();
        List<String> days = getNextThreeDays();
        List<WeatherData> weatherDataReturn = new ArrayList<>();
        for (int i = 0; i < weatherData.size(); i++) {
            WeatherForecastWeatherResponse current = weatherData.get(i);
            if (days.contains(current.getFormattedDate())) {
                WeatherData weather = generateDaysReport(weatherData.subList(i, weatherData.size()), current.getFormattedDate());
                weatherDataReturn.add(weather);
                days.remove(current.getFormattedDate());
            }
        }
        return weatherDataReturn;
    }

    private static List<String> getNextThreeDays() {
        List<String> dates = new ArrayList<>();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate current = LocalDate.now();
        for (int i = 1; i <= 3; i++) {
            LocalDate date = current.plusDays(i);
            dates.add(date.format(dateFormat));
        }
        return dates;
    }


    private static WeatherData generateDaysReport(List<WeatherForecastWeatherResponse> forecast, String current) {
        float totalTemp = 0f;
        float totalHumidity = 0f;
        float totalPressure = 0f;
        int count = 0;

        for (WeatherForecastWeatherResponse weather : forecast) {
            if (weather.getFormattedDate().equals(current)) {
                totalTemp += weather.getMain().getTemp();
                totalHumidity += weather.getMain().getHumidity();
                totalPressure += weather.getMain().getPressure();
                count++;
            }
        }
        return WeatherData.builder().date(current)
                .temperature((int) totalTemp / count)
                .pressure((int) totalPressure / count)
                .humidity((int) totalHumidity / count)
                .build();
    }

}
