package pojo.comment;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentThread {
    @NotNull
    @JsonProperty("kind")
    private String kind;
    @NotNull
    @JsonProperty("etag")
    private String etag;
    @NotNull
    @JsonProperty("id")
    private String id;
    @NotNull
    @JsonProperty("snippet")
    private Snippet snippet;


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Snippet {
        @NotNull
        @JsonProperty("channelId")
        private String channelId;
        @NotNull
        @JsonProperty("videoId")
        private String videoId;
        @NotNull
        @JsonProperty("topLevelComment")
        private TopLevelComment topLevelComment;
        @NotNull
        @JsonProperty("canReply")
        private boolean canReply;
        @NotNull
        @JsonProperty("totalReplyCount")
        private int totalReplyCount;
        @NotNull
        @JsonProperty("isPublic")
        private boolean isPublic;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TopLevelComment {
        @NotNull
        @JsonProperty("kind")
        private String kind;
        @NotNull
        @JsonProperty("etag")
        private String etag;
        @NotNull
        @JsonProperty("id")
        private String id;
        @NotNull
        @JsonProperty("snippet")
        private CommentSnippet snippet;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CommentSnippet {
        @NotNull
        @JsonProperty("channelId")
        private String channelId;
        @NotNull
        @JsonProperty("videoId")
        private String videoId;
        @NotNull
        @JsonProperty("textDisplay")
        private String textDisplay;
        @NotNull
        @JsonProperty("textOriginal")
        private String textOriginal;
        @NotNull
        @JsonProperty("authorDisplayName")
        private String authorDisplayName;
        @NotNull
        @JsonProperty("authorProfileImageUrl")
        private String authorProfileImageUrl;
        @NotNull
        @JsonProperty("authorChannelUrl")
        private String authorChannelUrl;
        @NotNull
        @JsonProperty("authorChannelId")
        private AuthorChannelId authorChannelId;
        @NotNull
        @JsonProperty("canRate")
        private boolean canRate;
        @NotNull
        @JsonProperty("viewerRating")
        private String viewerRating;
        @NotNull
        @JsonProperty("likeCount")
        private int likeCount;
        @NotNull
        @JsonProperty("publishedAt")
        private String publishedAt;
        @NotNull
        @JsonProperty("updatedAt")
        private String updatedAt;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AuthorChannelId {
        @NotNull
        @JsonProperty("value")
        private String value;
    }
}
