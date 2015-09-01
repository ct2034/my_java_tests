package de.henkelchristian.springboot;

import java.util.Collection;
import java.util.Random;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.netflix.hystrix.*;
import com.netflix.hystrix.HystrixCommandMetrics.HealthCounts;

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
		
		// ------------------------------
		// Spring boot
		ConfigurableApplicationContext cac2 = SpringApplication.run(SpringBootAppletClient.class, args);

		// Hystrix + Metrics
		System.out.println("new GetPowerCommand(5, 82).execute(): " + new GetPowerCommand(5, 82).execute());
		powerMetrics = HystrixCommandMetrics.getInstance(HystrixCommandKey.Factory.asKey(GetPowerCommand.class.getSimpleName()));

		boolean run = true;
		Random rand = new Random(2);
		while (run) {
			int randPosInt = 0;
			for (int i = 0; i<1; i++) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
					run = false;
				}
				randPosInt = rand.nextInt() % 10;
				new GetPowerCommand(randPosInt, 82).execute();
			}
			System.out.println("new GetPowerCommand(rand, 82).execute(): " + new GetPowerCommand(randPosInt, 82).execute());
			System.out.println("getStatsStringFromMetrics(powerMetrics): " + getStatsStringFromMetrics(powerMetrics));
		}

	}
}
