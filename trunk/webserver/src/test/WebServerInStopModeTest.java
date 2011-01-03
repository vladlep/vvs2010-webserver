package test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.UnknownHostException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import server.WebServer;

public class WebServerInStopModeTest {

	WebServer server;

	@Before
	public void init() {
		try {
			server = new WebServer("127.0.0.1", 11111, "./server", "./server");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testConstructor() {
		assertEquals(server.toString(),
				"127.0.0.1:11111 root: ./server ment : ./server");

	}

	@Test
	public void testServerStatus() {
		assertTrue(!server.getMaintananceStatus());
		server.setMentenance(true);
		assertTrue(server.getMaintananceStatus());
		assertTrue(server.getServerStatus());

	}

	@Test
	public void testPathRoot() {

		assertEquals(server.getPathRoot(), "./server");
		server.setPathRoot("./path");
		assertEquals(server.getPathRoot(), "./path");

	}

	@Test
	public void testPathMent() {
		assertEquals("./server", server.getPathMent());
		server.setPathMent("path");
		assertEquals("path", server.getPathMent());

	}

	@After
	public void clear() throws IOException {

		server.stopServer();

	}
}
