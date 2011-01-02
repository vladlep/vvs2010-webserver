package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import server.WebServer;

public class WebServerInStopModeTest {

	WebServer server = null;
	@Before
	public void init()
	{
		server = new WebServer("127.0.0.1", 10008, "./server", "./server");
	}
	@Test
	public void testConstructor()
	{
		assertEquals(server.toString(),"127.0.0.1:10008 root: ./server ment : ./server");
		
		
	}
	
	@Test
	public void testServerStatus()
	{
		assert(!server.getMaintananceStatus());
		server.setMentenance(true);
		assert(server.getMaintananceStatus());
		assert(!server.getServerStatus());
	}
	
	@Test
	public void testPathRoot()
	{
	
		assertEquals(server.getPathRoot(), "./server");
		server.setPathRoot("./path");
		assertEquals(server.getPathRoot(),"./path");
	}
	
	@Test 
	public void testPathMent()
	{
	assertEquals("./server", server.getPathMent());
	server.setPathMent("path");
	assertEquals("path", server.getPathMent());
	}
	
}
