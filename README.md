# cucumber7-restAssured-junit5-Allure-parallelExecution
Project with the ability to run in parallel execution with the desired number of threads.
Set in junit-platform.properties

To run tests locally:

- Launch RunCucumberTest class
- With mvn command:
```
mvn install
```

## Configuration
The cucumber settings are located:
```
src/test/resources/junit-platform.properties
```

## Peculiarities
The project is testing the api of YouTube, api documentation can be found here
```
https://developers.google.com/youtube/v3/docs/
```

### Building report locally
To start the allure server and view the report:
```
mvn allure:serve
```



