package test;

import java.io.IOException;
import java.net.UnknownHostException;

import server.WebServer;

public class StartServer {

	public static void main(String[] args) {
		WebServer server = null;
		try {
			server = new WebServer("127.0.0.1", 10008,
					"C:\\Users\\vll\\vlad\\scoala\\test",
					"C:\\Users\\vll\\vlad\\scoala\\test");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			server.start();
		} catch (Exception e1) {
			System.out.println("Can't start server. " + e1.getMessage());
			return;
		}
	}
}
