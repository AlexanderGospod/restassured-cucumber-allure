Feature: Feature: Retrieve YouTube Activities
  As a developer I want to retrieve YouTube chanel activities

  @smoke
  Scenario: Retrieving activities with default parameters
    Given the API endpoint with the following query parameters:
      | name      | value            |
      | key       | youtubeApiKey    |
      | channelId | youtubeChannelId |
    When I send a GET request to the endpoint
    Then the response status code should be 200
    And the response should contain a list of channel activity events
    And the response should contain the default number of items (5)

  @critical
  Scenario: Retrieving activities with snippet parameter
    Given the API endpoint with the following query parameters:
      | name      | value            |
      | key       | youtubeApiKey    |
      | channelId | youtubeChannelId |
      | part      | snippet          |
    When I send a GET request to the endpoint
    Then the response status code should be 200
    And the response should contain a list of channel activity events
    And the response should contain the default number of items (5)
    And the response should include the required property snippet
    And the response should include only the specified resource property snippet

  @critical
  Scenario: Retrieving activities with contentDetails parameter
    Given the API endpoint with the following query parameters:
      | name      | value            |
      | key       | youtubeApiKey    |
      | channelId | youtubeChannelId |
      | part      | contentDetails   |
    When I send a GET request to the endpoint
    Then the response status code should be 200
    And the response should contain a list of channel activity events
    And the response should contain the default number of items (5)
    And the response should include the required property contentDetails
    And the response should include only the specified resource property contentDetails

  @critical
  Scenario: Retrieving activities with multiple parameters
    Given the API endpoint with the following query parameters:
      | name      | value            |
      | key       | youtubeApiKey    |
      | channelId | youtubeChannelId |
      | part      | contentDetails   |
      | part      | snippet          |
    When I send a GET request to the endpoint
    Then the response status code should be 200
    And the response should contain a list of channel activity events
    And the response should contain the default number of items (5)
    And the response should include the required property contentDetails
    And the response should include the required property snippet

  @extended
  Scenario: Retrieving activities with invalid API key
    Given the API endpoint with the following query parameters:
      | name      | value              |
      | key       | wrongYoutubeApiKey |
      | channelId | youtubeChannelId   |
    When I send a GET request to the endpoint
    Then the response status code should be 400
    And the response should contain an error message that API key not valid

  @extended
  Scenario: Retrieving activities without channelId
    Given the API endpoint with the following query parameters:
      | name | value         |
      | key  | youtubeApiKey |
    When I send a GET request to the endpoint
    Then the response status code should be 400
    And the response should contain an error message that No filter selected

  @extended
  Scenario: Retrieve OAuth 2.0 Authorized user's activities with mine param
    Given the API endpoint with the following query parameters:
      | name | value          |
      | part | contentDetails |
      | mine | true           |
    And I have a valid OAuth 2.0 access token
    When I send a GET request to the endpoint
    Then the response status code should be 200
    And the response should contain a list of channel activity events
    And the response should contain the default number of items (5)
