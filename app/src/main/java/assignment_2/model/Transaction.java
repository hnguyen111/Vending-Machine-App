package assignment_2.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

/** Keeps track of any completed or cancelled transactions **/
public class Transaction {
    private Model model;
    private LocalDateTime transactionTime;
    private HashMap<Product, Integer> itemsSold;
    private int[] moneyPaid = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    private HashMap<Double, Integer> returnedChange = new HashMap<Double, Integer>();
    private String cancellationReason;
    private String username;

    public Transaction(Model model){
        this.model = model;
        this.init();
    }

    /** Initialises the values for the transaction, such as time/date and shopping bag **/
    public void init(){
        this.transactionTime = LocalDateTime.now();
        this.cancellationReason = "";

        // Retrieve number of coins/notes paid
        for (int i = 0; i < this.model.getTransactionHandler().getCurrentCashInserted().length; i++){
            moneyPaid[i] = this.model.getTransactionHandler().getCurrentCashInserted()[i];
        }       

        // Add current shopping bag to items sold
        this.itemsSold = new HashMap<Product, Integer>();
        ArrayList<Product> productKeys = new ArrayList<>(this.model.getTransactionHandler().getShoppingBag().keySet());
        for (int i = 0; i < this.model.getTransactionHandler().getShoppingBag().size(); i++){
            this.itemsSold.put(productKeys.get(i), this.model.getTransactionHandler().getShoppingBag().get(productKeys.get(i)));
        }

        // Add current returned change
        this.returnedChange = new HashMap<Double, Integer>();
        ArrayList<Double> doubleKeys = new ArrayList<>(this.model.getChangeCalculator().getChange().keySet());
        for (int i = 0; i < this.model.getChangeCalculator().getChange().size(); i++){
            this.returnedChange.put(doubleKeys.get(i), this.model.getChangeCalculator().getChange().get(doubleKeys.get(i)));
        }
    }

    /** Returns time of transaction **/
    public LocalDateTime getTransactionTime(){
        return this.transactionTime;
    }

    /** Returns hashmap of items sold: Product is the product sold, Integer is the quantity **/
    public HashMap<Product, Integer> getItemsSold(){
        return this.itemsSold;
    }

    /** Returns list of notes and coins paid **/
    public int[] getMoneyPaid(){
        return this.moneyPaid;
    }

    /** Returns a hashmap of change returned to customer: Double is coin/note, Integer is quantity **/
    public HashMap<Double, Integer> getReturnedMoney(){
        return this.returnedChange;
    }

    /** Returns string containing cancellation reason **/
    public String getCancellationReason(){
        return this.cancellationReason;
    }

    /** Sets the cancellation reason **/
    public void setCancellationReason(String reason){
        if (reason.equals("user cancelled") && !this.model.getTransactionHandler().changeIsAvailable()){
            this.cancellationReason = "change not available";
        } else {
            this.cancellationReason = reason;
        }
    }

    /** Returns username of user who completed transaction **/
    public String getUsername(){
        return this.username;
    }

    /** Sets username of user who completed transaction **/
    public void setUsername(String username){
        this.username = username;
    }
}
