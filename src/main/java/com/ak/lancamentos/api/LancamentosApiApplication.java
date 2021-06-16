package com.ak.lancamentos.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.ak.lancamentos.api.config.property.LancamentoApiProperty;

@SpringBootApplication
@EnableConfigurationProperties(LancamentoApiProperty.class)
public class LancamentosApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(LancamentosApiApplication.class, args);
	}

}
