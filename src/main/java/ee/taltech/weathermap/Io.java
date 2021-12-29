package ee.taltech.weathermap;

import com.google.gson.Gson;
import ee.taltech.weathermap.api.WeatherApi;
import ee.taltech.weathermap.model.ForecastReport;
import ee.taltech.weathermap.model.WeatherReport;
import ee.taltech.weathermap.model.response.forecast.WeatherForecastResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Io {

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

    public ForecastReport getWeatherForecastReport(String cityName) {
        return ForecastReport.from(api.getWeatherForecast(cityName));
    }
}
