package Warehouse;

/*
 *
 * This class implements a warehouse on a Hash Table like structure, 
 * where each entry of the table stores a priority queue. 
 * Due to your limited space, you are unable to simply rehash to get more space. 
 * However, you can use your priority queue structure to delete less popular items 
 * and keep the space constant.
 * 
 * @author Ishaan Ivaturi @Student in CS112 class(Humza Ahmed Naeem)
 */ 
public class Warehouse{
    private Sector[] sectors;
    
    // Initializes every sector to an empty sector
    public Warehouse(){
        sectors = new Sector[10];

        for (int i = 0; i < 10; i++) {
            sectors[i] = new Sector();
        }
    }
    
    /**
     * Provided method, code the parts to add their behavior
     * @param id The id of the item to add
     * @param name The name of the item to add
     * @param stock The stock of the item to add
     * @param day The day of the item to add
     * @param demand Initial demand of the item to add
     */
    public void addProduct(int id, String name, int stock, int day, int demand){
        evictIfNeeded(id);
        addToEnd(id, name, stock, day, demand);
        fixHeap(id);
    }

    /**
     * Add a new product to the end of the correct sector
     * Requires proper use of the .add() method in the Sector class
     * @param id The id of the item to add
     * @param name The name of the item to add
     * @param stock The stock of the item to add
     * @param day The day of the item to add
     * @param demand Initial demand of the item to add
     */
    private void addToEnd(int id, String name, int stock, int day, int demand){
        // IMPLEMENT THIS METHOD
        
        Product p = new Product(id, name, stock, day, demand);

        int sectorIndex = id % 10;

        p.setLastPurchaseDay(day);

        Sector currentSector = sectors[sectorIndex];

        currentSector.add(p);
        
    }

    /**
     * Fix the heap structure of the sector, assuming the item was already added
     * Requires proper use of the .swim() and .getSize() methods in the Sector class
     * @param id The id of the item which was added
     */
    private void fixHeap(int id){
        // IMPLEMENT THIS METHOD
        
        int sectorIndex = id % 10;

        Sector currentSector = sectors[sectorIndex];

        for(int i = 2; i <= currentSector.getSize(); i++){

            currentSector.swim(i);

        }
        
    }

    /**
     * Delete the least popular item in the correct sector, only if its size is 5 while maintaining heap
     * Requires proper use of the .swap(), .deleteLast(), and .sink() methods in the Sector class
     * @param id The id of the item which is about to be added
     */
    private void evictIfNeeded(int id){
       // IMPLEMENT THIS METHOD
        
        int sectorIndex = id % 10;

        Sector currentSector = sectors[sectorIndex];

        if(currentSector.getSize() == 5){

            currentSector.swap(1,5);

            currentSector.deleteLast();

            currentSector.sink(1);

        }

    }

    /**
     * Update the stock of some item by some amount
     * Requires proper use of the .getSize() and .get() methods in the Sector class
     * Requires proper use of the .updateStock() method in the Product class
     * @param id The id of the item to restock
     * @param amount The amount by which to update the stock
     */
    public void restockProduct(int id, int amount){
        // IMPLEMENT THIS METHOD

        int sectorIndex = id % 10;

        Sector currentSector = sectors[sectorIndex];

        for(int i = 1; i <= currentSector.getSize(); i++){

            if(currentSector.get(i) != null){

                if(currentSector.get(i).getId() == id){

                    currentSector.get(i).updateStock(amount);

                }

            }

        }
        
    }
    
    /**
     * Delete some arbitrary product while maintaining the heap structure in O(logn)
     * Requires proper use of the .getSize(), .get(), .swap(), .deleteLast(), .sink() and/or .swim() methods
     * Requires proper use of the .getId() method from the Product class
     * @param id The id of the product to delete
     */
    public void deleteProduct(int id){
        // IMPLEMENT THIS METHOD

        int sectorIndex = id % 10;

        Sector currentSector = sectors[sectorIndex];

        for(int i = 1; i <= currentSector.getSize(); i++){

            if(currentSector.get(i) != null){

                if(currentSector.get(i).getId() == id){

                    currentSector.swap(i, currentSector.getSize());

                    currentSector.deleteLast();

                    currentSector.sink(1);

                }

            }

        }

    }
    
    /**
     * Simulate a purchase order for some product
     * Requires proper use of the getSize(), sink(), get() methods in the Sector class
     * Requires proper use of the getId(), getStock(), setLastPurchaseDay(), updateStock(), updateDemand() methods
     * @param id The id of the purchased product
     * @param day The current day
     * @param amount The amount purchased
     */
    public void purchaseProduct(int id, int day, int amount){
        // IMPLEMENT THIS METHOD

        int sectorId = id % 10;

        Sector currentSector = sectors[sectorId];

        for(int i = 1; i <= currentSector.getSize(); i++){

            if(currentSector.get(i) != null){

                Product currentProduct = currentSector.get(i);

                if(amount > currentProduct.getStock()){

                    return;

                }

                if(currentProduct.getId() == id){

                    currentProduct.setLastPurchaseDay(day);

                    currentProduct.setStock(currentProduct.getStock() - amount);

                    currentProduct.updateDemand(amount);

                    currentSector.sink(i);

                }

            }

        }

    }
    
    /**
     * Construct a better scheme to add a product, where empty spaces are always filled
     * @param id The id of the item to add
     * @param name The name of the item to add
     * @param stock The stock of the item to add
     * @param day The day of the item to add
     * @param demand Initial demand of the item to add
     */
    public void betterAddProduct(int id, String name, int stock, int day, int demand){
        // IMPLEMENT THIS METHOD

        int sectorId = id % 10;

        Sector initialSector = sectors[sectorId];

        if((initialSector.get(5) == null)){

            addProduct(id, name, stock, day, demand);

        }else{

            int counter = sectorId + 1;

            if(sectorId == 9){

                counter = 0;

            }

            while(counter != sectorId && (sectors[counter].get(5) != null)){

                if(counter == 9){

                    counter = 0;

                }else{

                    counter++;

                }

            }

            if(counter == sectorId){

                addProduct(id, name, stock, day, demand);

            }

            if(sectors[counter].get(5) == null){

                Product p = new Product(id, name, stock, day, demand);

                p.setLastPurchaseDay(day);

                sectors[counter].add(p);

                fixHeap(id);

            }

        }

    }

    /*
     * Returns the string representation of the warehouse
     */
    public String toString(){

        String warehouseString = "[\n";

        for (int i = 0; i < 10; i++){

            warehouseString += "\t" + sectors[i].toString() + "\n";

        }
        
        return warehouseString + "]";

    }

    /*
     * Do not remove this method, it is used by Autolab
     */ 
    public Sector[] getSectors (){

        return sectors;

    }

}
