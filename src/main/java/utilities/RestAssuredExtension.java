package utilities;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;
import io.restassured.specification.RequestSpecification;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Properties;

import static endpoint.APIEndpoints.BASE_URI;
import static io.restassured.RestAssured.given;

public class RestAssuredExtension {
    private static RequestSpecification Request;
    private String apiKey;
    private String channelId;
    private static RequestSpecBuilder builder = new RequestSpecBuilder();

    public RestAssuredExtension() {
        readPropertyData();
        builder.setBaseUri(BASE_URI);
        builder.setContentType(ContentType.JSON);
        builder.addQueryParam("key", apiKey);
        builder.addQueryParam("channelId", channelId);
    }
    private void readPropertyData(){
        try (InputStream input = getClass().getResourceAsStream("/youtube_api.properties")) {
            Properties props = new Properties();
            props.load(input);
            apiKey = props.getProperty("youtubeApiKey");
            channelId = props.getProperty("youtubeChannelId");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static void buildRequest(){
        RequestSpecification requestSpec = builder.build();
        Request = given().spec(requestSpec);
    }

    public static void GetOpsWithPathParameter(String url, Map<String, String> pathParams) {
        Request.pathParams(pathParams);
        try {
            Request.get(new URI(url));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public static ResponseOptions<Response> sendGetRequest(String endpoint) {
        buildRequest();
        try {
            return Request.get(new URI(endpoint));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
    public static void addQueryParam(String queryParam, String value) {
        builder.addQueryParam(queryParam, value);
    }
    public static void changeQueryParam(String queryParam, String value) {
        builder.removeQueryParam(queryParam);
        builder.addQueryParam(queryParam, value);
    }

    public static ResponseOptions<Response> GetOpsWithToken(String url, String token) {
        try {
            Request.header(new Header("Authorization", "Bearer " + token));
            return Request.get(new URI(url));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ResponseOptions<Response> PUTOpsWithBodyAndPathParams(String url, Map<String,String> body, Map<String,String> pathParams) {
        Request.pathParams(pathParams);
        Request.body(body);
        return Request.put(url);
    }
    public static ResponseOptions<Response> PostOpsWithBodyAndPathParams(String url, Map<String, String> pathParams, Map<String, String> body)  {
        Request.pathParams(pathParams);
        Request.body(body);
        return Request.post(url);
    }
    public static ResponseOptions<Response> DeleteOpsWithPathParams(String url,Map<String, String> pathParams)  {
        Request.pathParams(pathParams);
        return Request.delete(url);
    }
    public static ResponseOptions<Response> GetWithPathParams(String url,Map<String, String> pathParams)  {
        Request.pathParams(pathParams);
        return Request.get(url);
    }

    public static ResponseOptions<Response> GetWithQueryParamsWithToken(String url,Map<String, String> pathParams, String token)  {
        Request.header(new Header("Authorization", "Bearer " + token));
        Request.queryParams(pathParams);
        return Request.get(url);
    }

//    public static ResponseOptions<Response> PostOpsWithBody(String url,Map<String, String> body)  {
//        Request.body(body);
//        return Request.post(url);
//    }




}
