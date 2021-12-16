package ee.taltech.weathermap.model;

import ee.taltech.weathermap.model.response.WeatherDetailsResponse;
import lombok.*;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@Builder
public class MainWeatherData {
    private String city;
    private String coordinates;
    private String temperatureUnit;

    public static MainWeatherData from(WeatherDetailsResponse weatherDetails){
        return MainWeatherData.builder()
                .coordinates(weatherDetails.getCoord().getFormatted())
                .city(weatherDetails.getName())
                .build();
    }
}
