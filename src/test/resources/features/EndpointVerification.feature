Feature: Feature: Retrieve YouTube Activities
  As a developer I want to retrieve YouTube chanel activities

  @smoke
  Scenario: Retrieving activities with default parameters
    Given the API endpoint for activities
    And the query param for apiKey
    And the query param for channelId
    When I send a GET request to the endpoint
    Then the response status code should be 200
    And the response should contain a list of channel activity events
    And the response should contain the default number of items (5)

  Scenario: Retrieving activities with snippet parameter
    Given the API endpoint for activities
    And the query param for apiKey
    And the query param for channelId
    And the query param "part" with the value "snippet"
    When I send a GET request to the endpoint
    Then the response status code should be 200
    And the response should contain a list of channel activity events
    And the response should contain the default number of items (5)
    And the response should include the required property snippet
    And the response should include only the specified resource property snippet

  Scenario: Retrieving activities with contentDetails parameter
    Given the API endpoint for activities
    And the query param for apiKey
    And the query param for channelId
    And the query param "part" with the value "contentDetails"
    When I send a GET request to the endpoint
    Then the response status code should be 200
    And the response should contain a list of channel activity events
    And the response should contain the default number of items (5)
    And the response should include the required property contentDetails
    And the response should include only the specified resource property contentDetails

  Scenario: Retrieving activities with multiple parameters
    Given the API endpoint for activities
    And the query param for apiKey
    And the query param for channelId
    And the query param "part" with the value "contentDetails"
    And the query param "part" with the value "snippet"
    When I send a GET request to the endpoint
    Then the response status code should be 200
    And the response should contain a list of channel activity events
    And the response should contain the default number of items (5)
    And the response should include the required property contentDetails
    And the response should include the required property snippet

  Scenario: Retrieving activities with invalid API key
    Given the API endpoint for activities
    And the query param "key" with the value "WrongApiKeyAIzaSyDC37VNdFUn-4KNI_NpH4xp"
    And the query param for channelId
    When I send a GET request to the endpoint
    Then the response status code should be 400
    And the response should contain an error message that API key not valid

  Scenario: Retrieving activities without channelId
    Given the API endpoint for activities
    And the query param for apiKey
    When I send a GET request to the endpoint
    Then the response status code should be 400
    And the response should contain an error message that No filter selected

  Scenario: Retrieve OAuth 2.0 Authorized user's activities with mine param
    Given the API endpoint for activities
    And the query param "mine" with the value "true"
    And I have a valid OAuth 2.0 access token
    When I send a GET request to the endpoint
    Then the response status code should be 200
    And the response should contain a list of channel activity events
    And the response should contain the default number of items (5)
