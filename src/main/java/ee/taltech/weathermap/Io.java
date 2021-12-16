package ee.taltech.weathermap;

import com.google.gson.Gson;
import ee.taltech.weathermap.api.WeatherApi;
import ee.taltech.weathermap.model.WeatherReport;

public class Io {

    public WeatherReport getWeatherReport(String cityName) {
        Gson gson = new Gson();
        WeatherReport weatherReport = WeatherReport.from(WeatherApi.getWeatherData(cityName));
        System.out.println(gson.toJson(weatherReport));
        return weatherReport;
    }
}
