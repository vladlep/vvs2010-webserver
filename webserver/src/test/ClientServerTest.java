package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

import org.junit.After;
import org.junit.Test;

import server.WebServer;

public class ClientServerTest {
	WebServer server = null;

	@Test
	public void testConstructor() throws Exception {

		server = new WebServer("127.0.0.1", 10011, "./server", "./server");

		server.start();
		URL url = null;

		url = new URL("http://localhost:10011");
		URLConnection urlConn = null;
		urlConn = url.openConnection();
		urlConn.setDoOutput(true);
		urlConn.setUseCaches(false);

		DataOutputStream dis = null;
		dis = new DataOutputStream(urlConn.getOutputStream());
		dis.writeUTF("GET /ex.html HTTP");
		dis.writeUTF("altceva");

		server.stopServer();
		dis.close();
	}

	@Test(expected = IOException.class)
	public void testCreateSocket() throws UnknownHostException, IOException {

		try {
			server = new WebServer("127.0.0.1", 10011, "./server", "./server");

		} catch (IOException e) {
			e.printStackTrace();
		}

		new WebServer("127.0.0.1", 10011, "./server", "./server");

	}

	// no file
	@Test
	public void testCehckUnavailableFile() throws IOException {

		server = new WebServer("127.0.0.1", 10011, "./server", "./server");
		String temp;
		boolean flag = false;
		server.start();
		assertEquals(true, server.getServerStatus());

		URL url1 = new URL("http://127.0.0.1:10011/nofile");

		BufferedReader in = new BufferedReader(new InputStreamReader(
				url1.openStream()));

		try {
			while ((temp = in.readLine()) != null) {

				if (temp.contains("404 No page found")) {
					flag = true;
					break;
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertTrue(flag);
		server.stopServer();
		assertTrue(!server.getServerStatus());
		in.close();
	}

	// mentenance mode
	@Test
	public void testMentenanceMode() throws IOException {

		server = new WebServer("127.0.0.1", 10011, "./server", "./server");
		String temp;
		boolean flag = false;
		server.setMentenance(true);
		server.start();

		assertEquals(true, server.getServerStatus());

		URL url1 = new URL("http://127.0.0.1:10011/nofile");

		BufferedReader in = new BufferedReader(new InputStreamReader(
				url1.openStream()));

		try {
			while ((temp = in.readLine()) != null) {

				if (temp.contains("Mentenance mode.")) {
					flag = true;
					break;
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertTrue(flag);
		server.stopServer();
		assertTrue(!server.getServerStatus());
		in.close();
	}

	// simple file
	@Test
	public void testAnAvailableFile() throws IOException {

		server = new WebServer("127.0.0.1", 10011, "./server", "./server");
		String temp;
		boolean flag = false;

		server.start();
		assertEquals(true, server.getServerStatus());

		URL url1 = new URL("http://127.0.0.1:10011/cssinsamefolder.html");

		BufferedReader in = new BufferedReader(new InputStreamReader(
				url1.openStream()));

		try {
			while ((temp = in.readLine()) != null) {

				System.out.println(temp);

				if (temp.contains("Css in same folder.")) {
					flag = true;
					break;
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		assertTrue(flag);
		server.stopServer();
		assertTrue(!server.getServerStatus());
		in.close();
		in.close();
	}

	// css in other folder

	@Test
	public void testCssInFolder() throws IOException {

		server = new WebServer("127.0.0.1", 10011, "./server", "./server");
		String temp;
		boolean flag = false;
		server.start();
		assertEquals(true, server.getServerStatus());

		URL url1 = new URL("http://127.0.0.1:10011/cssinfolder.html");

		BufferedReader in = new BufferedReader(new InputStreamReader(
				url1.openStream()));

		try {
			while ((temp = in.readLine()) != null) {

				System.out.println(temp);

				if (temp.contains("Css in folder.")) {
					flag = true;
					break;
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertTrue(flag);
		server.stopServer();
		assertTrue(!server.getServerStatus());
		in.close();
	}

	// css in other folder and site in other folder

	@Test
	public void testComplex() throws IOException {

		server = new WebServer("127.0.0.1", 10011, "./server", "./server");
		String temp;
		boolean flag = false;
		server.start();
		assertEquals(true, server.getServerStatus());

		URL url1 = new URL("http://127.0.0.1:10011/site/complex.html");

		BufferedReader in = new BufferedReader(new InputStreamReader(
				url1.openStream()));

		try {
			while ((temp = in.readLine()) != null) {

				System.out.println(temp);

				if (temp.contains("Complex test.")) {
					flag = true;
					break;
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		assertTrue(flag);
		server.stopServer();
		assertTrue(!server.getServerStatus());
		in.close();
	}

	@After
	public void clean() throws IOException {
		server.stopServer();

	}

}
