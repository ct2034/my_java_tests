package de.henkelchristian.springboot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

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
		gotData = this.getData(82, toGet);

		return "<!DOCTYPE html><html><body><h1>Meta call</h1><p>" + gotData + "</p></body></html>";
	}

	private String getData(int _serverPort, String[] toGet) {
		String out = "";

		for (String s : toGet) {
			String url = "http://localhost:" + _serverPort + s;

			URL obj;
			try {
				obj = new URL(url);
			} catch (MalformedURLException e2) {
				e2.printStackTrace();
				return "";
			}
			HttpURLConnection con;
			try {
				con = (HttpURLConnection) obj.openConnection();
			} catch (IOException e2) {
				e2.printStackTrace();
				return "";
			}

			try {
				con.setRequestMethod("GET");
			} catch (ProtocolException e1) {
				e1.printStackTrace();
				return "";
			}

			int responseCode = 0;
			try {
				responseCode = con.getResponseCode();
			} catch (IOException e1) {
				e1.printStackTrace();
				return "";
			}

			System.out.println("\nSending 'GET' request to URL : " + url);
			System.out.println("Response Code : " + responseCode);

			BufferedReader in;
			try {
				in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			} catch (IOException e) {
				e.printStackTrace();
				return "";
			}
			String inputLine;
			StringBuffer response = new StringBuffer();

			try {
				while ((inputLine = in.readLine()) != null)

				{
					response.append(inputLine);
				}
			} catch (IOException e) {
				e.printStackTrace();
				return "";
			}

			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
				return "";
			}

			// print result
			System.out.println(response.toString());
			out += response.toString() + "\n";
		}

		return out;

	}

}
