package pojo.comment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentThreadList {
    @NotNull
    @JsonProperty("kind")
    private String kind;
    @NotNull
    @JsonProperty("etag")
    private String etag;
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonProperty("nextPageToken")
    private String nextPageToken;
    @NotNull
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
    @NotNull
    @JsonProperty("items")
    private List<CommentThread> items;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PageInfo {
        @NotNull
        @JsonProperty("totalResults")
        private int totalResults;
        @NotNull
        @JsonProperty("resultsPerPage")
        private int resultsPerPage;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CommentThread {
        @NotNull
        @JsonProperty("kind")
        private String kind;
        @NotNull
        @JsonProperty("etag")
        private String etag;
        @NotNull
        @JsonProperty("id")
        private String id;
    }
}
