Feature: Interaction with comments
  As a developer I want to read, delete, reply to comments

  @smoke
  Scenario: Get comments for the video
    Given the API endpoint "commentThreads" with the following query parameters:
      | name    | value      |
      | key     | ${key}     |
      | videoId | ${videoId} |
    When I send a GET request to the endpoint
    Then the response status code should be 200
    And the response should contain a list of comments
