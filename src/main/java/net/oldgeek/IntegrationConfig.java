package net.oldgeek;

import java.io.File;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.integration.launch.JobLaunchingMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.core.Pollers;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.FileReadingMessageSource.WatchEventType;
import org.springframework.integration.file.filters.SimplePatternFileListFilter;

@Configuration
public class IntegrationConfig {

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private Job sampleJob;

	protected DirectChannel inputChannel() {
		return new DirectChannel();
	}

	@Bean
	public IntegrationFlow sampleFlow() {
		// @formatter:off
		return IntegrationFlows //
				.from(fileReadingMessageSource(), c -> c.poller(Pollers.fixedDelay(5000)))//
				.channel(inputChannel()) //
				.transform(fileMessageToJobRequest()) //
				.handle(jobLaunchingMessageHandler()) //
				.handle(jobExecution -> {
					System.out.println(jobExecution.getPayload());
				}) //
				.get();
		// @formatter:on
	}

	@Bean
	public MessageSource<File> fileReadingMessageSource() {
		FileReadingMessageSource source = new FileReadingMessageSource();
		source.setDirectory(new File("dropfolder"));
		source.setFilter(new SimplePatternFileListFilter("*.txt"));
		source.setUseWatchService(true);
		source.setWatchEvents(WatchEventType.CREATE);
		return source;
	}

	@Bean
	FileMessageToJobRequest fileMessageToJobRequest() {
		FileMessageToJobRequest transformer = new FileMessageToJobRequest();
		transformer.setJob(sampleJob);
		transformer.setFileParameterName("file_path");
		return transformer;
	}

	@Bean
	JobLaunchingMessageHandler jobLaunchingMessageHandler() {
		JobLaunchingMessageHandler handler = new JobLaunchingMessageHandler(jobLauncher);
		return handler;
	}

	// @Bean
	// JobLaunchingGateway jobLaunchingGateway() {
	// JobLaunchingGateway gateway = new JobLaunchingGateway(jobLauncher);
	// return gateway;
	// }

}