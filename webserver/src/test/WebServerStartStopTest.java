package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import org.junit.Test;
import static org.junit.Assert.*;

import server.WebServer;
import java.net.*;

public class WebServerStartStopTest {

	WebServer server = null;

	@Test
	public void testStopServer() throws IOException {

		server = new WebServer("127.0.0.1", 10011, "./server", "./server");
		String temp;
		server.start();
		assertEquals(true, server.getServerStatus());

		URL url1 = new URL("http://127.0.0.1:10011/S");

		BufferedReader in = new BufferedReader(new InputStreamReader(
				url1.openStream()));

		try {
			while ((temp = in.readLine()) != null) {

				if (temp.contains("404 No page found")) {
					break;
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		server.stopServer();
		assertTrue(!server.getServerStatus());
		in.close();
	}

	@Test(expected = UnknownHostException.class)
	public void testCreteMethod() throws UnknownHostException, IOException {

		new WebServer("127.11111.1111.1", 55455, "./server", "./server");

	}
}
