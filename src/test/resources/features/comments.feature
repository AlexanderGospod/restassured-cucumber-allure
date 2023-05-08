Feature: Interaction with comments
  As a developer I want to read, delete, reply to comments

  @smoke
  Scenario: Get comments for the video
    Given the API endpoint "commentThreads" with the following query parameters:
      | name    | value      |
      | key     | ${key}     |
      | videoId | ${videoId} |
    When I send a GET request to the endpoint
    Then the response status code from commentThreads should be 200
    And the response should contain a list of comments
    And the response should include the required comment data

  @smoke
  Scenario: Reply to a comment
    Given the API endpoint "commentThreads" with the following query parameters:
      | name    | value      |
      | videoId | ${videoId} |
    And a valid OAuth 2.0 access token with "YOUTUBE_FORCE_SSL" scope

#    Given I have the YouTube CommentThreads API endpoint
#    And I have a valid comment payload
#    When I send a POST request to "/commentThreads" with the comment payload
#    Then the response status code should be 201

