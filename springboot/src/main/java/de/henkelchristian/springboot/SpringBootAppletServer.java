package de.henkelchristian.springboot;

import java.util.Hashtable;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@EnableAutoConfiguration
public class SpringBootTest {

	@RequestMapping("/")
	public String home() {
		System.out.println("home() called ..");

		try {
			Thread.sleep(1000);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
		System.out.println("waited ..");

		return "<!DOCTYPE html><html><body><h1>Test</h1><p>Hello world!</p></body></html>";
	}

	@RequestMapping(value = "/function/{in}", method = RequestMethod.GET)
	public String function(@PathVariable int in) {
		try {
			Thread.sleep(10 * in);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
		int result = (int) java.lang.Math.pow(2, in);
		return "<!DOCTYPE html><html><body><h1>Function</h1><p>I guess .. </p><p>2^" + in + " = " + result
				+ "</p></body></html>";
	}

	@RequestMapping("/json")
	public String json() {
		ObjectMapper om = new ObjectMapper();
		
		try {
			// display
			int[] arr = {2, 0, 3, 4};
			Hashtable<String, Object> ht = new Hashtable<String, Object>();
			ht.put("Number", arr);
			ht.put("One", 1);
			return "<!DOCTYPE html><html><body><h1>Json</h1><p>" + om.writeValueAsString(ht) + "</p></body></html>";
		}
		catch (Exception e)
		{
			return e.toString();		
		}
	}
}