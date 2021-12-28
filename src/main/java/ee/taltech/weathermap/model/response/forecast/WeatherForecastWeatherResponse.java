package ee.taltech.weathermap.model.response.forecast;

import ee.taltech.weathermap.model.response.Main;
import lombok.Getter;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherForecastWeatherResponse {
    private int dt;
    private Main main;
}
