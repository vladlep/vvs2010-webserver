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

	public void run() {

		serverStarted = true;
		try {
			System.out.println(ipAddress);
			System.out.println();
			serverSocket = new ServerSocket(port,10,InetAddress.getByName(ipAddress));
			
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
		}catch(UnknownHostException e)
		{
			ServerGUI.showMessage("Incorect ip address: " + ipAddress + ".");	
		}
		catch (IOException e) {
			System.err.println("Could not listen on port: " + port + ".");
			e.printStackTrace();
			ServerGUI.showMessage("Could not listen on port: " + port + ".");
			System.exit(1);
		} finally {
			try {
				if (serverSocket.isBound())
					serverSocket.close();
			} catch (IOException e) {
				System.err.println("Could not close port: " + port + ".");
				ServerGUI.showMessage("Could not listen on port: " + port + ".");
				e.printStackTrace();
			
			}
		}
	}

	public WebServer(String ipAddress, int port, String pathRoot, String pathMent) {
		this.port = port;
		this.pathRoot = pathRoot;
		this.pathMent = pathMent;
		this.ipAddress = ipAddress;
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