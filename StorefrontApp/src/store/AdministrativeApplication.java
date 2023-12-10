package store;

import java.io.IOException;
import java.util.Scanner;

//class that sends commands to Store Front App through the Administration Service
//to view or replenish the inventory of the store
//Communicates to Store Front App over the local network
public class AdministrativeApplication {
	private AdministrationService adminService;
	//display menu system
	public void displayMenu() {
		System.out.println("Please enter one of the following options:\n"
				+ "'U' to update inventory, followed by delimiter '|' and the new inventory in JSON format;\n"
				+ "'R' to return inventory of products");
	}
	
	public static void main(String[] args) throws IOException {
		
		//new admin app instance
		AdministrativeApplication adminApp = new AdministrativeApplication();
		adminApp.adminService = new AdministrationService();
		//Create a Client and connect to the remote Server on the specified IP address and port

		adminApp.adminService.start("127.0.0.1", 1234); //call start() on Client to connect to the server
	
		//scanner for AdminApp entry
		Scanner scnr = new Scanner(System.in); //declare scanner
		
		boolean exit = false;
		do {
			adminApp.displayMenu();
			String adminInput = scnr.nextLine();
				
			//execute if entry is 'U', the first letter in the string
			if(adminInput.startsWith("U")) {
				//send full command to ServerThread
				adminApp.adminService.sendMessage(adminInput);
				//System.out.println("new Inventory sent to ServerThread");
			} else if ("R".equals(adminInput)) {
					//if entry is "R", send to serverThread
					adminApp.adminService.sendMessage(adminInput);
			
			} else {
					//tell user invalid option
					System.out.println("Please enter a valid option.");	
			}
		} while(!exit); //create bool that flips upon exit key
		
		//cleanup after loop
		adminApp.adminService.cleanup();
		scnr.close();

	}

}
