package ee.taltech.weathermap.model;

import ee.taltech.weathermap.model.response.WeatherDetailsResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.text.SimpleDateFormat;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrentWeatherData {
    private String date;
    private double temperature;
    private int humidity;
    private double pressure;

    public static CurrentWeatherData from(WeatherDetailsResponse weatherDetails) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        return CurrentWeatherData.builder()
                .date(formatter.format(new Date(System.currentTimeMillis())))
                .temperature(weatherDetails.getMain().getTemp())
                .humidity(weatherDetails.getMain().getHumidity())
                .pressure(weatherDetails.getMain().getPressure())
                .build();
    }
}