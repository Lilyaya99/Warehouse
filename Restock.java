package Warehouse;

public class Restock {
    public static void main(String[] args) {
        StdIn.setFile(args[0]);
        StdOut.setFile(args[1]);

	// Uset his file to test restock

        Warehouse w = new Warehouse();

        int numProducts = StdIn.readInt();
        int counter = 0;
        while(counter != numProducts){ // while there are still lines in the file (numLines correlates to numProducts)
            String op = StdIn.readString();
            if(op.equals("add")){
                int day = StdIn.readInt();
                int id = StdIn.readInt();
                String name = StdIn.readString();
                int stock = StdIn.readInt();
                int demand = StdIn.readInt();
                w.addProduct(id, name, stock, day, demand);
            }
            if(op.equals("restock")){
                int id = StdIn.readInt();
                int amountToRestock = StdIn.readInt();
                w.restockProduct(id, amountToRestock);
            }
            counter++;
        }

        StdOut.print(w);

    }
}
