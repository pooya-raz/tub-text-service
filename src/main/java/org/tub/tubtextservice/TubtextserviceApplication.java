package org.tub.tubtextservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.tub.tubtextservice.adapter.semanticmediawiki.model.property.TubProperties;

@SpringBootApplication
@EnableConfigurationProperties(TubProperties.class)
public class TubtextserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TubtextserviceApplication.class, args);
	}

}
