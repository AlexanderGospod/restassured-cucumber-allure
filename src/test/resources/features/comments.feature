Feature: Interaction with comments
  As a developer I want to read, write, delete, reply to comments

  @smoke
  Scenario: Get list of comments for the video
    Given the API endpoint "commentThreads" with the following query parameters:
      | name    | value      |
      | key     | ${key}     |
      | videoId | ${videoId} |
    When I send a GET request to the endpoint
    Then the response status code should be 200
    And the response should include the required data about list of comments
    And the response should contain a list of comments

  @smoke
  Scenario: Write a comment to the video
    Given the API endpoint "commentThreads" with the following query parameters:
      | name | value   |
      | part | snippet |
    And a valid OAuth 2.0 access token with "YOUTUBE_FORCE_SSL" scope
    And I have a request body with comment "my comment with time"
    When I send a POST request to endpoint
    Then the response status code should be 200
    And the response should include the required comment data




