package server;

import java.net.*;
import java.io.*;

import javax.swing.RootPaneContainer;

public class WebServer extends Thread {

	private boolean serverStarted = false;
	private boolean serverInMentenanceMode = false;

	private int port;
	private String pathRoot;
	private String pathMent;

	private ServerSocket serverSocket = null;
	public static int i = 0;

	public void run() {

		serverStarted = true;
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("Connection Socket Created");
			try {
				while (serverStarted) {
					System.out.println("Waiting for Connection");
					Socket client = serverSocket.accept();
					if (serverStarted)
						new ClientServer(client, this).start();
					i = i + 10;
				}
			} catch (IOException e) {
				if (serverStarted) {
					System.err.println("Accept failed.");
					throw new RuntimeException("Can't accept connections");
					// System.exit(1);
				} else {
					System.out.println("Server stopped.");
				}

			}
		} catch (IOException e) {
			System.err.println("Could not listen on port: " + port + ".");
			// throw new RuntimeException("Could not listen on port: " + port +
			// ".");
			e.printStackTrace();
			System.exit(1);
		} finally {
			try {
				if (serverSocket.isBound())
					serverSocket.close();
			} catch (IOException e) {
				System.err.println("Could not close port: " + port + ".");
				e.printStackTrace();
				// throw new RuntimeException("Could not close port: "+ port +
				// ".");
			}
		}
	}

	public WebServer(int port, String pathRoot, String pathMent) {
		this.port = port;
		this.pathRoot = pathRoot;
		this.pathMent = pathMent;

	}

	// public static synchronized void startServer(int port, String pathRoot,
	// String pathMent) {
	//
	// new WebServer(port, pathRoot, pathMent).start();
	//
	// }

	public synchronized void stopServer() {
		serverStarted = false;
		try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public synchronized void setMentenance(boolean flag) {
		serverInMentenanceMode = flag;
	}

	public synchronized boolean getMaintananceStatus() {
		return serverInMentenanceMode;
	}

	public String toString() {

		return "port: " + port + " root: " + pathRoot + " ment : " + pathMent;
	}

	public synchronized void setPathRoot(String path) {
		pathRoot = path;
	}

	public synchronized String getPathRoot() {
		return pathRoot;
	}

	public synchronized void setPathMent(String path) {
		pathMent = path;
	}

	public synchronized String getPathMent() {
		return pathMent;
	}
}