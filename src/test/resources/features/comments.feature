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
    And a OAuth 2.0 access token with "YOUTUBE_FORCE_SSL" scope
    And I have a request body with channelId, videoId and comment "my comment with time"
    When I send a POST request to endpoint
    Then the response status code should be 200
    And the response should include the required comment data
    And the written comment is displayed

  @smoke
  Scenario: Write a comment to the comment
    #  arrange
    Given the API endpoint "commentThreads" with the following query parameters:
      | name    | value      |
      | key     | ${key}     |
      | videoId | ${videoId} |
    When I send a GET request to the endpoint
    And the response should include the required data about list of comments
    And the response should contain a list of comments
    #  act
    Given the API endpoint "comments" with the following query parameters:
      | name | value   |
      | part | snippet |
    And a OAuth 2.0 access token with "YOUTUBE_FORCE_SSL" scope
    And I have a body for responding to an existing comment
    When I send a POST request to endpoint
    Then the response status code should be 200
    And the answer to a comment is displayed

  @smoke
  Scenario: Update the video comment
    #  arrange
    Given the API endpoint "commentThreads" with the following query parameters:
      | name | value   |
      | part | snippet |
    And a OAuth 2.0 access token with "YOUTUBE_FORCE_SSL" scope
    And I have a request body with channelId, videoId and comment "my comment with time"
    When I send a POST request to endpoint
    And the response should include the required comment data
    #  act
    Given the API endpoint "comments" with the following query parameters:
      | name | value   |
      | part | snippet |
    And a OAuth 2.0 access token with "YOUTUBE_FORCE_SSL" scope
    And I have a body for updating comment
    When I send a PUT request to endpoint
    Then the response status code should be 200
    And the modified comment is displayed

  @smoke
  Scenario: Delete the video comment
    #  arrange
    Given the API endpoint "commentThreads" with the following query parameters:
      | name    | value      |
      | key     | ${key}     |
      | videoId | ${videoId} |
    When I send a GET request to the endpoint
    And the response should include the required data about list of comments
    #  act
    Given the API endpoint "comments"
    And a OAuth 2.0 access token with "YOUTUBE_FORCE_SSL" scope
    And a comment id that I will delete
    When I send a DELETE request to endpoint
    Then the response status code should be 204
    And the comment is no longer displayed







