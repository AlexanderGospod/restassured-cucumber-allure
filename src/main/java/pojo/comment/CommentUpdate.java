package pojo.comment;

import com.fasterxml.jackson.annotation.JsonProperty;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import lombok.Data;

@Data
public class CommentUpdate {
    @NotNull
    private String kind;
    @NotNull
    private String etag;
    @NotNull
    private String id;
    @NotNull
    private Snippet snippet;

    @Data
    public static class Snippet {
        @JsonProperty("textDisplay")
        @NotNull
        private String textDisplay;
        @JsonProperty("textOriginal")
        @NotNull
        private String textOriginal;
        @JsonProperty("authorDisplayName")
        @NotNull
        private String authorDisplayName;
        @JsonProperty("authorProfileImageUrl")
        @NotNull
        private String authorProfileImageUrl;
        @JsonProperty("authorChannelUrl")
        @NotNull
        private String authorChannelUrl;
        @JsonProperty("authorChannelId")
        @NotNull
        private AuthorChannelId authorChannelId;
        @JsonProperty("canRate")
        private boolean canRate;
        @JsonProperty("viewerRating")
        private String viewerRating;
        @JsonProperty("likeCount")
        private int likeCount;
        @JsonProperty("publishedAt")
        @NotNull
        private String publishedAt;
        @JsonProperty("updatedAt")
        @NotNull
        private String updatedAt;
    }

    @Data
    public static class AuthorChannelId {
        @NotNull
        private String value;
    }


}
