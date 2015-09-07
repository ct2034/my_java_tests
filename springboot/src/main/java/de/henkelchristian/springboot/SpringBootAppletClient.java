package de.henkelchristian.springboot;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandMetrics;
import com.netflix.hystrix.HystrixCommandMetrics.HealthCounts;
import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;

@RestController
@EnableAutoConfiguration
@EnableHystrix
public class SpringBootAppletClient {
	@RequestMapping("/power")
	public String power() {
		System.out.println("power() called ..");
		
		System.out.println("new GetPowerCommand(5, 82).execute(): " + new GetPowerCommand(5, 82).execute());

		String gotData = "";
		int max = 10;
		for(int i = 0; i<max; i+=1){
			gotData+=new GetPowerCommand(i, 82).execute();
		}
						
		return "<!DOCTYPE html><html><body><h1>Power</h1><p>" + gotData + "</p></body></html>";
	}
	
	@RequestMapping("/hystrix_metrics")
	public String metrics() {
		System.out.println("metrics() called ..");
		
		return getStatsStringFromMetrics(HystrixCommandMetrics.getInstance(HystrixCommandKey.Factory.asKey(GetPowerCommand.class.getSimpleName())));
	}
	
	@Bean
    public ServletRegistrationBean hystrixStreamServlet() {
        return new ServletRegistrationBean(new HystrixMetricsStreamServlet(), "/hystrix.stream");
    }
	
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

}
