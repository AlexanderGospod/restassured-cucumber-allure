package pojo.request.comment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentUpdateRequest {
    @JsonProperty("id")
    private String id;

    @JsonProperty("snippet")
    private Snippet snippet;


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Snippet {
        @JsonProperty("textOriginal")
        private String textOriginal;

    }
}

