package net.oldgeek;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.integration.annotation.IntegrationComponentScan;

@SpringBootApplication
@IntegrationComponentScan
public class Main {

	public static void main(String[] args) {
		new SpringApplicationBuilder(Main.class)//
				.web(WebApplicationType.NONE)// newer Spring versions
				.run(args);
	}

}
