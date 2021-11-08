package hu.lsm.demo.contract.server.service;

import hu.lsm.demo.contract.server.model.TemperatureData;
import hu.lsm.demo.contract.server.repository.TemperatureRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TemperatureServiceTest {

    @Mock
    private TemperatureRepository temperatureRepository;
    @InjectMocks
    private TemperatureService temperatureService;

    @Test
    void getTemperatureByCountryCode() {
        when(temperatureRepository.findByCountryCode("li"))
                .thenReturn(
                        TemperatureData.builder()
                                .countryCode("li")
                                .id(123L)
                                .temperature(10.2)
                                .build());
        var res = temperatureService.getTemperatureByCountryCode("li");
        assertEquals(10.2, res);
    }

    @Test
    void getTemperatureByCountryCodeMissingCountry() {
        when(temperatureRepository.findByCountryCode(anyString())).thenReturn(null);
        var res = temperatureService.getTemperatureByCountryCode("hu");
        assertEquals(Double.NaN, res);
    }
}