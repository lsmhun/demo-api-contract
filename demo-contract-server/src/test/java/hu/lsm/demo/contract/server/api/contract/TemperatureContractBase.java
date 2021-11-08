package hu.lsm.demo.contract.server.api.contract;

import hu.lsm.demo.contract.server.controller.TemperatureController;
import hu.lsm.demo.contract.server.service.TemperatureService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK
//,properties = "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration, org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration"
)
@DirtiesContext
@AutoConfigureMessageVerifier
public class TemperatureContractBase {

    @Mock
    private TemperatureService temperatureService;

    private TemperatureController temperatureController;

    @BeforeEach
    void setup(){
        when(temperatureService.getTemperatureByCountryCode(anyString())).thenReturn(Double.NaN);
        when(temperatureService.getTemperatureByCountryCode("li")).thenReturn(10.2);
        temperatureController = new TemperatureController(temperatureService);
        StandaloneMockMvcBuilder standaloneMockMvcBuilder = MockMvcBuilders.standaloneSetup(temperatureController);
        RestAssuredMockMvc.standaloneSetup(standaloneMockMvcBuilder);
    }
}
