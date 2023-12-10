//StoreFront: runs user side of the store to go shopping
package store;
import java.util.Scanner;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;

public class StoreFront{
	private static InventoryManager inventoryManager = new InventoryManager();
	private static ShoppingCart shoppingCart = new ShoppingCart();
	
	//default constructor
	public StoreFront() {
		try {
			//set store and cart from json file
			inventoryManager.setJsonInventory("data.json");
			shoppingCart.setJsonCart("data.json");
		} catch (IOException e) {
			System.out.println("Error with inventory/cart initializing store.");
		}
	
		//create instance of a ServerThread
		ServerThread serverThread = new ServerThread();
		//start the thread
		serverThread.start();
		
		//start filewatcher thread to keep tabs on any admin updates to JSON file
		//it will automatically update inventory and cart when JSON file changes
	    Path directory = Paths.get(".");
	    Thread fileWatcherThread = new Thread(new FileWatcher(directory, inventoryManager, shoppingCart));
	    fileWatcherThread.start();
	    
	}
	public static void resetJsonInventoryAndCart() {
		try {
			//set store and cart from json file
			inventoryManager.setJsonInventory("data.json");
			shoppingCart.setJsonCart("data.json");
		} catch (IOException e) {
			System.out.println("Error with store inventory/cart resetting.");
		}	
	}
	
	public void welcome() { //welcomes user to Store Front and prints store image
		System.out.println("    _____________");
		System.out.println("   /             \\     *");
		System.out.println("  /---------------\\   * *");
		System.out.println("   |  ___   ___  |   * * *");
		System.out.println("   |  |  |  |__| |    * *");
		System.out.println("...|  |  |       |....| |...");
		System.out.println("Welcome to the Store Front!");
	}
	public void displayMenu() { //prints menu of 4 options to console
		System.out.println("Please enter one of the below keys for its corresponding option:");
		System.out.println("Enter '1' to view items for purchase.");
		System.out.println("Enter '2' to purchase an item.");
		System.out.println("Enter '3' to view your cart.");
		System.out.println("Enter '4' to exit the store.");
	}
	
	//option 1: view store items
	public void viewStore(Scanner scnr) { 
		inventoryManager.viewProducts(); //display items in inventory
		
		//receive input if preference to sort by ascending or descending
		System.out.println("If you would like to sort products in ascending order, please enter 'a'.");
		System.out.println("If you would like to sort products in descending order, please enter 'd'.");
		System.out.println("To return to the main menu, please enter any other key.");
		String sortChoice = scnr.next();
		
		//ascending choice
		if(sortChoice.toLowerCase().equals("a")) {
			System.out.println("Sorting products by name and price in ascending order.");
			inventoryManager.sortProductsDescending(); //sort items by name and price 
			inventoryManager.sortProductsAscending(); //sort items reversed by name and price 
			inventoryManager.viewProducts(); //display sorted items in inventory
		} else if(sortChoice.toLowerCase().equals("d")) {
		//descending choice
			System.out.println("Sorting products by name and price in descending order.");
			inventoryManager.sortProductsDescending(); //sort items by name and price 
			inventoryManager.viewProducts(); //display sorted items in inventory
		} 
		//else, nothing happens. return to main menu
		
	}
	
	//option 2: purchase an item
	public void purchaseProduct(Scanner scnr) { 
		boolean doneShopping = false;
		do {
			//loop through all items and make sure there's at least one thing for purchase
			inventoryManager.viewProducts(); //display products
			System.out.println("Input name of item you wish to purchase.");
			String itemName = scnr.next();
		
			//add to cart if item quantity is more than 0
			if((inventoryManager.getProduct(itemName)).getQuantity() > 0) {
				shoppingCart.addToCart(itemName);
				//remove one item from inventory so user can't add more to cart than is available
				inventoryManager.removeFromInventory(itemName); //update inventory for each item being purchased
				
				//option to keep shopping, check out, or return to main menu 
				System.out.println(itemName + " was added to your cart.");
				System.out.println("Would you like to keep shopping?\nEnter 'y' for yes.\nIf you would like to check out, enter 'c'.\nEnter any other character to return to main menu.");
				String checkout = scnr.next(); //user input
				//checkout option
				if(checkout.toLowerCase().equals("c")) {
					doneShopping = true; //exit loop and return to main menu when done
					//proceed to checkout
					shoppingCart.checkOutAndEmptyCart();
				} else if(!(checkout.toLowerCase().equals("y"))) {
					doneShopping = true; //if they don't want to keep shopping, exit loop
				}
			} else {
				//inform user that it's out of stock
				System.out.println("This item is out of stock. Please see our other items.");
			}
		} while (!(doneShopping)); //keep shopping until user wants to exit
	}
	
	//option 3: view cart, option to check out or remove item
	public void goToCart(Scanner scnr) {
		//view cart
		shoppingCart.viewCart();
		ArrayList<SalableProduct<?>> cart = shoppingCart.getCart(); //array of current cart items
		
		//check for items in cart 
		boolean itemsInCart = false;
		for(SalableProduct<?> product : cart) {
			if(product.getQuantity() > 0) {
				itemsInCart = true;
			}
		}
		//if no items in cart, exit
		if(!itemsInCart) {
			System.out.println("You have no items in your cart yet!");
		}
		
		if(itemsInCart) {
			//option to check out or remove items from cart
			System.out.println("If you would like to check out, enter 'c'.\nIf you would like to remove an item from your cart, enter 'r'. \nEnter any other key to return to the main menu.");
			String checkOut = scnr.next();
			if(checkOut.toLowerCase().equals("c")) {
				//proceed to checkout
				shoppingCart.checkOutAndEmptyCart();
			} else if (checkOut.toLowerCase().equals("r")) {
				//check if name/quantity matches an item in cart first
				boolean itemPresent = false;
				do {
					//get item to be removed
					System.out.println("Please enter the name of the item you would like to remove from your cart.");
					String name = scnr.next();
			
					//remove item from cart: match by name and verify quantity		
					for(SalableProduct<?> product : cart) {
						if((name.toLowerCase()).equals(product.getName())) { //if name matches the index getName
							//make sure item quantity is more than 0
							if(product.getQuantity() > 0){
								//remove from cart
								shoppingCart.removeFromCart(name);
								itemPresent = true;
								//and return product to inventory
								inventoryManager.addToInventory(name);
								System.out.println(name + " removed from cart.");
							} 
						} 
					}
					if(!itemPresent) { //tell user the item is not in their cart
						System.out.println("That item is not in your cart.");
					}			
				} while(!(itemPresent));
			}	
		}
	}
	
	public static void main(String[] args) {

		Scanner scnr = new Scanner(System.in); //declare scanner
		
		//declare new store, which initializes inventory and cart and starts ServerThread
		StoreFront store = new StoreFront();
		
		//Print welcome message
		store.welcome();
		
		//loop the main menu to display, take user input, and execute steps until they choose to exit
		boolean exit = false;
		do {
			store.displayMenu();
			String menuChoice = scnr.next();
			switch(menuChoice) {
				case "1": //view
						store.viewStore(scnr);
						
						break;
				case "2": //purchase
						store.purchaseProduct(scnr);
						break;
				case "3": //cart
						store.goToCart(scnr);
						break;
				case "4": //exit
						exit = true;
						System.out.println("Thanks for visiting. Come again soon!");
						break;
				default: System.out.println("Invalid entry. Please enter 1, 2, 3, or 4.");
						break;
			
			}
		} while(!exit); //create bool that flips upon exit key
		
		
	} //end main
		
} // end class
