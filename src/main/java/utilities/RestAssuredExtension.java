package utilities;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;
import io.restassured.specification.RequestSpecification;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;
import static endpoint.APIEndpoints.BASE_URI;
import static io.restassured.RestAssured.given;

public class RestAssuredExtension {
    private RequestSpecification request;
    private String apiKey;
    private String channelId;
    private RequestSpecBuilder builder = new RequestSpecBuilder();

    public RestAssuredExtension() {
        readPropertyData();
        builder.setBaseUri(BASE_URI);
        builder.setContentType(ContentType.JSON);
    }

    private void readPropertyData() {
        try (InputStream input = getClass().getResourceAsStream("/youtube-api.properties")) {
            Properties props = new Properties();
            props.load(input);
            apiKey = props.getProperty("youtubeApiKey");
            channelId = props.getProperty("youtubeChannelId");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void buildRequest() {
        RequestSpecification requestSpec = builder.build();
        request = given().spec(requestSpec);
    }

    public ResponseOptions<Response> sendGetRequest(String endpoint) {
        buildRequest();
        try {
            return request.get(new URI(endpoint));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public void addApiKeyParam() {
        builder.addQueryParam("key", apiKey);
    }

    public void addChannelIdParam() {
        builder.addQueryParam("channelId", channelId);
    }

    public void addQueryParam(String queryParam, String value) {
        builder.addQueryParam(queryParam, value);
    }

    public void addToken(String token) {
        builder.addHeader("Authorization", "Bearer " + token);
    }

    public void changeQueryParam(String queryParam, String value) {
        deleteQueryParam(queryParam);
        builder.addQueryParam(queryParam, value);
    }

    public void deleteQueryParam(String queryParam) {
        builder.removeQueryParam(queryParam);
    }
}
