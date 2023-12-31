package Warehouse;

/*
 * Use this class to test to addProduct method.
 */
public class AddProduct {
    public static void main(String[] args) {
        StdIn.setFile(args[0]);
        StdOut.setFile(args[1]);

        // Use this file to test addProduct

        Warehouse w = new Warehouse(); // created a warehouse object
        int numProducts = StdIn.readInt();
        int counter = 0;
        while(counter != numProducts){ // while there are still lines in the file (numLines correlates to numProducts)
            int day = StdIn.readInt();
            int id = StdIn.readInt();
            String name = StdIn.readString();
            int stock = StdIn.readInt();
            int demand = StdIn.readInt();
            w.addProduct(id, name, stock, day, demand); // add a product with the above info to the warehouse
            counter++;
        }

        StdOut.print(w);

    }
}
