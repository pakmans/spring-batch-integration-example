package net.oldgeek;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.PassThroughLineMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

	@Autowired
	JobBuilderFactory jobBuilderFactory;

	@Autowired
	StepBuilderFactory stepBuilderFactory;

	@Bean
	Step sampleStep() {
		return stepBuilderFactory.get("sampleStep")//
				.<String, String>chunk(5) //
				.reader(itemReader(null)) // null will be replaced by #{jobParameters[file_path]}
				.writer(i -> i.stream().forEach(j -> System.out.println(j))) //
				.build();
	}

	@Bean
	Job sampleJob() {
		Job job = jobBuilderFactory.get("sampleJob") //
				.incrementer(new RunIdIncrementer()) //
				.start(sampleStep()) //
				.build();
		return job;
	}

	@Bean
	@StepScope
	FlatFileItemReader<String> itemReader(@Value("#{jobParameters[file_path]}") String filePath) {
		FlatFileItemReader<String> reader = new FlatFileItemReader<String>();
		final FileSystemResource fileResource = new FileSystemResource(filePath);
		reader.setResource(fileResource);
		reader.setLineMapper(new PassThroughLineMapper());
		return reader;
	}

}
