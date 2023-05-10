package steps;

import io.cucumber.java.en.And;
import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;
import model.CommentThreadRequest;
import model.CommentThreadRequest.*;
import pojo.comment.CommentThread;
import pojo.comment.CommentThreadList;
import utilities.RestAssuredExtension;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import static org.assertj.core.api.Assertions.assertThat;

public class CommentsSteps {
    private ResponseOptions<Response> response;
    private final RestAssuredExtension restAssuredExtension;

    private CommentThreadList commentThreadList;
    private CommentThread commentThread;


    public CommentsSteps() {
        restAssuredExtension = CommonSteps.getRestAssuredExtension();
    }

    @And("the response should contain a list of comments")
    public void assertThatListOfCommentsIsPresentInResponse() {
        assertThat(commentThreadList.getItems().size()).isGreaterThan(1);
    }

    @And("the response should include the required data about list of comments")
    public void verifyRequiredCommentListData() {
        response = CommonSteps.getResponse();
        // Deserialize the response to the commentThreadList POJO class, in the pojo class there is a check of all fields not null
        commentThreadList = response.getBody().as(CommentThreadList.class);
    }

    @And("the response should include the required comment data")
    public void verifyRequiredCommentData() {
        // Deserialize the response to the commentThread POJO class, in the pojo class there is a check of all fields not null
        response = CommonSteps.getResponse();
        commentThread = response.getBody().as(CommentThread.class);
    }

    @And("I have a request body with comment {string}")
    public void setRequestBodyWithComment(String comment) {
        String channelId = CommonSteps.getProps().getProperty("channelId");
        String videoId = CommonSteps.getProps().getProperty("videoId");
        CommentSnippet commentSnippet = new CommentSnippet(comment + ": " + getCurrentTime());
        TopLevelComment topLevelComment = new TopLevelComment(commentSnippet);
        Snippet snippet = new Snippet(channelId, videoId, topLevelComment);
        CommentThreadRequest commentThreadRequest = new CommentThreadRequest(snippet);
        restAssuredExtension.setBody(commentThreadRequest);
    }

    private String getCurrentTime() {
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return currentTime.format(formatter);
    }
}
