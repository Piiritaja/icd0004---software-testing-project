package ee.taltech.weathermap.model;

import ee.taltech.weathermap.model.response.WeatherDetailsResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrentWeatherData {
    private WeatherData weatherData;

    public static CurrentWeatherData from(WeatherDetailsResponse weatherDetails) {
        return CurrentWeatherData.builder()
                .weatherData(WeatherData.from(weatherDetails))
                .build();
    }
}