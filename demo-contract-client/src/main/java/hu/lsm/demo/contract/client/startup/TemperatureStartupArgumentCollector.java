package hu.lsm.demo.contract.client.startup;

import hu.lsm.demo.contract.client.service.TemperatureService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class TemperatureStartupArgumentCollector implements ApplicationRunner {

    @Value("${temperature.countryList:}")
    private List<String> temperatureCountryList;

    private final TemperatureService temperatureService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if(ObjectUtils.isEmpty(temperatureCountryList)){
            throw new IllegalArgumentException("missing or invalid --temperature.countryList parameter. \n" +
                    "This should be a comma separated country code list. Example: \n" +
                    "--temperature.countryList=li,hu");
        }
        log.info("Hottest country(s): " + temperatureService.getHottestCountry(temperatureCountryList));
    }
}
