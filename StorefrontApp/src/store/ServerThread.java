package store;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


//class to receive and handle commands from AdministratorService, on StoreFront App's behalf
public class ServerThread extends Thread implements FileService{
	private ServerSocket serverSocket; 
	private Socket clientSocket; //for AdminService client connection
	private PrintWriter out;
	private BufferedReader in;
	
	//port permanently 1234
	public static final int PORT = 1234;
	
	@Override
	public void run() {
		try {
			//wait for a client connection
			//System.out.println("Store serverThread: Waiting for a AdministrationService connection...");
			serverSocket = new ServerSocket(PORT);
			clientSocket = serverSocket.accept(); //wait for a connection from the client AdministrationService
		
			//If you get here then a client connected to this server,
			// so create some input and output network buffers
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); //for receiving text over the socket from the client
			
			//Wait for Command (string that is terminated by a line feed character)
			String adminInput;
			while((adminInput = in.readLine()) != null) {
					
				//adminInput is the full input
					
				//if AdminService sends "U" as first letter
				if(adminInput.startsWith("U")) {
					if(adminInput.contains("|")) {
						//System.out.println("ServerThread: Got a message to update the inventory");		
						//take substring after "|" by finding index and substring it after that index
						int index = adminInput.indexOf('|');
						String newInventory = adminInput.substring(index + 1);
						out.println("Inventory will be updated to: " + newInventory); //inform AdministrationService what was received
						//update data.json with new inventory string
						updateJsonFileInventory(newInventory, "data.json"); 
					} else {
						out.println("You forgot the | and new inventory.");
					}
				
				} else if ("R".equals(adminInput)) {
					//if AdminService sends "R", get current inventory
					//System.out.println("ServerThread: Received message to return inventory");
					//variable to store String of json formatted inventory
					String jsonInventory;
					//gets inventory from inventoryManager
					jsonInventory = getStringJsonInventory("data.json"); //saves String of inventory in json format from data.json
					//System.out.println("Returning: " + jsonInventory);
					out.println(jsonInventory); //send message to Admin Service of string of inventory in json format
					
				} else {
					//if command message is not received, send instruction to client
					//let client know that message was received but caused no action
					//System.out.println("ServerThread: Got a message of: " + adminInput);
					out.println("No corresponding action. Please send valid command.");
				}		
			} 
		} catch (IOException e) {
			System.out.println("Exception running ServerThread.");
		}
		
		//close up everything 
		try {
	    	this.cleanup();
	    } catch (IOException e) {
	    	
	    }
	}
	
	//method to return String of json data from given filename
	//to be used when Administration Service gives "R" command to return the inventory as a String in JSON format
	public String getStringJsonInventory(String filename) throws IOException  {
		//new stringBuilder object to store data sent from Admin
		StringBuilder stringBuilder = new StringBuilder();
		//read all data and add it to stringBuilder
	    try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
	        String line;
	        while ((line = reader.readLine()) != null) {
	            stringBuilder.append(line);
	        }
	    }
	    //return full string of what admin sent
	    return stringBuilder.toString();
	} 
	
	//method to take string and overwrite the data.json file
	public void updateJsonFileInventory(String data, String filename) throws IOException {
		//create new fileWriter to write to given file
		FileWriter fileWriter = new FileWriter(filename);
		fileWriter.write(data); //write String to the file
		fileWriter.close(); //close fileWriter
	}	
		//cleanup method to close all network buffers and sockets
		public void cleanup() throws IOException {
			//Close all input and output network buffers and sockets
			in.close();
			out.close();
			clientSocket.close();
			serverSocket.close();
		}

		//interface methods not used in ServerThread
		@Override
		public void setJsonInventory(String filename) throws IOException {
			// method for inventory only
			 throw new UnsupportedOperationException("setJsonInventory not supported by ServerThread");
		}

		@Override
		public void setJsonCart(String filename) throws IOException {
			// method for cart only
			 throw new UnsupportedOperationException("setJsonCart not supported by ServerThread");
		}
	
}
