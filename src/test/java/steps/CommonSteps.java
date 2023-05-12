package steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Allure;
import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;
import utilities.AccessTokenProvider;
import utilities.RestAssuredExtension;
import utilities.TokenStorage;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import static org.assertj.core.api.Assertions.assertThat;

public class CommonSteps {
    private final static ThreadLocal<RestAssuredExtension> restAssuredExtension = new ThreadLocal<>();

    public static RestAssuredExtension getRestAssuredExtension() {
        return restAssuredExtension.get();
    }

    private final static ThreadLocal<ResponseOptions<Response>> response = new ThreadLocal<>();

    public static ResponseOptions<Response> getResponse() {
        return response.get();
    }

    private static Properties props;

    public static Properties getProps() {
        return props;
    }

    public CommonSteps() {
        restAssuredExtension.set(new RestAssuredExtension());
        props = restAssuredExtension.get().readPropertyData();
    }

    @Given("the API endpoint {string} with the following query parameters:")
    public void setEndpointWithQueryParameters(String endpointName, DataTable queryParams) {
        setEndpoint(endpointName);
        List<Map<String, String>> rows = queryParams.asMaps(String.class, String.class);
        rows.forEach(row -> {
            switch (row.get("value")) {
                case "${key}":
                case "${channelId}":
                case "${wrongKey}":
                case "${videoId}":
                    String value = props.getProperty(row.get("value").replaceAll("\\$\\{(.+?)\\}", "$1")); //replace ${}
                    restAssuredExtension.get().addQueryParam(row.get("name"), value);
                    break;
                default:
                    restAssuredExtension.get().addQueryParam(row.get("name"), row.get("value"));
                    break;
            }
        });
    }

    @Given("the API endpoint {string}")
    public void setEndpoint(String endpointName) {
        restAssuredExtension.get().setEndpoint(endpointName);
    }

    @When("I send a GET request to the endpoint")
    public void sendGETRequestToEndpoint() {
        /* restAssuredExtension.sendGetRequest() - returns response, but to get it from another class
           when running tests in parallel, I put it in a static "threadLocalResponse" */
        response.set(restAssuredExtension.get().sendGetRequest());
    }

    @When("I send a POST request to endpoint")
    public void sendPOSTRequestToEndpoint() {
        response.set(restAssuredExtension.get().sendPostRequest());
    }

    @When("I send a PUT request to endpoint")
    public void sendPUTRequestToEndpoint() {
        response.set(restAssuredExtension.get().sendPutRequest());
    }

    @When("I send a DELETE request to endpoint")
    public void sendDELETERequestToEndpoint() {
        response.set(restAssuredExtension.get().sendDeleteRequest());
    }

    @After
    public void attachErrorMessage() {
        if (response.get() != null && response.get().getStatusCode() >= 400) {
            Allure.addAttachment("Error message", response.get().getBody().asString());
        }
    }

    @And("a OAuth 2.0 access token with {string} scope")
    public void setTokenWithNecessaryAccessRights(String scope) {
        getTokenWithNecessaryAccessRights(scope);
        restAssuredExtension.get().addToken(TokenStorage.getToken(scope));
    }
    @And("an invalid OAuth 2.0 access token")
    public void setInvalidTokenWithNecessaryAccessRights() {
        restAssuredExtension.get().addToken(getProps().getProperty("invalidToken"));
    }

    @Then("the response status code should be {int}")
    public void assertResponseStatus(int expectedStatusCode) {
        assertThat(response.get().getStatusCode())
                .as("Response status code does not match expected, should be " + expectedStatusCode)
                .isEqualTo(expectedStatusCode);
    }

    @And("the response protocol version and content type, as expected")
    public void assertProtocolVersion() {
        assertThat(response.get().getStatusLine())
                .as("protocol version should be HTTP/1.1")
                .contains("HTTP/1.1");
        assertThat(response.get().getContentType())
                .as("ContentType should be 'application/json; charset=UTF-8'")
                .isEqualTo("application/json; charset=UTF-8");
    }

    public void getTokenWithNecessaryAccessRights(String scope) {
        //TokenStorage.setToken("YOUTUBE_FORCE_SSL", "ya29.a0AWY7Ckl-vha9Y5fhFPwjRKIelMd2BFSF7OvL7oStUhotqwfGtDz5pjWiSKpyu8lJuLAxqHE0RtNMc-h5KvhaDBRkEKiLumrtEx-Mz072yjJFJc5cky_7x--cIg4Kd7Y_SLUT-H3xXhdqSST7Qcs0yqqA-1LKaCgYKARkSARISFQG1tDrpUF18VQ0bDtLuFVvY4gvx0A0163");
        if (TokenStorage.getToken(scope) == null) {
            AccessTokenProvider accessTokenProvider = new AccessTokenProvider();
            String token = accessTokenProvider.getAccessToken(scope);
            System.out.println(token);
            TokenStorage.setToken(scope, token);
        }
    }
}
