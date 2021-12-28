package ee.taltech.weathermap.model.response.forecast;

import ee.taltech.weathermap.model.response.Coordinates;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherForecastCityResponse {
    private long id;
    private String name;
    private Coordinates coord;
    private String country;
    private int population;
    private int timezone;
    private long sunrise;
    private long sunset;
}
