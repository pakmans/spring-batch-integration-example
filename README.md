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

http://docs.spring.io/spring-batch/3.0.x/reference/html/springBatchIntegration.html

http://docs.spring.io/spring-integration/docs/4.3.10.RELEASE/reference/html/

https://github.com/spring-projects/spring-integration-java-dsl/wiki/spring-integration-java-dsl-reference

https://github.com/mminella/SpringBatchWebinar

Note: This project is not running in JRE 17 (likely version conflict : IllegalStateException: ApplicationEventMulticaster not initialized), run it in JRE 11 instead while a fix is not found.