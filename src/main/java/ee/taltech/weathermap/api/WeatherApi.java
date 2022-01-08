package ee.taltech.weathermap.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import ee.taltech.weathermap.exception.InvalidCityNameException;
import ee.taltech.weathermap.model.WeatherReport;
import ee.taltech.weathermap.model.response.WeatherDetailsResponse;
import ee.taltech.weathermap.model.response.forecast.WeatherForecastResponse;
import ee.taltech.weathermap.store.KeyStore;
import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;


import java.io.FileWriter;
import java.io.IOException;

import static com.sun.jersey.api.json.JSONConfiguration.FEATURE_POJO_MAPPING;

public class WeatherApi {

    public WeatherDetailsResponse getWeatherData(String cityName) {
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

        weatherDetailsResponse.getMain().setUnit("celsius");

        return weatherDetailsResponse;
    }

    public WeatherForecastResponse getWeatherForecast(String cityName){
        Client client = getConfiguredClient();

        ClientResponse response = client.resource("https://api.openweathermap.org/data/2.5/forecast")
                .queryParam("appid", KeyStore.API_KEY)
                .queryParam("q", cityName)
                .queryParam("units", "metric")
                .get(ClientResponse.class);

        WeatherForecastResponse weatherForecastResponse = response.getEntity(WeatherForecastResponse.class);
        if (response.getStatus() != 200) {
            throw new InvalidCityNameException();
        }

        return weatherForecastResponse;
    }


    private static Client getConfiguredClient() {
        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getClasses().add(JacksonJaxbJsonProvider.class);
        clientConfig.getFeatures().put(FEATURE_POJO_MAPPING, true);
        return Client.create(clientConfig);
    }
}
