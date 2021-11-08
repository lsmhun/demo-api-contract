package hu.lsm.demo.contract.server.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TemperatureDataTest {

    @Test
    void testSerialization() throws JsonProcessingException {
        TemperatureData temperatureData = new TemperatureData();
        temperatureData.setTemperature(1.0);
        temperatureData.setCountryCode("is");
        temperatureData.setId(123L);
        ObjectMapper objectMapper = new ObjectMapper();
        String temperatureDataAsString = objectMapper.writeValueAsString(temperatureData);
        assertEquals("{\"id\":123,\"countryCode\":\"is\",\"temperature\":1.0}", temperatureDataAsString);
    }
}