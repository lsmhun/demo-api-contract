package hu.lsm.demo.contract.server.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "temperature")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TemperatureData implements Serializable {
    @Id
    private Long id;
    private String countryCode;
    private Double temperature;
}
