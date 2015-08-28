package de.henkelchristian.springboot;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

@RestController
@EnableAutoConfiguration
public class SpringBootTest {

	@RequestMapping("/")
	public String home() {
		return "<!DOCTYPE html><html><body><h1>Test</h1><p>Hello world!</p></body></html>";
	}
}