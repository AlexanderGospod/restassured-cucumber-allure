package pojo.response.comment;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnswerToComment {
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
        @JsonProperty("textDisplay")
        private String textDisplay;

        @NotNull
        @JsonProperty("textOriginal")
        private String textOriginal;

        @NotNull
        @JsonProperty("parentId")
        private String parentId;

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
