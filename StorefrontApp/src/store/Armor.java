//SalableProduct armor product that is sold in the store
package store;

public class Armor extends SalableProduct<String> {
	
	//default constructor, description to armor indicator
	public Armor() {
		name = "Name";
		description = "Survive any attack.";
		price = -1;
		quantity = -1;
		trait = null;
	}
	//non-default constructor, generic armor description
	public Armor(String productName, int cost, int stock) {
		name = productName;
		description = "Survive any attack.";
		price = cost;
		quantity = stock;
		trait = "chainmail";
	}
	//non-default constructor, can give specific armor description
	public Armor(String productName, String comment, int cost, int stock, String trait) {
		super(productName, comment, cost, stock, trait);
	}
}
