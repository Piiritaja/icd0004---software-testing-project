package ee.taltech.weathermap;

import com.google.gson.Gson;
import ee.taltech.weathermap.api.WeatherApi;
import ee.taltech.weathermap.model.WeatherReport;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Io {

    private WeatherApi api;

    public WeatherReport getWeatherReport(String cityName) {
        Gson gson = new Gson();
        WeatherReport weatherReport = WeatherReport.from(api.getWeatherData(cityName));
        System.out.println(gson.toJson(weatherReport));
        return weatherReport;
    }
}
