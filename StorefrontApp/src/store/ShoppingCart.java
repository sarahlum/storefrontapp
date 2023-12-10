//cart class to hold products while user is shopping
package store;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ShoppingCart implements FileService {
	
	//declare inventory arraylist to hold products; all quantities start at 0
	private ArrayList<SalableProduct<?>> cart = new ArrayList<>();
		
	//method to initialize cart arraylist based on json file
	public void setJsonCart(String filename) throws IOException  {
			//create new mapper object
			ObjectMapper mapper = new ObjectMapper();
			//declare type reference to generic SalableProduct array
			TypeReference<ArrayList<SalableProduct<?>>> typeRef = new TypeReference<ArrayList<SalableProduct<?>>>() {};
			//set inventory equal to data in json file
			cart = mapper.readValue(new File(filename), typeRef);
				
			//set all quantities to 0
			for(SalableProduct<?> item : cart) {
				item.setQuantity(0);
			}
	}
	
	public void viewCart() {
	//Print all items, quantities, and prices in cart
	//iterate through arraylist and print name, quantity and price if their quantity is more than 0
		System.out.println(); //for whitespace
		System.out.println("Your Cart:");
		System.out.println("Item" + "         " + "Quantity" + "     " + "Total Price"); //categories of each column
		for(SalableProduct<?> product : cart) {
			if(product.getQuantity() > 0) {
				System.out.print(product.getName()); //print name
				//for spacing
				for(int a = 0; a <= (12 - (product.getName().length())); a++) { //add a space for every letter in item's name less than 15 for column alignment
					System.out.print(" ");
				}
				System.out.print(product.getQuantity()); //print quantity	
				//spacing between quantity and price
				if(product.getPrice() < 10) { //if quantity < two digits, print normal space for column alignment
					System.out.print("             ");
				} else { //otherwise print one less space
					System.out.print("            ");
				}
				System.out.print(product.getPrice()*product.getQuantity()); //print total price
				System.out.println();
			}
		}
	}
	//method to get cart items, returns the arraylist
	public ArrayList<SalableProduct<?>> getCart() {
		//return array of cart
		return cart;
	}
	
	//add item to cart
	//for matching item name, increase the quantity of that item in cart 
	public void addToCart(String name) {
		//iterate through array with getName to see if it matches the name parameter
			for(SalableProduct<?> product : cart) {
				if((name.toLowerCase()).equals(product.getName())) { //if name matches the index getName, INCREASE quantity by 1
					product.setQuantity((product.getQuantity()) + 1); //adding to current quantity
				}
			}
	}
	
	//remove item from cart
	//for matching item name, decrease quantity of that item in cart by 1
	public void removeFromCart(String name) {
		//iterate through array with getName to see if it matches the name parameter
		for(SalableProduct<?> product : cart) {
			if((name.toLowerCase()).equals(product.getName())) { //if name matches the index getName, decrease quantity by 1
				product.setQuantity((product.getQuantity()) - 1); //subtracting 1 from current quantity
			}
		}
	}
	
	//print message for total amt and items
	//clear shopping cart
	//previously checkOut()
	int totalCost; //to add up cart's total	
	public void checkOutAndEmptyCart() {
		System.out.println(); // whitespace for console readability
			//print all items, quantities, and total price
			System.out.println("Checking out the following items:");
			System.out.println("Item" + "         " + "Quantity" + "     " + "Total Price"); //categories of each column
			for(SalableProduct<?> product : cart) {
				if(product.getQuantity() > 0) {
					System.out.print(product.getName()); //print name
					//for spacing
					for(int a = 0; a <= (12 - (product.getName().length())); a++) { //add a space for every letter in item's name less than 15 for column alignment
						System.out.print(" ");
					}
					System.out.print(product.getQuantity()); //print quantity	
					//spacing between quantity and price
					if(product.getPrice() < 10) { //if quantity < two digits, print normal space for column alignment
						System.out.print("             ");
					} else { //otherwise print one less space
						System.out.print("            ");
					}
					System.out.print(product.getPrice()*product.getQuantity()); //print total price
					System.out.println();
				}
			}
			//get total cost
			//loop through array to add each index's price*quantity to total cost
			for(SalableProduct<?> product : cart) {
				totalCost += (product.getPrice()*product.getQuantity());
			}
			//print total cost
			System.out.println("The total cost is " + totalCost);
			System.out.println("Thanks for your purchase!");
			
			//clear cart by setting all quantities to 0
			for(SalableProduct<?> product : cart) {
				product.setQuantity(0);
			}
		}

	//methods from FileService interface
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
	public void setJsonInventory(String filename) throws IOException {
		// method for inventory only
		throw new UnsupportedOperationException("Invenory Method setJsonInventory not supported by ServerThread");
	}
			
} //end class
