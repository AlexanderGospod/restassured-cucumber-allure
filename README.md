# cucumber7-restAssured-junit5-Allure-parallelExecution
YouTube api testing project, api documentation can be found here
```
https://developers.google.com/youtube/v3/docs/
```

## To run tests locally:

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



