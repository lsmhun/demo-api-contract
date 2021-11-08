package hu.lsm.demo.contract.client.service;

import hu.lsm.demo.contract.client.apiclient.TemperatureApiClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class TemperatureService {

    private final TemperatureApiClient temperatureApiClient;

    public String getHottestCountry(List<String> countryCodes) {
        double hottestTemperature = Double.MIN_VALUE;
        var countryTemperature = new HashMap<String, Double>();
        for (String countryCode : countryCodes) {
            var avgTemperature = getCountryAvgTemperature(countryCode);
            if (!avgTemperature.isNaN()) {
                countryTemperature.put(countryCode, avgTemperature);
                if (hottestTemperature < avgTemperature) {
                    hottestTemperature = avgTemperature;
                }
            }
        }
        double finalHottestTemperature = hottestTemperature;
        return countryTemperature.entrySet().stream()
                .filter(entr -> entr.getValue().compareTo(finalHottestTemperature) == 0)
                .sorted(Map.Entry.comparingByKey())
                .map(Map.Entry::getKey)
                .sorted()
                .collect(Collectors.joining(","));

    }

    private Double getCountryAvgTemperature(String countryCode) {
        var res = Double.NaN;
        try {
            res = temperatureApiClient.getAvgTemperatureByCountryCode(countryCode);
        } catch (Exception ex) {
            log.error("Unable to get average temperature for country code: " + countryCode, ex);
        }
        return res;
    }
}
