package server;

import gui.ServerGUI;

import java.net.*;
import java.io.*;

public class WebServer extends Thread {

	private boolean serverStarted = false;
	private boolean serverInMentenanceMode = false;

	private int port;
	private String pathRoot;
	private String pathMent;

	private ServerSocket serverSocket = null;
	private String ipAddress;

	public static int i = 0;

	@Override
	public void run() {

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
				e.printStackTrace();

			} else {
				System.out.println("Server stopped.");
			}
		} finally {
			try {
				if (serverSocket.isBound())
					serverSocket.close();
			} catch (IOException e) {
				System.err.println("Could not close port: " + port + ".");
				ServerGUI
						.showMessage("Could not listen on port: " + port + ".");
				e.printStackTrace();

			}
		}

	}

	public WebServer(String ipAddress, int port, String pathRoot,
			String pathMent) throws UnknownHostException, IOException {
		this.port = port;
		this.pathRoot = pathRoot;
		this.pathMent = pathMent;
		this.ipAddress = ipAddress;
		createSocket();
	}

	// public static synchronized void startServer(int port, String pathRoot,
	// String pathMent) {
	//
	// new WebServer(port, pathRoot, pathMent).start();
	//
	// }

	public synchronized void stopServer() throws IOException {
		serverStarted = false;
	
			serverSocket.close();
	
	}

	public synchronized void setMentenance(boolean flag) {
		serverInMentenanceMode = flag;
	}

	public synchronized boolean getMaintananceStatus() {
		return serverInMentenanceMode;
	}

	public synchronized boolean getServerStatus() {
		return serverStarted;
	}

	@Override
	public String toString() {

		return ipAddress + ":" + port + " root: " + pathRoot + " ment : "
				+ pathMent;
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

	private void createSocket() throws UnknownHostException, IOException {

		serverStarted = true;
		serverSocket = new ServerSocket(port, 10,
				InetAddress.getByName(ipAddress));

		System.out.println("Connection Socket Created");
	}
}