package ee.taltech.weathermap;

import com.google.gson.Gson;
import ee.taltech.weathermap.api.WeatherApi;
import ee.taltech.weathermap.model.WeatherReport;

public class Io {

    public String getWeatherReport(String cityName) {
        Gson gson = new Gson();
        return gson.toJson(WeatherReport.from(WeatherApi.getWeatherData(cityName)));
    }
}
