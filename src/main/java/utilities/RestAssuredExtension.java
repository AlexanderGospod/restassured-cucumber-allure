package utilities;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;
import io.restassured.specification.RequestSpecification;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static endpoint.APIEndpoints.*;
import static io.restassured.RestAssured.given;

public class RestAssuredExtension {
    private RequestSpecification request;
    private RequestSpecBuilder builder = new RequestSpecBuilder();

    public Properties readPropertyData() {
        Properties props = new Properties();
        try (InputStream input = getClass().getResourceAsStream("/system.properties")) {
            props.load(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return props;
    }

    private void buildRequest() {
        RequestSpecification requestSpec = builder.build();
        request = given().spec(requestSpec);
    }
    public void setEndpoint(String endpointName) {
        builder.setBaseUri(BASE_URI);
        builder.setContentType(ContentType.JSON);
        switch (endpointName) {
            case "activities":
                builder.setBasePath(ACTIVITIES_ENDPOINT);
                break;
            case "commentThreads":
                builder.setBasePath(COMMENT_THREADS);
                break;
            default:
                throw new IllegalArgumentException("Invalid endpoint name: " + endpointName);
        }
    }
    public ResponseOptions<Response> sendGetRequest() {
        buildRequest();
        return request.get();
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
