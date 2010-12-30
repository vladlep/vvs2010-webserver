package server;

import java.net.*;
import java.io.*;

public class WebServer extends Thread {
	
	public static boolean serverStarted;
	public final int port;
	public final String pathRoot;
	public final String pathMent;
	private ServerSocket serverSocket = null;
	public static int i=0;
	
	public void run() {
		
		serverStarted = true;
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("Connection Socket Created");
			try {
				while (serverStarted) {
					System.out.println("Waiting for Connection");
					Socket client = serverSocket.accept();
					if(serverStarted)
						new	ClientServer(client);
					i= i+10;
				}
			} catch (IOException e) {
				System.err.println("Accept failed.");
				System.exit(1);
			}
		} catch (IOException e) {
			System.err.println("Could not listen on port: "+port+".");
			System.exit(1);
		} finally {
			try {
				serverSocket.close();
			} catch (IOException e) {
				System.err.println("Could not close port: "+port+".");
				System.exit(1);
			}
		}
	}

	private WebServer(int port, String pathRoot, String pathMent) {
	this.port = port;
	this.pathRoot = pathRoot;
	this.pathMent = pathMent;
	
	}

	public static void startServer(int port, String pathRoot, String pathMent)
	{
		
		new WebServer(port, pathRoot, pathMent).start();
		
		
	}
	
	public static void stopServer()
	{
		serverStarted = false;
		
	}
	
	@Override
	public String toString() {
	
	return "port: "+port+" root: "+pathRoot+" ment : "+pathMent;
	}
}