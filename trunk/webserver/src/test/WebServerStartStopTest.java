package test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import server.WebServer;

public class WebServerStartStopTest {

	WebServer server;

	@Before
	public void init() {
		server = new WebServer("127.0.0.1", 10008, "./server", "./server");
		server.start();
	}

	@Test
	public void testStopServer() {
		assertEquals(true, server.getServerStatus());
		server.stopServer();
		assertEquals(false, server.getServerStatus());
	}

	
}
