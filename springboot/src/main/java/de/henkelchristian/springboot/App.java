package de.henkelchristian.springboot;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

//import java.util.concurrent.ExecutionException;
//import java.util.concurrent.Future;

import org.junit.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.netflix.hystrix.*;
import com.netflix.hystrix.HystrixCommandMetrics.HealthCounts;

//import rx.Observable;

public class App {
	@Test
	public void testSynchronous() {
		assertEquals("Hello World!", new CommandHelloWorld("World").execute());
		assertEquals("Hello Bob!", new CommandHelloWorld("Bob").execute());
	}

	private static String getStatsStringFromMetrics(HystrixCommandMetrics metrics) {
		StringBuilder m = new StringBuilder();
		if (metrics != null) {
			HealthCounts health = metrics.getHealthCounts();
			m.append("Requests: ").append(health.getTotalRequests()).append(" ");
			m.append("Errors: ").append(health.getErrorCount()).append(" (").append(health.getErrorPercentage())
					.append("%)   ");
			m.append("Mean: ").append(metrics.getExecutionTimePercentile(50)).append(" ");
			m.append("75th: ").append(metrics.getExecutionTimePercentile(75)).append(" ");
			m.append("90th: ").append(metrics.getExecutionTimePercentile(90)).append(" ");
			m.append("99th: ").append(metrics.getExecutionTimePercentile(99)).append(" ");
		}
		return m.toString();
	}

	public static void main(String[] args) {
		System.out.println("init ...");

		CommandHelloWorld c = new CommandHelloWorld("Bob");
		CommandHelloWorld cj = new CommandHelloWorld("Joe");
		CommandHelloWorld cb = new CommandHelloWorld("Bill");

		HystrixCommandMetrics someMetrics = HystrixCommandMetrics
				.getInstance(HystrixCommandKey.Factory.asKey(CommandHelloWorld.class.getSimpleName()));
		System.out.println("getStatsStringFromMetrics(someMetrics): " + getStatsStringFromMetrics(someMetrics));

		System.out.println("String: " + c.execute() + ", " + cj.execute() + ", " + cb.execute() + "!");

		System.out.println("getStatsStringFromMetrics(someMetrics): " + getStatsStringFromMetrics(someMetrics));

		// ------------------------------
		// Spring boot

		ConfigurableApplicationContext cac = SpringApplication.run(SpringBootTest.class, args);
		System.out.println(cac.getDisplayName() + " .isRunning(): " + cac.isRunning());

		try {
		    Thread.sleep(1000);               
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		
		System.out.println(cac.getDisplayName() + " .isRunning(): " + cac.isRunning());
		
		// Some info (from tutorial: https://spring.io/guides/gs/spring-boot/)
		System.out.println("Let's inspect the beans provided by Spring Boot:");

        String[] beanNames = cac.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }
		
		//cac.stop(); // (inherited from org.springframework.context.Lifecycle)
		//cac.close(); // actually closes the spring boot bean
		
		if(cac.isRunning()){
			System.out.println(cac.getDisplayName() + " .isRunning(): " + cac.isRunning() + "\n== NEW BEAN ==");
			@SuppressWarnings("unused")
			ConfigurableApplicationContext cac2 = SpringApplication.run(SpringBootAppletClient.class, args);
		}
	}
}
