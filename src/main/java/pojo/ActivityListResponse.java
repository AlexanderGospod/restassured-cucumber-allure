package pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityListResponse {
    @JsonProperty("kind")
    private String kind;
    @JsonProperty("etag")
    private String etag;
    @JsonProperty("items")
    private List<Activity> items;
    @JsonProperty("nextPageToken")
    private String nextPageToken;
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Activity {
        @JsonProperty("kind")
        private String kind;
        @JsonProperty("etag")
        private String etag;
        @JsonProperty("id")
        private String id;
        @JsonIgnoreProperties(ignoreUnknown = true)
        @JsonProperty("snippet")
        private Snippet snippet;
        @JsonIgnoreProperties(ignoreUnknown = true)
        @JsonProperty("contentDetails")
        private ContentDetails contentDetails;
    }

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
    public static class Snippet {
        @JsonProperty("publishedAt")
        private String publishedAt;
        @JsonProperty("channelId")
        private String channelId;
        @JsonProperty("title")
        private String title;
        @JsonProperty("description")
        private String description;
        @JsonProperty("thumbnails")
        private ActivityThumbnail thumbnails;
        @JsonProperty("channelTitle")
        private String channelTitle;
        @JsonProperty("type")
        private String type;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ActivityThumbnail {
        @JsonProperty("defaultImage")
        private ActivityThumbnailImage defaultImage;
        @JsonProperty("mediumImage")
        private ActivityThumbnailImage mediumImage;
        @JsonProperty("highImage")
        private ActivityThumbnailImage highImage;
        @JsonProperty("standardImage")
        private ActivityThumbnailImage standardImage;
        @JsonProperty("maxresImage")
        private ActivityThumbnailImage maxresImage;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ContentDetails {

        private Upload upload;
        private PlaylistItem playlistItem;

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Upload {

            private String videoId;

        }

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class PlaylistItem {

            private ResourceId resourceId;
            private String playlistId;

            @Data
            @NoArgsConstructor
            @AllArgsConstructor
            public static class ResourceId {

                private String kind;
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