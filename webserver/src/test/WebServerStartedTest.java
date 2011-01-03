package test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import server.WebServer;

public class WebServerStartedTest {

	WebServer server = null;;

	@Before
	public void init() throws Exception{
		
			server = new WebServer("127.0.0.1", 10008, "./server", "./server");
		
		server.start();
	}

	@Test
	public void testStopServer() {

		assertEquals(true, server.getServerStatus());

	}

	@Test
	public void testEnterMaintanance()
	{
		server.setMentenance(true);
		assertEquals(true,server.getMaintananceStatus());
		assertEquals(true,server.getServerStatus());
	}
	@After
	public void clear() throws IOException {
		server.stopServer();
	}
}
