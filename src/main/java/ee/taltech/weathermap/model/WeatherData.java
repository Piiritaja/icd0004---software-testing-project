package ee.taltech.weathermap.model;

import ee.taltech.weathermap.model.response.WeatherDetailsResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.text.SimpleDateFormat;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherData {
    private String date;
    private int temperature;
    private int humidity;
    private int pressure;

    public static WeatherData from(WeatherDetailsResponse weatherDetails){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return WeatherData.builder()
                .date(formatter.format(new Date(System.currentTimeMillis())))
                .temperature(weatherDetails.getMain().getTemp())
                .humidity(weatherDetails.getMain().getHumidity())
                .pressure(weatherDetails.getMain().getPressure())
                .build();
    }
}
