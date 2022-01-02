package ee.taltech.weathermap;

import ee.taltech.weathermap.api.WeatherApi;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Application {

    public static void main(String[] args) {
        log.info("init");
        String file_location = args[0];
        WeatherApi api = new WeatherApi();
        Io io = new Io(api, ".");
        io.generateWeatherReport(file_location);
    }
}
