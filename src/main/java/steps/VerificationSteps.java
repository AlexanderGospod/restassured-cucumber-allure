package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;
import pojo.ActivityListResponse;
import utilities.RestAssuredExtension;

import java.util.Objects;

import static endpoint.APIEndpoints.ACTIVITIES_ENDPOINT;
import static org.assertj.core.api.Assertions.assertThat;


public class VerificationSteps {
    private String endpoint;
    public ResponseOptions<Response> response;
    public ActivityListResponse activityListResponse;
    public RestAssuredExtension restAssuredExtension = new RestAssuredExtension();


    @Given("the API endpoint for activities")
    public void theAPIEndpointForActivities() {
        endpoint = ACTIVITIES_ENDPOINT;
    }

    @When("a GET request is sent to the endpoint")
    public void aGETRequestIsSentToTheEndpoint() {
        response = restAssuredExtension.sendGetRequest(endpoint);
        //System.out.println(response.getBody().asString());
    }

    @Then("the response status code should be {int}")
    public void theResponseStatusCodeShouldBe(int expectedStatusCode) {
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        assertThat(response.statusCode())
                .as("Response status code does not match expected, should be " + expectedStatusCode)
                .isEqualTo(expectedStatusCode);
        if (expectedStatusCode == 200)
            // Deserialize the response to the ActivityListResponse POJO class
            activityListResponse = response.getBody().as(ActivityListResponse.class);
    }

    @And("the response should contain a list of channel activity events")
    public void theResponseShouldContainAListOfChannelActivityEvents() {
        assertThat(activityListResponse.getItems().isEmpty()).isFalse();
    }

    @And("the response should contain the default number of items \\({int})")
    public void theResponseShouldContainTheDefaultNumberOfItems(int defaultNumberOfItems) {
        assertThat(activityListResponse.getItems().size()).isEqualTo(defaultNumberOfItems);
    }

    @And("the query param {string} with the value {string} is added")
    public void theQueryParamWithTheValueIsAdded(String queryParam, String value) {
        restAssuredExtension.addQueryParam(queryParam, value);
    }

    @And("the response should include the required property snippet")
    public void theResponseShouldIncludeTheRequiredPropertySnippet() {
        boolean allSnippetsNotEmpty = activityListResponse.getItems().stream()
                .map(ActivityListResponse.Activity::getSnippet)
                .allMatch(Objects::nonNull);
        assertThat(allSnippetsNotEmpty).isTrue();
    }

    @And("the response should include only the specified resource property snippet")
    public void theResponseShouldIncludeOnlyTheSpecifiedPropertySnippet() {
        boolean allContentDetailsIsEmpty = activityListResponse.getItems().stream()
                .map(ActivityListResponse.Activity::getContentDetails)
                .allMatch(Objects::isNull);
        assertThat(allContentDetailsIsEmpty).isTrue();
    }

    @And("the response should include the required property contentDetails")
    public void theResponseShouldIncludeTheRequiredPropertyContentDetails() {
        boolean allContentDetailsNotEmpty = activityListResponse.getItems().stream()
                .map(ActivityListResponse.Activity::getContentDetails)
                .allMatch(Objects::nonNull);
        assertThat(allContentDetailsNotEmpty).isTrue();
    }

    @And("the response should include only the specified resource property contentDetails")
    public void theResponseShouldIncludeOnlyTheSpecifiedPropertyContentDetails() {
        boolean allSnippetIsEmpty = activityListResponse.getItems().stream()
                .map(ActivityListResponse.Activity::getSnippet)
                .allMatch(Objects::isNull);
        assertThat(allSnippetIsEmpty).isTrue();
    }

    @And("set an invalid API key")
    public void setAnInvalidAPIKey() {
        restAssuredExtension.changeQueryParam("key", "AIzaSyDC3sqH2Gt7VNdFUn-4KNI_NpH4xpWrong");
    }

    @And("the response should contain an error message")
    public void theResponseShouldContainAnErrorMessage() {
        String expectedErrorMessage = "API key not valid. Please pass a valid API key.";
        String errorMessage = response.getBody().jsonPath().getString("error.message");
        assertThat(response.getBody().jsonPath().getString("error.message"))
                .as("Response Error message does not match expected, should be " + expectedErrorMessage)
                .isEqualTo(expectedErrorMessage);
    }


}
