package ee.taltech.weathermap;

import ee.taltech.weathermap.api.WeatherApi;
import ee.taltech.weathermap.model.WeatherReport;

public class Io {

    public WeatherReport getWeatherReport(String cityName) {
        return WeatherReport.from(WeatherApi.getWeatherData(cityName));
    }
}
