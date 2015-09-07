package de.henkelchristian.springboot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.netflix.hystrix.*;
import com.netflix.hystrix.HystrixCommand;

public class GetPowerCommand extends HystrixCommand<String> {

	private final int in;
	private final int port;

	public GetPowerCommand(int in, int port) {
		super(HystrixCommandGroupKey.Factory.asKey("Server Command"));
		this.in = in;
		this.port = port;
	}

	@Override
	protected String run() throws IOException {
		String url = "http://localhost:" + this.port + "/function/" + this.in;
		String out = "";

		URL obj;
		try {
			obj = new URL(url);
		} catch (MalformedURLException e2) {
			e2.printStackTrace();
			return "";
		}
		HttpURLConnection con;
		con = (HttpURLConnection) obj.openConnection();

		try {
			con.setRequestMethod("GET");
		} catch (ProtocolException e1) {
			e1.printStackTrace();
			return "";
		}

//		int responseCode = 0;
//
//		responseCode = con.getResponseCode();

		// System.out.println("\nSending 'GET' request to URL : " + url);
		// System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		
		in.close();

		// print result
		// System.out.println(response.toString());

		// get only paragraph
		Pattern p = Pattern.compile("<p>.*</p>");
		Matcher m = p.matcher(response.toString());

		while (m.find()) {
			out += response.toString().substring(m.start(), m.end());
		}

		return out;
	}
	

    @Override
    protected String getFallback() {
        return "A fallback occured!";
    }
}
