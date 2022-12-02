package assignment_2.model;

/** Cash handler which keeps track of notes and coins available in the application **/
public class Cash {
    private int[] cash;

    public Cash(){
        // $100, $50, $20, $10, $5, $2, $1, 50c, 20c, 10c, 5c
        this.cash = new int[]{5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5};
    }

    /** Returns an array of the availability for all coins and notes **/
    public int[] getQuantity(){
        return this.cash;
    }

    /** Increments quantity for the specified note/coin by 1 **/
    public void incrementCash(int i) {
        System.out.println("Incrementing index:" + i);
        this.cash[i] += 1;
        System.out.println("Current value:" + this.cash[i]);
    }

    /** Decrements quantity for the specified note/coin by 1 **/
    public void decrementCash(int i) {
        this.cash[i] -= 1;
    }

    /** Sets quantity of 5 cents **/
    public void set5cQuantity(int quantity){
        this.cash[10] = quantity;
    }

    /** Sets quantity of 10 cents **/
    public void set10cQuantity(int quantity){
        this.cash[9] = quantity;
    }

    /** Sets quantity of 20 cents **/
    public void set20cQuantity(int quantity){
        this.cash[8] = quantity;
    }

    /** Sets quantity of 50 cents **/
    public void set50cQuantity(int quantity){
        this.cash[7] = quantity;
    }

    /** Sets quantity of 1 dollar **/
    public void set1DollarQuantity(int quantity){
        this.cash[6] = quantity;
    }

    /** Sets quantity of 2 dollars **/
    public void set2DollarQuantity(int quantity){
        this.cash[5] = quantity;
    }

    /** Sets quantity of 5 dollar **/
    public void set5DollarQuantity(int quantity){
        this.cash[4] = quantity;
    }

    /** Sets quantity of 10 dollars **/
    public void set10DollarQuantity(int quantity){
        this.cash[3] = quantity;
    }

    /** Sets quantity of 20 dollars **/
    public void set20DollarQuantity(int quantity){
        this.cash[2] = quantity;
    }

    /** Sets quantity of 50 dollars **/
    public void set50DollarQuantity(int quantity){
        this.cash[1] = quantity;
    }

    /** Sets quantity of 100 dollars **/
    public void set100DollarQuantity(int quantity){
        this.cash[0] = quantity;
    }

}
