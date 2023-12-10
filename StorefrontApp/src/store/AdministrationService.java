package store;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

//class that receives commands in Admin App and send them to Store Front ServerThread
public class AdministrationService {
	//methods to receive commands and send to ServerThread
	
	private Socket clientSocket; //to connect to the specified IP address and port
	private PrintWriter out; //for sending text over the socket to the Server
	private BufferedReader in; //for receiving text over the socket from the Server
	
	//establish connection with serverThread port
	//Connect to the remote Server on the specified IP address and Port
	public void start(String ip, int port) throws UnknownHostException, IOException {
		//connect to the Remote Server on the specified IP Address and Port
		clientSocket = new Socket(ip, port);
		
		//Create some input and output network buffers to communicate back and forth with the Server
		out = new PrintWriter(clientSocket.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		
	}
	// send messages to ServerThread
	public void sendMessage(String msg) throws IOException{
		//Send/Print a Message to Server with a terminating line feed
		out.println(msg);
		
		String response = in.readLine();
		//Return the full response from the Server
		System.out.println(response);
		
	}
	
	//cleanup logic to close all the network connections
	public void cleanup() throws IOException {
		//close all input and output network buffers and sockets
		in.close();
		out.close();
		clientSocket.close();
	}
	

}
