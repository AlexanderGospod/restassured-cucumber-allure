package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;
import pojo.ActivityListResponse;

import java.util.*;
import static org.assertj.core.api.Assertions.assertThat;


public class ActivitiesSteps {

    private ActivityListResponse activityListResponse;
    private final ResponseOptions<Response> response;

    public ActivitiesSteps() {
        response = CommonSteps.getResponse();
    }

    @Then("the response status code from activity should be {int}")
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

}
