package ee.taltech.weathermap.api;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import ee.taltech.weathermap.model.CurrentWeatherData;
import ee.taltech.weathermap.store.KeyStore;
import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;


import static com.sun.jersey.api.json.JSONConfiguration.FEATURE_POJO_MAPPING;

public class WeatherApi {

    public CurrentWeatherData getCurrentWeatherData(String cityName) {
        Client client = getConfiguredClient();

        ClientResponse response = client.resource("https://api.openweathermap.org/data/2.5/weather")
                .queryParam("appid", KeyStore.API_KEY)
                .queryParam("q", cityName)
                .queryParam("units", "metric")
                .get(ClientResponse.class);

        return response.getEntity(CurrentWeatherData.class);
    }

    private static Client getConfiguredClient() {
        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getClasses().add(JacksonJaxbJsonProvider.class);
        clientConfig.getFeatures().put(FEATURE_POJO_MAPPING, true);
        return Client.create(clientConfig);
    }
}
