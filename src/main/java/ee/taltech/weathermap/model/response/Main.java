package ee.taltech.weathermap.model.response;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Main {
    private int temp;
    private int feels_like;
    private int temp_min;
    private int temp_max;
    private int pressure;
    private int humidity;
    private int sea_level;
    private int grnd_level;

}
