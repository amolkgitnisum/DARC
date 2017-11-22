package org.hac.drc.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan("org.hac.drc.controller,org.hac.drc.dao,org.hac.drc.service")
public class DarcApplication {

	public static void main(String[] args) {
		SpringApplication.run(DarcApplication.class, args);
	}
}
