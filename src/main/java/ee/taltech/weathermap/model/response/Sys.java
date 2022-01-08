package ee.taltech.weathermap.model.response;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Sys {
    private int type;
    private long id;
    private String country;
    private long sunrise;
    private long sunset;
}
