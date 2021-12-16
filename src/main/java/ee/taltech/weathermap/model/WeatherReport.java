package ee.taltech.weathermap.model;

import ee.taltech.weathermap.model.response.WeatherDetailsResponse;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeatherReport {
    private MainWeatherData mainDetails;
    private CurrentWeatherData currentWeatherData;

    public static WeatherReport from(WeatherDetailsResponse weatherDetails) {
        return WeatherReport.builder()
                .currentWeatherData(CurrentWeatherData.from(weatherDetails))
                .mainDetails(MainWeatherData.from(weatherDetails))
                .build();
    }
}
