package pojo.comment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentThreadListResponse {
    @JsonProperty("kind")
    private String kind;
    @JsonProperty("etag")
    private String etag;
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
    @JsonProperty("items")
    private List<CommentThread> items;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PageInfo {
        @JsonProperty("totalResults")
        private int totalResults;
        @JsonProperty("resultsPerPage")
        private int resultsPerPage;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CommentThread {
        @JsonProperty("kind")
        private String kind;
        @JsonProperty("etag")
        private String etag;
        @JsonProperty("id")
        private String id;
    }
}
