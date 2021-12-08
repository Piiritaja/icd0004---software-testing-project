package ee.taltech.weathermap.model.response;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Weather {
    private long id;
    private String main;
    private String description;
    private String icon;
}
