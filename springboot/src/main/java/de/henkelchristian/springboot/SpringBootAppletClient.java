package de.henkelchristian.springboot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

@RestController
@EnableAutoConfiguration
public class SpringBootAppletClient {

	@RequestMapping("/")
	public String meta() {
		System.out.println("meta() called ..");

		String gotData = "";
		String[] toGet = { "/", "/json", "/function/8" };
		gotData = this.getData(82, Arrays.asList(toGet));

		return "<!DOCTYPE html><html><body><h1>Meta call</h1><p>" + gotData + "</p></body></html>";
	}

	@RequestMapping("/power")
	public String power() {
		System.out.println("power() called ..");

		String gotData = "";
		List<String> toGet = new ArrayList<String>();
		int max = 100;
		for(int i = 0; i<max; i+=1){
			GetPowerCommand gpc = new GetPowerCommand(i, 82);
			gotData+=gpc.run();
		}
		
		return "<!DOCTYPE html><html><body><h1>Power</h1><p>" + gotData + "</p></body></html>";
	}

	private String getData(int _serverPort, List<String> toGet) {
		String out = "";

		for (String s : toGet) {
			GetPowerCommand gpc = new GetPowerCommand(5, 82);
			System.out.println("GetPowerCommand.run(5, 82): " + gpc.run());
		}

		return out;

	}

}
