package de.henkelchristian.springboot;

import java.util.Collection;

//import java.util.concurrent.ExecutionException;
//import java.util.concurrent.Future;

import org.springframework.boot.SpringApplication;
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
		
		powerMetrics = gpc.getMetrics();

		Collection<HystrixCommandMetrics> chcm = HystrixCommandMetrics.getInstances();
		for (HystrixCommandMetrics hcm : chcm) {
			System.out.println("hcm.getProperties().toString(): " + hcm.getProperties().toString());
			System.out.println("hcm.getProperties().metricsRollingStatisticalWindowInMilliseconds(): "
					+ hcm.getProperties().metricsRollingStatisticalWindowInMilliseconds().get());
		}

		// ------------------------------
		// Spring boot
		ConfigurableApplicationContext cac2 = SpringApplication.run(SpringBootAppletClient.class, args);

		// Hystrix
		boolean run = true;
		while (run) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
				run = false;
			}
			System.out.println("GetPowerCommand.run(5, 82): " + gpc.run());
			System.out.println("GetPowerCommand.run(50, 82): " + gpc.run());
			System.out.println("getStatsStringFromMetrics(powerMetrics): " + getStatsStringFromMetrics(powerMetrics));
		}

	}
}
