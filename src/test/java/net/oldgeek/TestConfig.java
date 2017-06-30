package net.oldgeek;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@EnableBatchProcessing
@Profile(value = "test")
public class TestConfig {

	@Bean
	JobLauncherTestUtils jobLauncherTestUtils() {
		JobLauncherTestUtils jltu = new JobLauncherTestUtils();
		return jltu;
	}

}
