Feature: Interaction with comments
  As a developer I want to read, delete, reply to comments

#  @smoke
#  Scenario: Get comments for the video
#    Given the API endpoint "commentThreads" with the following query parameters:
#      | name    | value          |
#      | key     | youtubeApiKey  |
#      | part    | snippet        |
#      | videoId | youtubeVideoId |
#    And a valid OAuth 2.0 access token with "YOUTUBE_READONLY" scope
#    When I send a GET request to the endpoint
#    Then the response status code should be 200
#    And the response should contain a list of comments
#    And the response should contain the comments with the specified IDs