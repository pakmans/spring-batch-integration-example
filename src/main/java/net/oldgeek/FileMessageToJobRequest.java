package net.oldgeek;

import java.io.File;
import java.util.Date;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.integration.launch.JobLaunchRequest;
import org.springframework.integration.annotation.Transformer;
import org.springframework.messaging.Message;

// See http://docs.spring.io/spring-batch/trunk/reference/html/springBatchIntegration.html#launching-batch-jobs-through-messages
public class FileMessageToJobRequest {

	private Job job;

	private String fileParameterName = "input.file.name";

	public void setFileParameterName(String fileParameterName) {
		this.fileParameterName = fileParameterName;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	@Transformer// inputChannel and outputChannel setup in sampleFlow Bean (check IntegrationConfig.class)
	public JobLaunchRequest toRequest(Message<File> message) {
		JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();

		jobParametersBuilder.addString(fileParameterName, message.getPayload().getAbsolutePath());
		// TODO: I do not know hot fix this yet, but not supposed to 
		// have to add a dummy value to make job params unique, or else spring batch
		// will only run it the first time
		// Suppose there must be some default we need to change to allow multiple jobs executions with the same parameters
		jobParametersBuilder.addDate("timestamp", new Date());

		return new JobLaunchRequest(job, jobParametersBuilder.toJobParameters());
	}
}