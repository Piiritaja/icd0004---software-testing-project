package ee.taltech.weathermap.api;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import ee.taltech.weathermap.exception.InvalidCityNameException;
import ee.taltech.weathermap.model.response.WeatherDetailsResponse;
import ee.taltech.weathermap.store.KeyStore;
import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;


import static com.sun.jersey.api.json.JSONConfiguration.FEATURE_POJO_MAPPING;

public class WeatherApi {

    public static WeatherDetailsResponse getWeatherData(String cityName) {
        Client client = getConfiguredClient();

        ClientResponse response = client.resource("https://api.openweathermap.org/data/2.5/weather")
                .queryParam("appid", KeyStore.API_KEY)
                .queryParam("q", cityName)
                .queryParam("units", "metric")
                .get(ClientResponse.class);
        WeatherDetailsResponse weatherDetailsResponse = response.getEntity(WeatherDetailsResponse.class);
        if (response.getStatus() != 200) {
            throw new InvalidCityNameException();
        }
        return weatherDetailsResponse;
    }

    private static Client getConfiguredClient() {
        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getClasses().add(JacksonJaxbJsonProvider.class);
        clientConfig.getFeatures().put(FEATURE_POJO_MAPPING, true);
        return Client.create(clientConfig);
    }
}
