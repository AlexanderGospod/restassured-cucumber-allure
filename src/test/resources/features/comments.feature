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
    And the response protocol version and content type, as expected
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
    And the response protocol version and content type, as expected
    And the response should include the required comment data
    And the written comment is displayed

  @smoke
  Scenario: Write a comment to the comment
    #  arrange (get list of comments)
    Given the API endpoint "commentThreads" with the following query parameters:
      | name    | value      |
      | key     | ${key}     |
      | videoId | ${videoId} |
    When I send a GET request to the endpoint
    And the response should include the required data about list of comments
    #  act
    Given the API endpoint "comments" with the following query parameters:
      | name | value   |
      | part | snippet |
    And a OAuth 2.0 access token with "YOUTUBE_FORCE_SSL" scope
    And I have a body for responding to an existing comment
    When I send a POST request to endpoint
    Then the response status code should be 200
    And the response protocol version and content type, as expected
    And the response should include the required data about answer to comment
    And the answer to a comment is displayed

  @smoke
  Scenario: Update the video comment
    #  arrange (write a comment)
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
    And the response protocol version and content type, as expected
    And the response should include the required data about comment update
    And the modified comment is displayed

  @smoke
  Scenario: Delete the video comment
    #  arrange (write a comment)
    Given the API endpoint "commentThreads" with the following query parameters:
      | name | value   |
      | part | snippet |
    And a OAuth 2.0 access token with "YOUTUBE_FORCE_SSL" scope
    And I have a request body with channelId, videoId and comment "my comment with time"
    When I send a POST request to endpoint
    And the response should include the required comment data
    #  act
    Given the API endpoint "comments"
    And a OAuth 2.0 access token with "YOUTUBE_FORCE_SSL" scope
    And a comment id that I will delete
    When I send a DELETE request to endpoint
    Then the response status code should be 204
    And the response protocol version and content type, as expected
    And the comment is no longer displayed

  @extended
  Scenario: Delete the video comment Idempotence
    #  arrange (write a comment)
    Given the API endpoint "commentThreads" with the following query parameters:
      | name | value   |
      | part | snippet |
    And a OAuth 2.0 access token with "YOUTUBE_FORCE_SSL" scope
    And I have a request body with channelId, videoId and comment "my comment with time"
    When I send a POST request to endpoint
    And the response should include the required comment data
    #  act
    Given the API endpoint "comments"
    And a OAuth 2.0 access token with "YOUTUBE_FORCE_SSL" scope
    And a comment id that I will delete
    When I send a DELETE request to endpoint
    Then the response status code should be 204
    And the response protocol version and content type, as expected
    And the comment is no longer displayed
    # assert idempotence
    When I send a DELETE request to endpoint
    Then the response status code should be 204
    And the response protocol version and content type, as expected

  @extended
  Scenario: Update the video comment Idempotence
    #  arrange (write a comment)
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
    And the response protocol version and content type, as expected
    And the response should include the required data about comment update
    And the modified comment is displayed
    # assert idempotence
    When I send a PUT request to endpoint
    Then the response status code should be 200
    And the response protocol version and content type, as expected
    And the response should include the required data about comment update
    And the modified comment is displayed

  @extended
  Scenario: Get list of comments for the video with invalid API key
    Given the API endpoint "commentThreads" with the following query parameters:
      | name    | value       |
      | key     | ${wrongKey} |
      | videoId | ${videoId}  |
    When I send a GET request to the endpoint
    Then the response status code should be 400

  @extended
  Scenario: Write a comment to the video with invalid OAuth 2.0 access token
    Given the API endpoint "commentThreads" with the following query parameters:
      | name | value   |
      | part | snippet |
    And an invalid OAuth 2.0 access token
    And I have a request body with channelId, videoId and comment "my comment with time"
    When I send a POST request to endpoint
    Then the response status code should be 401

  @extended
  Scenario: Write a comment to the video with insufficient rights OAuth 2.0 access token
    Given the API endpoint "commentThreads" with the following query parameters:
      | name | value   |
      | part | snippet |
    And a OAuth 2.0 access token with "YOUTUBE_READONLY" scope
    And I have a request body with channelId, videoId and comment "my comment with time"
    When I send a POST request to endpoint
    Then the response status code should be 403

  @extended
  Scenario: Write a comment to the comment with invalid OAuth 2.0 access token
    #  arrange (get list of comments)
    Given the API endpoint "commentThreads" with the following query parameters:
      | name    | value      |
      | key     | ${key}     |
      | videoId | ${videoId} |
    When I send a GET request to the endpoint
    And the response should include the required data about list of comments
    #  act
    Given the API endpoint "comments" with the following query parameters:
      | name | value   |
      | part | snippet |
    And an invalid OAuth 2.0 access token
    And I have a body for responding to an existing comment
    When I send a POST request to endpoint
    Then the response status code should be 401

  @extended
  Scenario: Write a comment to the comment with insufficient rights OAuth 2.0 access token
    #  arrange (get list of comments)
    Given the API endpoint "commentThreads" with the following query parameters:
      | name    | value      |
      | key     | ${key}     |
      | videoId | ${videoId} |
    When I send a GET request to the endpoint
    And the response should include the required data about list of comments
    #  act
    Given the API endpoint "comments" with the following query parameters:
      | name | value   |
      | part | snippet |
    And a OAuth 2.0 access token with "YOUTUBE_READONLY" scope
    And I have a body for responding to an existing comment
    When I send a POST request to endpoint
    Then the response status code should be 403

  @extended
  Scenario: Update the video comment with invalid OAuth 2.0 access token
    #  arrange (write a comment)
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
    And an invalid OAuth 2.0 access token
    And I have a body for updating comment
    When I send a PUT request to endpoint
    Then the response status code should be 401

  @extended
  Scenario: Update the video comment with insufficient rights OAuth 2.0 access token
    #  arrange (write a comment)
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
    And a OAuth 2.0 access token with "YOUTUBE_READONLY" scope
    And I have a body for updating comment
    When I send a PUT request to endpoint
    Then the response status code should be 403

  @extended
  Scenario: Delete the video comment with invalid OAuth 2.0 access token
    #  arrange (write a comment)
    Given the API endpoint "commentThreads" with the following query parameters:
      | name | value   |
      | part | snippet |
    And a OAuth 2.0 access token with "YOUTUBE_FORCE_SSL" scope
    And I have a request body with channelId, videoId and comment "my comment with time"
    When I send a POST request to endpoint
    And the response should include the required comment data
    #  act
    Given the API endpoint "comments"
    And an invalid OAuth 2.0 access token
    And a comment id that I will delete
    When I send a DELETE request to endpoint
    Then the response status code should be 401

  @extended
  Scenario: Delete the video comment with insufficient rights OAuth 2.0 access token
    #  arrange (write a comment)
    Given the API endpoint "commentThreads" with the following query parameters:
      | name | value   |
      | part | snippet |
    And a OAuth 2.0 access token with "YOUTUBE_FORCE_SSL" scope
    And I have a request body with channelId, videoId and comment "my comment with time"
    When I send a POST request to endpoint
    And the response should include the required comment data
    #  act
    Given the API endpoint "comments"
    And a OAuth 2.0 access token with "YOUTUBE_READONLY" scope
    And a comment id that I will delete
    When I send a DELETE request to endpoint
    Then the response status code should be 403











