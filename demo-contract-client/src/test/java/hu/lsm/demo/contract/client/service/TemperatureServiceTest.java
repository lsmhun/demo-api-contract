package hu.lsm.demo.contract.client.service;

import hu.lsm.demo.contract.client.apiclient.TemperatureApiClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TemperatureServiceTest {

    @Mock
    private TemperatureApiClient temperatureApiClient = mock(TemperatureApiClient.class);

    @InjectMocks
    private TemperatureService temperatureService;

    @BeforeEach
    void setUp() {
        when(temperatureApiClient.getAvgTemperatureByCountryCode("li")).thenReturn(10.2);
    }

    @Test
    void getHottestCountryWith2Country() {
        when(temperatureApiClient.getAvgTemperatureByCountryCode("hu")).thenReturn(10.3);
        assertEquals("hu", temperatureService.getHottestCountry(Arrays.asList("li", "hu")));
    }

    @Test
    void getHottestCountryWith2CountryOneWithDoubleNaN() {
        when(temperatureApiClient.getAvgTemperatureByCountryCode("hu")).thenReturn(Double.NaN);
        assertEquals("li", temperatureService.getHottestCountry(Arrays.asList("li", "hu")));
    }

    @Test
    void getHottestCountryWithSameAvgTemperature() {
        when(temperatureApiClient.getAvgTemperatureByCountryCode("hu")).thenReturn(10.2);
        assertEquals("hu,li", temperatureService.getHottestCountry(Arrays.asList("li", "hu")));
    }
}