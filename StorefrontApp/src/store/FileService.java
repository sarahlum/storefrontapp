package store;

import java.io.IOException;


//interface to read and write JSON inventory data files
//encapsulates access to the JSON data files used to hold and maintain the state of the SalableProduct inventory
public interface FileService {

	//For ServerThread: method to return String of json data from given filename
	String getStringJsonInventory(String filename)  throws IOException;
	
	//For ServerThread: method to take string and overwrite the data.json file
	void updateJsonFileInventory(String data, String filename)  throws IOException;
	
	//For InventoryManager: method to initialize inventory arraylist based on json file
	void setJsonInventory(String filename)  throws IOException;
	
	//for shoppingCart: method to initialize cart arraylist based on json file
	public void setJsonCart(String filename) throws IOException;
	
	
}
