## Spring Batch + Spring Integration + Spring Integration DSL Example

I couldn't find a complete, up to date example so I created one in order to
learn how to do it.
I found the documentation and examples confusing, because many use xml, others
use JavaConfig, others a mix of everything.
I found the JavaConfig very confusing and difficult to follow.
I wanted a pure DSL configuration, which is way easier to follow.

The example is very simple.
It consists on a sample flow which watches the dropfolder directory for .txt
files.
The flow then transforms the message to a FileMessageToJobRequest and then
passes it to a JobLaunchingMessageHandler and finally prints the JobExecution
result.

Relevant resources:

https://docs.spring.io/spring-batch/docs/4.3.8/reference/html/spring-batch-integration.html

http://docs.spring.io/spring-integration/docs/5.5.18/reference/html/

https://github.com/spring-projects/spring-integration-java-dsl/wiki/spring-integration-java-dsl-reference

https://github.com/mminella/SpringBatchWebinar