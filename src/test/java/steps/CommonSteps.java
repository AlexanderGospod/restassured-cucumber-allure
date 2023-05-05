package steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.qameta.allure.Allure;
import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;
import utilities.AccessTokenProvider;
import utilities.RestAssuredExtension;

import java.util.List;
import java.util.Map;
import java.util.Properties;

public class CommonSteps {
    private RestAssuredExtension restAssuredExtension = new RestAssuredExtension();
    private static ThreadLocal<ResponseOptions<Response>> threadLocalResponse = new ThreadLocal<>();

    public static ResponseOptions<Response> getResponse() {
        return threadLocalResponse.get();
    }

    @Given("the API endpoint {string} with the following query parameters:")
    public void setEndpointWithQueryParameters(String endpointName, DataTable queryParams) {
        restAssuredExtension.setEndpoint(endpointName);
        List<Map<String, String>> rows = queryParams.asMaps(String.class, String.class);
        Properties props = restAssuredExtension.readPropertyData();
        rows.forEach(row -> {
            switch (row.get("value")) {
                case "${key}":
                case "${channelId}":
                case "${wrongKey}":
                case "${videoId}":
                    String value = props.getProperty(row.get("value").replaceAll("\\$\\{(.+?)\\}", "$1")); //replace ${}
                    restAssuredExtension.addQueryParam(row.get("name"), value);
                    break;
                default:
                    restAssuredExtension.addQueryParam(row.get("name"), row.get("value"));
                    break;
            }
        });
    }

    @When("I send a GET request to the endpoint")
    public void sendGETRequestToEndpoint() {
        // restAssuredExtension.sendGetRequest() - returns response,
        // but to get it from another class when running tests in parallel, I put it in a static "threadLocalResponse"
        threadLocalResponse.set(restAssuredExtension.sendGetRequest());
    }

    @After
    public void attachErrorMessage() {
        if (getResponse() != null && getResponse().getStatusCode() >= 400) {
            Allure.addAttachment("Error message", getResponse().getBody().asString());
        }
    }

    @And("a valid OAuth 2.0 access token with {string} scope")
    public void iHaveAValidOAuthAccessToken(String scope) {
        AccessTokenProvider accessTokenProvider = new AccessTokenProvider();
        String token = accessTokenProvider.getAccessToken(scope);
        restAssuredExtension.addToken(token);
        System.out.println(token);
    }
}
