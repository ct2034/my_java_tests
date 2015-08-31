package de.henkelchristian.springboot2;

import org.springframework.boot.SpringApplication;
import com.netflix.hystrix.*;
import com.netflix.hystrix.HystrixCommandMetrics.HealthCounts;

public class App {
	public static void main(String[] args) {
		System.out.println("init ...");
		SpringApplication app = new SpringApplication(SpringBootAppletServer.class);
		app.run();
	}
}
