package server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientServer extends Thread {

	private Socket clientSocket;
	private WebServer server;

	public ClientServer(Socket clientSoc, WebServer webServer) {
		clientSocket = clientSoc;
		server = webServer;
	}

	public void run() {
		System.out.println("New Communication Thread Started");

		try {
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),
					true);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					clientSocket.getInputStream()));
			BufferedReader fileReader = null;

			String inputLine;
			String path;
			path = server.getPathRoot();
			System.out.println(path);
			File asckedFile = new File(path + "/ex.html");
			System.out.println(asckedFile.exists());
			if (!asckedFile.exists())
				asckedFile = new File(path + "/index.html");
			if(server.getMaintananceStatus())
				asckedFile = new File(path + "/mentenance.html");
			
			fileReader = new BufferedReader(new FileReader(asckedFile));
			while ((inputLine = fileReader.readLine()) != null) {

				System.out.println(inputLine);
				out.println(inputLine);
			}

			while ((inputLine = in.readLine()) != null) {

				System.out.println("Server: " + WebServer.i + inputLine);
				out.println(inputLine);

				if (inputLine.trim().equals(""))
					break;
			}

			out.close();
			in.close();
			fileReader.close();
			clientSocket.close();
		} catch (IOException e) {
			System.err.println("Problem with Communication Server");
			System.exit(1);
		}
	}
}
