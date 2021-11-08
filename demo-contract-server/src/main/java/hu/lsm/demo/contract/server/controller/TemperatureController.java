package hu.lsm.demo.contract.server.controller;

import hu.lsm.demo.contract.api.server.TemperatureApi;
import hu.lsm.demo.contract.server.service.TemperatureService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class TemperatureController implements TemperatureApi {

    private final TemperatureService temperatureService;

    @Override
    public ResponseEntity<Double> getAvgTempByCountryCode(String countryCode) {
        return ResponseEntity.ok(temperatureService.getTemperatureByCountryCode(countryCode));
    }
}
