//Class of InventoryManager to manage the inventory
package store;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

public class InventoryManager implements FileService { 
	
	//declare inventory arraylist to hold products
	private ArrayList<SalableProduct<?>> inventory = new ArrayList<>();
	
	//method to initialize inventory array based on json file
	public void setJsonInventory(String filename) throws IOException  {
			//create new mapper object
			ObjectMapper mapper = new ObjectMapper();
			//declare type reference to generic SalableProduct array
			TypeReference<ArrayList<SalableProduct<?>>> typeRef = new TypeReference<ArrayList<SalableProduct<?>>>() {};
			//set inventory equal to data in json file
			inventory = mapper.readValue(new File(filename), typeRef);
	}
	
	//method to sort products by name and price in descending order
	public void sortProductsDescending() {
		Collections.sort(inventory);
	}
	
	//method to sort products by name and price in ascending order
	public void sortProductsAscending() {
		Collections.reverse(inventory);
	}
	
	//Display all products name, price, and description
	public void viewProducts() { 
		System.out.println();
		System.out.println("Store:");
		System.out.println("Item" + "         " + "Price" + "     " + "Description"); //titles of each column category
		
		for(SalableProduct<?> product : inventory) { //loop through arraylist and print each item's name, price, and description
			if(product.getQuantity() > 0) {//only display products with quantities above 0
				System.out.print(product.getName()); //print name
				//for spacing
				for(int a = 0; a <= (12 - (product.getName().length())); a++) { //add a space for every letter in item's name less than 15 for column alignment
					System.out.print(" ");
				}
				System.out.print(product.getPrice()); // print price
				if(product.getPrice() < 10) { //if price < two digits, print normal space for column alignment
					System.out.print("         ");
				} else { //otherwise print one less space
					System.out.print("        ");
				}
			
				System.out.print(product.getDescription() + ", " + product.getTrait()); //print description
				System.out.println();
			}
			
		}
		System.out.println(); //whitespace for readability
	}
	
	//updates the inventory for object being purchased
	// for use each time shoppingCart's addToCart is used
	//formerly purchaseProduct
	public void removeFromInventory(String name) {
		//iterate through array with getName to see if it matches the name parameter
			for(SalableProduct<?> product : inventory) {
				if((name.toLowerCase()).equals(product.getName())) { //if name matches the index getName, subtract one from quantity since its being purchased
					product.setQuantity(product.getQuantity() - 1);
				}
			}
	}
	
	//used to add item to inventory quantity since it's been removed from cart
	//previously returnProduct
	public void addToInventory(String name) {
		//iterate through array with getName to see if it matches the name parameter
			for(SalableProduct<?> product : inventory) {
				if((name.toLowerCase()).equals(product.getName())) { //if name matches the index getName, add one to quantity since it's removed from cart
					product.setQuantity(product.getQuantity() + 1);
				}
			}
	}
	
	//return product that matches name parameter
	public SalableProduct<?> getProduct(String name) {
		//iterate through array with getName to see if it matches the name parameter
		for(SalableProduct<?> product : inventory) {
			if((name.toLowerCase()).equals(product.getName())) { //if name matches the index getName, return the index
				return product;
			}
		}
		SalableProduct<String> defaultItem = new SalableProduct<String>("Item does not exist.");
		System.out.println("The Store does not have that product. Please check your spelling.");
		return defaultItem;
	}
	
	//Methods for Admin User to access inventory manager
	//method that returns the inventory as an array to be able to access objects by their index
	//previously getInventoryArray
	public ArrayList<SalableProduct<?>> getInventory() { 
		//returns the array of inventory
		return inventory;
	}
		
	//previously getInventory
	public void printInventory() { 
		System.out.println(); //for console readability
		System.out.println("Current inventory:");
		//loop through arraylist to print item and quantity
		for(SalableProduct<?> product : inventory) {
			System.out.println((product.getName()) + " quantity: " + (product.getQuantity()));
		}
		System.out.println(); //for console readability
	}
	//for Admin User to update inventory
	//method can be called with product name and new amount 
	public void updateInventory(String name, int newAmount) {
		//weigh name against all item names. if equal, set its quantity to newAmount
			for(SalableProduct<?> product : inventory) {
				if((name.toLowerCase()).equals(product.getName())) { //if name matches the index getName, return the index
					product.setQuantity(newAmount);
				}
			}
	}

	//other methods implemented from the FileService interface
	@Override
	public String getStringJsonInventory(String filename) throws IOException {
		// method for ServerThread only
		throw new UnsupportedOperationException("ServerThread method getStringJsonInventory not supported by InventoryManager");
	}

	@Override
	public void updateJsonFileInventory(String data, String filename) throws IOException {
		// method for ServerThread only
		throw new UnsupportedOperationException("ServerThread method updateJsonFileInventory not supported by InventoryManager");
	}

	@Override
	public void setJsonCart(String filename) throws IOException {
		// method for cart only
		throw new UnsupportedOperationException("Cart Method setJsonCart not supported by InventoryManager");
	}
	
} //end class
