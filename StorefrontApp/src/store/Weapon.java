//SalableProduct weapon product that is sold in the store

package store;

public class Weapon extends SalableProduct<String> {
	
	//default constructor, description to weapon indicator
	public Weapon() {
		name = "Name";
		description = "Smite your enemies!";
		price = -1;
		quantity = -1;
		trait = null;
	}
	//non-default constructor for generic weapon
	public Weapon(String productName, int cost, int stock) {
		name = productName;
		description = "Smite your enemies!";
		price = cost;
		quantity = stock;
		trait = "iron";
	}
	//non-default constructor, can give specific weapon description
	public Weapon(String productName, String comment, int cost, int stock, String trait) {
		super(productName, comment, cost, stock, trait);
	}
	
}
