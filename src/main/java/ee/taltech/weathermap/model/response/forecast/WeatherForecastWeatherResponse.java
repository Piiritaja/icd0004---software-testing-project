package ee.taltech.weathermap.model.response.forecast;

import ee.taltech.weathermap.model.response.Main;
import lombok.Getter;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherForecastWeatherResponse {
    private long dt;
    private Main main;

    public Date getDate() {
        return new Date(dt*1000);
    }

    public String getFormattedDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(getDate());
    }
}
