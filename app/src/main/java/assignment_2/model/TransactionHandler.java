package assignment_2.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/** Manages all of the system's transactions, even current transactions that have not yet been completed **/
public class TransactionHandler {
    private HashMap<Product, Integer> currentShoppingBag;
    private ArrayList<Transaction> allTransactions;
    private double currentTotal;
    private Model model;
    private int[] currentCashInserted = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    private boolean changeAvailable;

    public TransactionHandler(Model model){
        this.model = model;
        this.init();
    }

    /** Initialises change available, current shopping bag and list of transactions **/
    public void init(){
        this.changeAvailable = true;
        this.currentShoppingBag = new HashMap<Product, Integer>();
        this.currentTotal = 0;
        this.allTransactions = new ArrayList<Transaction>();
    }

    /** Adds a product with specified quantity of items to the shopping bag **/
    public void addToShoppingBag(Product product, int quantity){
        int indexProduct = this.indexOfProduct(product);

        /* Check that product doesn't already exist in hashmap */
        if (indexProduct != -1){
            int currentQ = this.currentShoppingBag.get(product);
            int quantityAdded = quantity;
            this.currentShoppingBag.put(product, Integer.valueOf(currentQ + quantityAdded));
        } else {
            this.currentShoppingBag.put(product, Integer.valueOf(quantity));
        }
        this.updateTotal();
    }

    /** Traverses through the shopping cart and adds to current total **/
    public void updateTotal(){
        this.currentTotal = 0;
        ArrayList<Product> productKeys = new ArrayList<Product>(this.currentShoppingBag.keySet());
        for(int i = 0; i < productKeys.size(); i++){
            if (productKeys.get(i) != null){
                this.currentTotal += productKeys.get(i).getPrice() * this.currentShoppingBag.get(productKeys.get(i));
            }
        }
    }

    /** Finds the index of a product in the hashmap if it exists **/
    public int indexOfProduct(Product product){
        ArrayList<Product> keys = new ArrayList<Product>(this.currentShoppingBag.keySet());
        for(int i = 0; i < keys.size(); i++){
            if (keys != null && keys.get(i) != null && keys.get(i).equals(product)){
                return i;
            }
        }
        return -1;
    }

    /** Returns shopping bag **/
    public HashMap<Product, Integer> getShoppingBag(){
        return this.currentShoppingBag;
    }

    /** Resets shopping bag after a cancelled/completed transaction **/
    public void resetShoppingBag(){
        this.currentShoppingBag.clear();

        /* Remove temporary items put on hold by user */
        for (int i = 0; i < this.model.getProductHandler().getAllProducts().size(); i++){
            this.model.getProductHandler().getAllProducts().get(i).resetTempQuantity();
        }
    }

    /** Increases number of sold items and reduces quantity available **/
    public void completeTransaction(){

        Transaction newTransaction = new Transaction(this.model);
        this.allTransactions.add(newTransaction);

        System.out.println(newTransaction.getItemsSold().size());

        for (int i = 0; i < this.currentShoppingBag.size(); i++){
            for (int j = 0; j < this.model.getProductHandler().getAllProducts().size(); j++){
                ArrayList<Product> productKeys = new ArrayList<Product>(this.currentShoppingBag.keySet());
                ArrayList<Integer> values = new ArrayList<Integer>(this.currentShoppingBag.values());

                // If the products match, increase quantity
                if (productKeys.get(i).equals(this.model.getProductHandler().getAllProducts().get(j))){
                    this.model.getProductHandler().getAllProducts().get(j).increaseAmountSold(values.get(i));
                    this.model.getProductHandler().getAllProducts().get(j).reduceQuantity(values.get(i));
                }
            }
        }

    }

    /** Cancels a transaction **/
    public void cancelTransaction(String username, String reason){
        Transaction newTransaction = new Transaction(this.model);
        newTransaction.setCancellationReason(reason);
        newTransaction.setUsername(username);
        this.allTransactions.add(newTransaction);
    }

    /** Returns current total **/
    public double getTotal(){
        return this.currentTotal;
    }

    /** When transaction is complete, increment number of coins/quantity inserted **/
    public void incrementNotesCoins(){
        for (int i = 0; i < this.currentCashInserted.length; i++){

            /* Check number in list, increment cash by this amount */
            int index = 0;
            while (index < this.currentCashInserted[i]){
                this.model.getCashHandler().incrementCash(i);
                index++;
            }
        }
    }

    /** Resets current cash inserted **/
    public void resetCurrentCashInserted(){
        this.currentCashInserted = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    }

    /** Increments cash inserted by 1 **/
    public void incrementCurrentCash(int index){
        this.currentCashInserted[index] += 1;
    }

    /** Returns list of inserted cash **/
    public int[] getCurrentCashInserted(){
        return this.currentCashInserted;
    }

    /** Returns list of all transaction **/
    public ArrayList<Transaction> getAllTransactions(){
        return this.allTransactions;
    }

    /** Returns whether or not any change is available **/
    public boolean changeIsAvailable(){
        return this.changeAvailable;
    }

    /** Set boolean value for change availability **/
    public void setChangeAvailability(boolean newBool){
        this.changeAvailable = newBool;
    }
}