package net.oldgeek;

import java.io.File;

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

	protected DirectChannel inputChannel() {
		return new DirectChannel();
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
	public IntegrationFlow integrationFlow() {
		// @formatter:off
		return IntegrationFlows //
				.from(fileReadingMessageSource(), c -> c.poller(Pollers.fixedDelay(5000)))//
				.channel(inputChannel()) //
				.handle(f -> System.out.println("Done.")) //
				.get();
		// @formatter:on
	}
}