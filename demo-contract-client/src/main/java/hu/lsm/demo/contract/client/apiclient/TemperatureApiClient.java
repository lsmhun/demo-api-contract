package hu.lsm.demo.contract.client.apiclient;

import hu.lsm.demo.contract.api.client.ApiClient;
import hu.lsm.demo.contract.api.client.generated.TemperatureApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TemperatureApiClient {

    @Value("${hu.lsm.contract.api.server.protocol:http}")
    private String protocol;
    @Value("${hu.lsm.contract.api.server.host:localhost}")
    private String host;
    @Value("${hu.lsm.contract.api.server.port:8080}")
    private String port;

    private TemperatureApi temperatureApi;

    private TemperatureApi temperatureApi() {
        if (temperatureApi == null) {
            temperatureApi = new TemperatureApi(
                    new ApiClient()
                            .setBasePath(protocol + "://" + host + ":" + port));
        }
        return temperatureApi;
    }

    public Double getAvgTemperatureByCountryCode(String countryCode) {
        TemperatureApi temperatureApi = temperatureApi();
        return temperatureApi.getAvgTempByCountryCode(countryCode);
    }
}
