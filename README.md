# cucumber7, restAssured, junit-platform-engine, Allure, parallelExecution

## Project Description

#### Test automation framework for integration testing (youtube api is used for tests) includes:
- Running tests in parallel
- Utility for step-by-step construction of a request (query params, headers, body) using RequestSpecBuilder
- Utility to get a OAuth 2.0 token by scope using Google's authorization server and store it for tests
- Utility for parameter validation (recursive method, runs through all the parameters of the pojo class, checks that the parameters with the @NotNull annotation are not null)
- Pojo (model) classes for deserialization (serialization) of objects using lombok and jackson annotations
- Awaitility library for setting up a repeated request for a response from the server if the response was not ready
- Assertj library to check the results in tests
- Building an allure report
- Property files with settings for accessing the youtube api

## Api documentation can be found here
```
https://developers.google.com/youtube/v3/docs/
```

## To run tests locally

- Launch RunCucumberTest class
- With one of the mvn commands:
```
mvn install
mvn clean test
```

## Configuration
The cucumber settings are located (configuring parallel execution with the desired number of threads):
```
src/test/resources/junit-platform.properties
```

## Building report locally
To start the allure server and view the report:
```
mvn allure:serve
```




