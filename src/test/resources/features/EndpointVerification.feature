Feature: Activities API Endpoint Verification

  @smoke
  Scenario: Retrieving activities with default parameters
    Given the API endpoint for activities
    When a GET request is sent to the endpoint
    Then the response status code should be 200
    And the response should contain a list of channel activity events
    And the response should contain the default number of items (5)

  Scenario: Retrieving activities with snippet parameter
    Given the API endpoint for activities
    And the query param "part" with the value "snippet" is added
    When a GET request is sent to the endpoint
    Then the response status code should be 200
    And the response should contain a list of channel activity events
    And the response should contain the default number of items (5)
    And the response should include the required property snippet
    And the response should include only the specified resource property snippet

  Scenario: Retrieving activities with contentDetails parameter
    Given the API endpoint for activities
    And the query param "part" with the value "contentDetails" is added
    When a GET request is sent to the endpoint
    Then the response status code should be 200
    And the response should contain a list of channel activity events
    And the response should contain the default number of items (5)
    And the response should include the required property contentDetails
    And the response should include only the specified resource property contentDetails

  Scenario: Retrieving activities with multiple parameters
    Given the API endpoint for activities
    And the query param "part" with the value "contentDetails" is added
    And the query param "part" with the value "snippet" is added
    When a GET request is sent to the endpoint
    Then the response status code should be 200
    And the response should contain a list of channel activity events
    And the response should contain the default number of items (5)
    And the response should include the required property contentDetails
    And the response should include the required property snippet

  Scenario: Retrieving activities with invalid API key
    Given the API endpoint for activities
    And set an invalid API key
    When a GET request is sent to the endpoint
    Then the response status code should be 400
    And the response should contain an error message
