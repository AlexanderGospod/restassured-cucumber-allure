package steps;

import io.cucumber.java.en.And;
import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;
import model.comment.comment.CommentThreadRequest;
import model.comment.comment.CommentThreadRequest.*;
import model.comment.comment.CommentUpdateRequest;
import pojo.comment.CommentThread;
import pojo.comment.CommentThreadList;
import pojo.comment.CommentUpdate;
import utilities.RestAssuredExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

public class CommentsSteps {
    private ResponseOptions<Response> response;
    private final RestAssuredExtension restAssuredExtension;

    private CommentThreadList commentThreadList;
    private CommentThread commentThread;
    private String comment;
    private String commentForUpdate;

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
        System.out.println(response.getBody().asString());
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
        this.comment = comment + ": " + getCurrentTime();
        CommentSnippet commentSnippet = new CommentSnippet(this.comment);
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

    @And("I have a body for updating comment")
    public void setUpdateCommentBody() {
        // Create a new CommentUpdateRequest object
        CommentUpdateRequest commentUpdateRequest = new CommentUpdateRequest();
        // Set the values for the properties
        commentUpdateRequest.setId(commentThread.getId());
        CommentUpdateRequest.Snippet snippet = new CommentUpdateRequest.Snippet();
        commentForUpdate = comment + " changed at " + getCurrentTime();
        snippet.setTextOriginal(commentForUpdate);
        commentUpdateRequest.setSnippet(snippet);
        restAssuredExtension.setBody(commentUpdateRequest);
    }

    @And("the modified comment message is displayed")
    public void verifyModifiedCommentMessage() {
        response = CommonSteps.getResponse();
        String displayedComment = response.getBody().as(CommentUpdate.class).getSnippet().getTextDisplay();
        assertThat(displayedComment).isEqualTo(commentForUpdate);
    }
}
