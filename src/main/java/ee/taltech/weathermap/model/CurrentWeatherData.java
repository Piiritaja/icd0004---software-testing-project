package ee.taltech.weathermap.model;

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
    private String name;
}
