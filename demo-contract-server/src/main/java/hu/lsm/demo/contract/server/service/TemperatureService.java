package hu.lsm.demo.contract.server.service;

import hu.lsm.demo.contract.server.model.TemperatureData;
import hu.lsm.demo.contract.server.repository.TemperatureRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class TemperatureService {

    private final TemperatureRepository temperatureRepository;

    public Double getTemperatureByCountryCode(String countryCode) {
        log.info("Searching avg temperature by country code: " + countryCode);
        return Optional.ofNullable(temperatureRepository.findByCountryCode(countryCode))
                .orElse(TemperatureData.builder()
                        .temperature(Double.NaN)
                        .build())
                .getTemperature();
    }
}
