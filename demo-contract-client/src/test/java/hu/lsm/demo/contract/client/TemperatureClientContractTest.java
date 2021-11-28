package hu.lsm.demo.contract.client;

import hu.lsm.demo.contract.api.client.ApiClient;
import hu.lsm.demo.contract.api.client.generated.TemperatureApi;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.NONE)
@AutoConfigureStubRunner(
        ids = {"hu.lsm:demo-contract-server:0.0.1-SNAPSHOT:stubs:6565"},
        stubsMode = StubRunnerProperties.StubsMode.LOCAL)
@DirtiesContext
public class TemperatureClientContractTest {

    private TemperatureApi temperatureApi;

    private TemperatureApi temperatureApi() {
        if (temperatureApi == null) {
            temperatureApi = new TemperatureApi(
                    new ApiClient()
                            .setBasePath("http://localhost:6565"));
        }
        return temperatureApi;
    }

    @Test
    void testGetLiTemperature() {
        Double contractResponse = temperatureApi().getAvgTempByCountryCode("li");
        assertEquals(10.2, contractResponse);
    }
}
