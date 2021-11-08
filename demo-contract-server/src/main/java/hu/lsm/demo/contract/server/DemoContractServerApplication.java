package hu.lsm.demo.contract.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
public class DemoContractServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoContractServerApplication.class, args);
	}

}
