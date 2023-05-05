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
import pojo.ActivityListResponse;
import utilities.AccessTokenProvider;
import utilities.RestAssuredExtension;

import java.util.*;
import static org.assertj.core.api.Assertions.assertThat;


public class ActivitiesSteps extends BaseSteps{
    private ResponseOptions<Response> response;
    private ActivityListResponse activityListResponse;
    private RestAssuredExtension restAssuredExtension = new RestAssuredExtension();
//    private BaseSteps baseSteps = new BaseSteps();

    @Given("the API endpoint {string} with the following query parameters:")
    public void setEndpointWithQueryParameters(String endpointName, DataTable queryParams) {
        setEndpointWithQueryParameters(restAssuredExtension, endpointName, queryParams);
    }

    @When("I send a GET request to the endpoint")
    public void sendGETRequestToEndpoint() {
        response = restAssuredExtension.sendGetRequest();
        System.out.println(response.getBody().asString());
    }

    @After
    public void attachErrorMessage() {
        if (response != null && response.getStatusCode() >= 400) {
            Allure.addAttachment("Error message", response.getBody().asString());
        }
    }

    @Then("the response status code should be {int}")
    public void assertResponseStatusCode(int expectedStatusCode) {
        assertThat(response.statusCode())
                .as("Response status code does not match expected, should be " + expectedStatusCode)
                .isEqualTo(expectedStatusCode);
        if (expectedStatusCode == 200)
            // Deserialize the response to the ActivityListResponse POJO class
            activityListResponse = response.getBody().as(ActivityListResponse.class);
    }

    @And("the response should contain a list of channel activity events")
    public void assertResponseContainsChannelActivityEvents() {
        assertThat(activityListResponse.getItems().isEmpty()).isFalse();
    }

    @And("the response should contain the default number of items \\({int})")
    public void assertResponseContainsDefaultNumberOfItems(int defaultNumberOfItems) {
        assertThat(activityListResponse.getItems().size()).isEqualTo(defaultNumberOfItems);
    }

    @And("the query param {string} with the value {string}")
    public void addQueryParam(String queryParam, String value) {
        restAssuredExtension.addQueryParam(queryParam, value);
    }

    @And("the response should include the required property snippet")
    public void assertResponseIncludesSnippet() {
        boolean allSnippetsNotEmpty = activityListResponse.getItems().stream()
                .map(ActivityListResponse.Activity::getSnippet)
                .allMatch(Objects::nonNull);
        assertThat(allSnippetsNotEmpty).isTrue();
    }

    @And("the response should include only the specified resource property snippet")
    public void assertResponseContainsSpecifiedSnippetProperty() {
        boolean allContentDetailsIsEmpty = activityListResponse.getItems().stream()
                .map(ActivityListResponse.Activity::getContentDetails)
                .allMatch(Objects::isNull);
        assertThat(allContentDetailsIsEmpty).isTrue();
    }

    @And("the response should include the required property contentDetails")
    public void assertResponseIncludesContentDetails() {
        boolean allContentDetailsNotEmpty = activityListResponse.getItems().stream()
                .map(ActivityListResponse.Activity::getContentDetails)
                .allMatch(Objects::nonNull);
        assertThat(allContentDetailsNotEmpty).isTrue();
    }

    @And("the response should include only the specified resource property contentDetails")
    public void assertResponseContainsSpecifiedProperty() {
        boolean allSnippetIsEmpty = activityListResponse.getItems().stream()
                .map(ActivityListResponse.Activity::getSnippet)
                .allMatch(Objects::isNull);
        assertThat(allSnippetIsEmpty).isTrue();
    }

    @And("the response should contain an error message that API key not valid")
    public void assertErrorMessageForInvalidApiKey() {
        String expectedErrorMessage = "API key not valid. Please pass a valid API key.";
        assertErrorMessage(expectedErrorMessage);
    }

    @And("the response should contain an error message that No filter selected")
    public void assertErrorMessageForMissingChannelID() {
        String expectedErrorMessage = "No filter selected. Expected one of:";
        assertErrorMessage(expectedErrorMessage);
    }

    private void assertErrorMessage(String expectedErrorMessage) {
        String errorMessage = response.getBody().jsonPath().getString("error.message");
        System.out.println(errorMessage);
        assertThat(errorMessage)
                .as("Response Error message does not match expected, should be " + expectedErrorMessage)
                .contains(expectedErrorMessage);
    }

    @And("a valid OAuth 2.0 access token with {string} scope")
    public void iHaveAValidOAuthAccessToken(String scope) {
        AccessTokenProvider accessTokenProvider = new AccessTokenProvider();
        String token = accessTokenProvider.getAccessToken(scope);
        restAssuredExtension.addToken(token);
        System.out.println(token);
    }
}
