package cgi;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.http.client.ClientProtocolException;

public class Application {

	public static void main(String[] args) throws ClientProtocolException, IOException, URISyntaxException {

				try {
					new CalcSalary();
				} catch (Exception e) {
					e.printStackTrace();
				}

	}

}
