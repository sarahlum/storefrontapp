//Main product file; parent class for all products for the store
package store;

public class SalableProduct<T extends Comparable<T>> implements Comparable<SalableProduct<T>>{
	protected String name;
	protected String description;
	protected int price;
	protected int quantity;
	protected T trait;
	
	public SalableProduct() { //default constructor
		name = "Name";
		description = "Description";
		price = -1;
		quantity = -1;
		trait = null;
	}
	public SalableProduct(String defaultName) { //item filler for items that don't exist
		name = defaultName;
	}
	public SalableProduct(String productName, String describe, int cost, int stock, T trait) {
		name = productName;
		description = describe;
		price = cost;
		quantity = stock;
		this.trait = trait;
	}
	
	//methods
	public String getName() { //getter for name
		return name;
	}
	public void setName(String newName) { //setter for name
		name = newName;
	}
	public String getDescription() { //getter for description
		return description;
	}
	public void setDescription(String newDescription) { //setter for description
		description = newDescription;
	}
	public int getPrice() { //getter for price
		return price;
	}
	public void setPrice(int newPrice) { //setter for price
		price = newPrice;
	}
	public int getQuantity() { //getter for quantity
		return quantity;
	}
	public void setQuantity(int newQuantity) { //setter for quantity
		quantity = newQuantity;
	}
	public T getTrait() { //getter for trait
		return trait;
	}
	public void setTrait(T newTrait) { //setter for trait
		trait = newTrait;
	}
	
	//Comparable interface method
	//use to sort inventory/cart by name and price
	@Override
	public int compareTo(SalableProduct<T> o) {
		//needs to be based on name
		//ignore case by making names lowercase
		int value = (this.getName().toLowerCase()).compareTo((o.getName().toLowerCase()));
		
		//if same name, compare prices and return that value
		if(value == 0) {
			//compare by price, convert price to String
			return Integer.toString(this.getPrice()).compareTo(Integer.toString(o.getPrice())); 
		} else {
			//if different names, return value of comparing name
			return value;
		}
	}
	
} //end class
