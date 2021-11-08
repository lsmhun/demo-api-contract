package hu.lsm.demo.contract.client.startup;

import hu.lsm.demo.contract.client.service.TemperatureService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.DefaultApplicationArguments;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class TemperatureStartupArgumentCollectorTest {

    @Mock
    private TemperatureService temperatureService;
    @InjectMocks
    private TemperatureStartupArgumentCollector temperatureStartupArgumentCollector;

    @Test
    void testMissingArguments() {
        var appArgs = new DefaultApplicationArguments("");
        assertThrows(IllegalArgumentException.class, () -> temperatureStartupArgumentCollector.run(appArgs));
    }
}