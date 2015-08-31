package de.henkelchristian.springboot;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.apache.catalina.core.ApplicationContext;

//import java.util.concurrent.ExecutionException;
//import java.util.concurrent.Future;

import org.junit.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.embedded.EmbeddedWebApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import com.netflix.hystrix.*;
import com.netflix.hystrix.HystrixCommandMetrics.HealthCounts;

//import rx.Observable;

public class App {
	private static HystrixCommandMetrics powerMetrics;
	
	private static String getStatsStringFromMetrics(HystrixCommandMetrics metrics) {
		StringBuilder m = new StringBuilder();
		if (metrics != null) {
			HealthCounts health = metrics.getHealthCounts();
			m.append("Requests: ").append(health.getTotalRequests()).append(" ");
			m.append("Errors: ").append(health.getErrorCount()).append(" (").append(health.getErrorPercentage())
					.append("%)   ");
			m.append("Mean: ").append(metrics.getTotalTimeMean()).append(" ");
			m.append("50th: ").append(metrics.getExecutionTimePercentile(50)).append(" ");
			m.append("75th: ").append(metrics.getExecutionTimePercentile(75)).append(" ");
			m.append("90th: ").append(metrics.getExecutionTimePercentile(90)).append(" ");
			m.append("99th: ").append(metrics.getExecutionTimePercentile(99)).append(" ");
		}
		return m.toString();
	}

	public static void main(String[] args) {
		System.out.println("init ...");
		
		GetPowerCommand gpc = new GetPowerCommand(5, 82);
		System.out.println("GetPowerCommand.run(5, 82): " + gpc.run());
		
		System.out.println("HystrixCommandMetrics.getInstances():" + HystrixCommandMetrics.getInstances());
		
				
				
		// ------------------------------
		// Spring boot
		ConfigurableApplicationContext cac2 = SpringApplication.run(SpringBootAppletClient.class, args);
		
		
		// Hystrix
		boolean run = true;
		while(run){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				run = false;
			}
			System.out.println("getStatsStringFromMetrics(someMetrics): " + getStatsStringFromMetrics(powerMetrics));
		}

	}
}
