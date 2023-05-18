package model.comment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentThreadRequest {
    @JsonProperty("snippet")
    private Snippet snippet;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Snippet {
        @JsonProperty("channelId")
        private String channelId;
        @JsonProperty("videoId")
        private String videoId;
        @JsonProperty("topLevelComment")
        private TopLevelComment topLevelComment;

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TopLevelComment {
        @JsonProperty("snippet")
        private CommentSnippet snippet;

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CommentSnippet {
        @JsonProperty("textOriginal")
        private String textOriginal;

    }

}
