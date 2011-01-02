package test;

import javax.swing.JOptionPane;

import server.WebServer;
import gui.ServerGUI;

public class StartServer {

	public static void main(String[] args) {
		WebServer server = new WebServer("127.0.0.1",10008,"C:\\sers\\vll\\vlad\\scoala\\test" ,
				"C:\\sers\\vll\\vlad\\scoala\\test");
		try{
		server.start();
		 } catch (Exception e1) {
		 System.out.println(
		 "Can't start server. "+e1.getMessage());
		 return;
		 }
	}
}
