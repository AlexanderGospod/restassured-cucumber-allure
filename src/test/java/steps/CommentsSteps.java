package steps;

import io.cucumber.java.en.And;
import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;
import pojo.request.comment.AnswerToCommentRequest;
import pojo.request.comment.CommentThreadRequest;
import pojo.request.comment.CommentThreadRequest.*;
import pojo.request.comment.CommentUpdateRequest;
import pojo.response.comment.AnswerToComment;
import pojo.response.comment.CommentThread;
import pojo.response.comment.CommentThreadList;
import pojo.response.comment.CommentUpdate;
import utilities.ParameterValidation;
import utilities.RestAssuredExtension;
import static org.awaitility.Awaitility.await;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static steps.CommonSteps.getResponse;

public class CommentsSteps {
    private ResponseOptions<Response> response;
    private final RestAssuredExtension restAssuredExtension;
    private CommentThreadList commentThreadList;
    private CommentThread commentThread;
    private String comment;
    private String commentForUpdate;
    private String answerToCommentMessage;
    private String commentId;

    public CommentsSteps() {
        restAssuredExtension = CommonSteps.getRestAssuredExtension();
    }

    @And("the response should contain a list of comments")
    public void assertThatListOfCommentsIsPresentInResponse() {
        assertThat(commentThreadList.getItems().size()).isGreaterThan(1);
    }

    @And("the response should include the required data about list of comments")
    public void verifyRequiredCommentThreadListData() {
        response = getResponse();
        commentThreadList = response.getBody().as(CommentThreadList.class);
        ParameterValidation parameterValidation = new ParameterValidation();
        parameterValidation.checkThatAllParametersAnnotatedNotNullAreNotEqualNull(commentThreadList);
    }
    @And("the response should include the required data about answer to comment")
    public void verifyRequiredAnswerToCommentData() {
        response = getResponse();
        AnswerToComment answerToComment = response.getBody().as(AnswerToComment.class);
        ParameterValidation parameterValidation = new ParameterValidation();
        parameterValidation.checkThatAllParametersAnnotatedNotNullAreNotEqualNull(answerToComment);
    }

    @And("the response should include the required comment data")
    public void verifyRequiredCommentData() {
        response = getResponse();
        commentThread = response.getBody().as(CommentThread.class);
        ParameterValidation parameterValidation = new ParameterValidation();
        parameterValidation.checkThatAllParametersAnnotatedNotNullAreNotEqualNull(commentThread);
    }
    @And("the response should include the required data about comment update")
    public void verifyRequiredCommentUpdateData () {
        response = getResponse();
        CommentUpdate commentUpdate = response.getBody().as(CommentUpdate.class);
        ParameterValidation parameterValidation = new ParameterValidation();
        parameterValidation.checkThatAllParametersAnnotatedNotNullAreNotEqualNull(commentUpdate);
    }

    @And("I have a request body with channelId, videoId and comment {string}")
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
    @And("I have a body for responding to an existing comment")
    public void setAnswerToExistingComment() {
        // Create a new CommentUpdateRequest object
        AnswerToCommentRequest answerToCommentRequest = new AnswerToCommentRequest();
        // Set the values for the properties
        AnswerToCommentRequest.Snippet snippet = new AnswerToCommentRequest.Snippet();
        answerToCommentMessage = "My answer on the comment, answered at " + getCurrentTime();
        snippet.setTextOriginal(answerToCommentMessage);
        snippet.setParentId(commentThreadList.getItems().get(1).getId());
        answerToCommentRequest.setSnippet(snippet);
        restAssuredExtension.setBody(answerToCommentRequest);
    }

    @And("the modified comment is displayed")
    public void verifyModifiedCommentMessage() {
        response = getResponse();
        String displayedComment = response.getBody().as(CommentUpdate.class).getSnippet().getTextDisplay();
        assertThat(displayedComment).isEqualTo(commentForUpdate);
    }
    @And("the answer to a comment is displayed")
    public void verifyThatAnswerToCommentIsDisplayed() {
        response = getResponse();
        String displayedAnswer = response.getBody().as(AnswerToComment.class).getSnippet().getTextDisplay();
        assertThat(displayedAnswer).isEqualTo(answerToCommentMessage);
    }

    @And("the written comment is displayed")
    public void checkThatCommentIsDisplayed() {
        String displayedComment = commentThread.getSnippet().getTopLevelComment().getSnippet().getTextDisplay();
        assertThat(displayedComment).isEqualTo(comment);
    }

    @And("a comment id that I will delete")
    public void setCommentId() {
        commentId = commentThread.getId();
        restAssuredExtension.addQueryParam("id", commentId);
    }

    @And("the comment is no longer displayed")
    public void checkThatCommentIsNoLongerDisplayed() {
        //return Error if server response takes more than a minute without deleted comment ID.
        await()
                .atMost(60, SECONDS)
                .pollInterval(5, SECONDS)
                .until(this::isCommentDeleted);
    }

    private boolean isCommentDeleted() {
        response = restAssuredExtension.sendGetRequest();
        CommentThreadList commentThreadList = response.getBody().as(CommentThreadList.class);
        boolean commentExists = commentThreadList.getItems().stream()
                .anyMatch(commentThread -> commentThread.getId().equals(commentId));
        return !commentExists;
    }
}
