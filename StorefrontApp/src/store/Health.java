//SalableProduct health product that is sold in the store
package store;

public class Health extends SalableProduct<String> {

	//default constructor, description to health item indicator
	public Health() {
		name = "Name";
		description = "Recover your strength.";
		price = -1;
		quantity = -1;
		trait = null;
	}
	//non-default constructor, generic health item description
	public Health(String productName, int cost, int stock) {
		name = productName;
			description = "Recover your strength.";
			price = cost;
			quantity = stock;
			trait = "edible";
	}
	//non-default constructor, can give specific health description
	public Health(String productName, String comment, int cost, int stock, String trait) {
		super(productName, comment, cost, stock, trait);
	}
	
}
