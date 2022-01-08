package ee.taltech.weathermap.api;

import ee.taltech.weathermap.store.KeyStore;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.HTTP_OK;
import static java.net.HttpURLConnection.HTTP_UNAUTHORIZED;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;


public class OpenWeatherMapTests {
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather";
    private static final String FORECAST_URL = "https://api.openweathermap.org/data/2.5/forecast";

    /*
    SMOKE tests
     */
    @Test
    public void whenCalledWithoutApiKeyReturnHttpUnauthorized() {
        given()
                .queryParam("q", "Keila")
                .queryParam("units", "metric")
                .when()
                .get(BASE_URL)
                .then()
                .statusCode(HTTP_UNAUTHORIZED);
    }

    @Test
    public void shouldReturnHttpOkWhenCityNameIsGiven() {
        given()
                .queryParam("q", "Keila")
                .queryParam("units", "metric")
                .queryParam("appid", KeyStore.API_KEY)
                .when()
                .get(BASE_URL)
                .then()
                .statusCode(HTTP_OK);
    }

    @Test
    public void shouldReturnCityNameWhenCityNameIsGiven() {
        given()
                .queryParam("q", "keila")
                .queryParam("units", "metric")
                .queryParam("appid", KeyStore.API_KEY)
                .when()
                .get(BASE_URL)
                .then()
                .body("$", hasKey("name"))
                .body("name", equalTo("Keila"));
    }

    @Test
    public void shouldHaveCoordinatesBlockWhenCorrectCityNameIsGiven() {
        given()
                .queryParam("q", "keila")
                .queryParam("units", "metric")
                .queryParam("appid", KeyStore.API_KEY)
                .when()
                .get(BASE_URL)
                .then()
                .body("$", hasKey("coord")) // $ - terve body
                .body("coord", hasKey("lon"))
                .body("coord", hasKey("lat"));
    }

    /*
    Forecast
     */

    @Test
    public void shouldHaveCityNameInForecastResponseWhenCityNameIsGiven() {
        given()
                .queryParam("q", "Keila")
                .queryParam("units", "metric")
                .queryParam("appid", KeyStore.API_KEY)
                .when()
                .get(FORECAST_URL)
                .then()
                .body("$", hasKey("city"))
                .body("city", hasKey("name"))
                .body("city.name", equalTo("Keila"));
    }

    @Test
    public void shouldHaveForecastDataInForecastResponseWhenCityNameIsGiven(){
        given()
                .queryParam("q", "Keila")
                .queryParam("units", "metric")
                .queryParam("appid", KeyStore.API_KEY)
                .when()
                .get(FORECAST_URL)
                .then()
                .body("$", hasKey("list"));
    }

}
