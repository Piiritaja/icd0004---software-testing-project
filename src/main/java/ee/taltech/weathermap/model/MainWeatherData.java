package ee.taltech.weathermap.model;

import lombok.*;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class MainWeatherData {
    private String name;


}
