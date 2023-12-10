package store;

import java.io.IOException;
import java.nio.file.*;

//class to watch changes made to the data.json file and update inventory & cart when changes are made
public class FileWatcher implements Runnable {
    private final Path directory;
    private final InventoryManager inventoryManager;
    private final ShoppingCart shoppingCart;

    //nondefault constructor to be able to make changes to inventoryManager and shoppingCart
    public FileWatcher(Path directory, InventoryManager inventoryManager, ShoppingCart shoppingCart) {
        this.directory = directory;
        this.inventoryManager = inventoryManager;
        this.shoppingCart = shoppingCart;
    }

    //override run method
    @Override
    public void run() {
        try {
        	//create new watchservice
            WatchService watcher = FileSystems.getDefault().newWatchService();
            directory.register(watcher, StandardWatchEventKinds.ENTRY_MODIFY);

            //loop to keep watch of data.json for any changes
            while (true) {
                WatchKey key = watcher.take();
                for (WatchEvent<?> event : key.pollEvents()) {
                    Path changedFile = (Path) event.context();
                    //if change detected, update the inventory and cart
                    if (changedFile.getFileName().toString().equals("data.json")) {
                    	 
                    	// Reload the inventory and cart from the updated data.json file
                            inventoryManager.setJsonInventory("data.json");
                            shoppingCart.setJsonCart("data.json");

                    }
                }
                //reset key to watch for another file change
                key.reset();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
