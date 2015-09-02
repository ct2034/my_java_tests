package de.henkelchristian.springboot2;

import org.springframework.boot.SpringApplication;

public class App {
	public static void main(String[] args) {
		System.out.println("init ...");
		SpringApplication app = new SpringApplication(SpringBootAppletServer.class);
		app.run();
	}
}
