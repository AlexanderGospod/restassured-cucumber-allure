package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;
import pojo.comment.CommentThreadListResponse;
import static org.assertj.core.api.Assertions.assertThat;

public class CommentsSteps {
    private final ResponseOptions<Response> response;
    private CommentThreadListResponse commentThreadListResponse;

    public CommentsSteps() {
        response = CommonSteps.getResponse();
    }

    @Then("the response status code from commentThreads should be {int}")
    public void assertResponseStatusCode(int expectedStatusCode) {
        assertThat(response.statusCode())
                .as("Response status code does not match expected, should be " + expectedStatusCode)
                .isEqualTo(expectedStatusCode);
        if (expectedStatusCode == 200)
            // Deserialize the response to the ActivityListResponse POJO class
            commentThreadListResponse = response.getBody().as(CommentThreadListResponse.class);
    }
    @And("the response should contain a list of comments")
    public void assertThatListOfCommentsIsPresentInResponse() {
        assertThat(commentThreadListResponse.getItems().size()).isGreaterThan(1);
    }

    @And("the response should include the required comment data")
    public void assertThatRequiredCommentDataIsIncludedInResponse() {
        boolean allCommentsHasData = commentThreadListResponse.getItems().stream()
                .allMatch(commentThread -> commentThread.getId() != null
                        && commentThread.getKind() != null
                        && commentThread.getEtag() != null);
        assertThat(allCommentsHasData).isTrue();
    }
}
