package ee.taltech.weathermap.model.response.forecast;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherForecastResponse {
    private String cod;
    private int message;
    private int cnt;
    private List<WeatherForecastWeatherResponse> list;
    private WeatherForecastCityResponse city;

}
