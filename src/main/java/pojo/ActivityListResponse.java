package pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityListResponse {
    @NotNull
    @JsonProperty("kind")
    private String kind;
    @NotNull
    @JsonProperty("etag")
    private String etag;
    @NotNull
    @JsonProperty("items")
    private List<Activity> items;
    @NotNull
    @JsonProperty("nextPageToken")
    private String nextPageToken;
    @NotNull
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Activity {
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
        @JsonIgnoreProperties(ignoreUnknown = true)
        @JsonProperty("snippet")
        private Snippet snippet;
        @NotNull
        @JsonIgnoreProperties(ignoreUnknown = true)
        @JsonProperty("contentDetails")
        private ContentDetails contentDetails;
    }

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
    public static class Snippet {
        @NotNull
        @JsonProperty("publishedAt")
        private String publishedAt;
        @NotNull
        @JsonProperty("channelId")
        private String channelId;
        @NotNull
        @JsonProperty("title")
        private String title;
        @NotNull
        @JsonProperty("description")
        private String description;
        @NotNull
        @JsonProperty("thumbnails")
        private ActivityThumbnail thumbnails;
        @NotNull
        @JsonProperty("channelTitle")
        private String channelTitle;
        @NotNull
        @JsonProperty("type")
        private String type;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ActivityThumbnail {
        @NotNull
        @JsonProperty("defaultImage")
        private ActivityThumbnailImage defaultImage;
        @NotNull
        @JsonProperty("mediumImage")
        private ActivityThumbnailImage mediumImage;
        @NotNull
        @JsonProperty("highImage")
        private ActivityThumbnailImage highImage;
        @NotNull
        @JsonProperty("standardImage")
        private ActivityThumbnailImage standardImage;
        @NotNull
        @JsonProperty("maxresImage")
        private ActivityThumbnailImage maxresImage;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ContentDetails {
        @NotNull
        @JsonProperty("upload")
        private Upload upload;
        @NotNull
        @JsonProperty("playlistItem")
        private PlaylistItem playlistItem;

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Upload {
            @NotNull
            @JsonProperty("videoId")
            private String videoId;

        }

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class PlaylistItem {
            @NotNull
            @JsonProperty("resourceId")
            private ResourceId resourceId;
            @NotNull
            @JsonProperty("playlistId")
            private String playlistId;

            @Data
            @NoArgsConstructor
            @AllArgsConstructor
            public static class ResourceId {
                @NotNull
                @JsonProperty("kind")
                private String kind;
                @NotNull
                @JsonProperty("videoId")
                private String videoId;

            }

        }

    }




    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ActivityThumbnailImage {
        @JsonProperty("url")
        private String url;
        @JsonProperty("width")
        private int width;
        @JsonProperty("height")
        private int height;
    }
}