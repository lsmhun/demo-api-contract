package hu.lsm.demo.contract.server.repository;

import hu.lsm.demo.contract.server.model.TemperatureData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemperatureRepository extends JpaRepository<TemperatureData, Long> {
    TemperatureData findByCountryCode(String countryCode);
}
