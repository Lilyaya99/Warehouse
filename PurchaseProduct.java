package Warehouse;

public class PurchaseProduct {
    public static void main(String[] args) {
        StdIn.setFile(args[0]);
        StdOut.setFile(args[1]);

	// Use this file to test purchaseProduct

        Warehouse w = new Warehouse();
        int numQueries = StdIn.readInt();
        int counter = 0;
        while(counter != numQueries){ // while there are still lines in the file (numLines correlates to numProducts)
            String op = StdIn.readString();
            if(op.equals("add")){
                int day = StdIn.readInt();
                int id = StdIn.readInt();
                String name = StdIn.readString();
                int stock = StdIn.readInt();
                int demand = StdIn.readInt();
                w.addProduct(id, name, stock, day, demand);
            }
            if(op.equals("purchase")){
                int day = StdIn.readInt();
                int id = StdIn.readInt();
                int amountPurchase = StdIn.readInt();
                w.purchaseProduct(id, day, amountPurchase);
            }
            counter++;
        }

        StdOut.print(w);

    }
}
