package model.comment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnswerToCommentRequest {
    @JsonProperty("snippet")

    private Snippet snippet;
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Snippet {
        @JsonProperty("parentId")

        private String parentId;
        @JsonProperty("textOriginal")

        private String textOriginal;
    }
}
