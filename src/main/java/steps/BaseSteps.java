package steps;

import io.cucumber.datatable.DataTable;
import utilities.RestAssuredExtension;

import java.util.List;
import java.util.Map;
import java.util.Properties;

public class BaseSteps {
    protected void setEndpointWithQueryParameters(RestAssuredExtension restAssuredExtension, String endpointName, DataTable queryParams) {
        restAssuredExtension.setEndpoint(endpointName);
        List<Map<String, String>> rows = queryParams.asMaps(String.class, String.class);
        Properties props = restAssuredExtension.readPropertyData();
        rows.forEach(row -> {
            switch (row.get("value")) {
                case "${key}":
                case "${channelId}":
                case "${wrongKey}":
                    String value = props.getProperty(row.get("value").replaceAll("\\$\\{(.+?)\\}", "$1")); //replace ${}
                    restAssuredExtension.addQueryParam(row.get("name"), value);
                    break;
                default:
                    restAssuredExtension.addQueryParam(row.get("name"), row.get("value"));
                    break;
            }
        });
    }
}
