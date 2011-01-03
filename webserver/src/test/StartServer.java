package test;

import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import server.WebServer;
import gui.ServerGUI;

public class StartServer {

	public static void main(String[] args) {
		WebServer server = null; 
		try {
			server = new WebServer("127.0.0.1",10008,"C:\\sers\\vll\\vlad\\scoala\\test" ,
					"C:\\sers\\vll\\vlad\\scoala\\test");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try{
		server.start();
		 } catch (Exception e1) {
		 System.out.println(
		 "Can't start server. "+e1.getMessage());
		 return;
		 }
	}
}
