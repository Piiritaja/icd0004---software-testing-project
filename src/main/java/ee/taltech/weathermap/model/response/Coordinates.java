package ee.taltech.weathermap.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Coordinates {
    private float lon;
    private float lat;

    public String getFormatted(){
        return String.format("%.2f,%.2f", lat, lon);
    }
}
