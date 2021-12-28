package ee.taltech.weathermap.model;

import ee.taltech.weathermap.model.response.WeatherDetailsResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ForecastReport {
    private List<WeatherData> weatherData;

    public static ForecastReport from(WeatherDetailsResponse weatherDetails) {
        return ForecastReport.builder().build();
    }

}
