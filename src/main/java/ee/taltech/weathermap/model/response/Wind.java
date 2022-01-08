package ee.taltech.weathermap.model.response;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Wind {
    private int speed;
    private int deg;
    private int gust;
}
