package assignment_2.model;
import java.util.Map;
import java.util.HashMap;

/** Class containing functions responsible for calculating change available and resetting change **/
public class ChangeCalculator {
    int changeNeeded;
    HashMap<Double, Integer> change;
    Cash cash;

    /** Initialise the hashmap containing all different change amounts **/
    public ChangeCalculator(Cash cash) {
        this.changeNeeded = 0;

        this.change = new HashMap<Double, Integer>();
        change.put(100.0, 0);
        change.put(50.0, 0);
        change.put(20.0, 0);
        change.put(10.0, 0);
        change.put(5.0, 0);
        change.put(2.0, 0);
        change.put(1.0, 0);
        change.put(0.5, 0);
        change.put(0.2, 0);
        change.put(0.1, 0);
        change.put(0.05, 0);

        this.cash = cash;
    }

    /** Reset all change amounts to 0 **/
    public void resetChange() {
        for(double change: this.change.keySet()) {
            this.change.put(change, 0);
        }
    }

    /** Return the hashmap containing the change **/
    public HashMap<Double, Integer> getChange() {
        return this.change;
    }

    /** Figure out what change will be optimal, returning the highest change first **/
    public HashMap<Double, Integer> outputChange(double totalDue, double amountPaid) {
        this.changeNeeded = (int)Math.round(amountPaid * 100) - (int)Math.round(totalDue * 100);

        while (this.changeNeeded != 0.0) {
            if (this.changeNeeded >= 10000 && this.cash.getQuantity()[0] > 0) {
                this.change.put(100.00, this.change.get(100.0) + 1);
                this.changeNeeded -= 10000;
                this.cash.decrementCash(0);
            }
            else if (this.changeNeeded >= 5000 && this.cash.getQuantity()[1] > 0) {
                this.change.put(50.0, this.change.get(50.0) + 1);
                this.changeNeeded -= 5000;
                this.cash.decrementCash(1);
            }
            else if (this.changeNeeded >= 2000 && this.cash.getQuantity()[2] > 0) {
                this.change.put(20.0, this.change.get(20.0) + 1);
                this.changeNeeded -= 2000;
                this.cash.decrementCash(2);
            }
            else if (this.changeNeeded >= 1000 && this.cash.getQuantity()[3] > 0) {
                this.change.put(10.0, this.change.get(10.0) + 1);
                this.changeNeeded -= 1000;
                this.cash.decrementCash(3);
            }
            else if (this.changeNeeded >= 500 && this.cash.getQuantity()[4] > 0) {
                this.change.put(5.0, this.change.get(5.0) + 1);
                this.changeNeeded -= 500;
                this.cash.decrementCash(4);
            }
            else if (this.changeNeeded >= 200 && this.cash.getQuantity()[5] > 0) {
                this.change.put(2.0, this.change.get(2.0) + 1);
                this.changeNeeded -= 200;
                this.cash.decrementCash(5);
            }
            else if (this.changeNeeded >= 100 && this.cash.getQuantity()[6] > 0) {
                this.change.put(1.0, this.change.get(1.0) + 1);
                this.changeNeeded -= 100;
                this.cash.decrementCash(6);
            }
            else if (this.changeNeeded >= 50 && this.cash.getQuantity()[7] > 0) {
                this.change.put(0.5, this.change.get(0.5) + 1);
                this.changeNeeded -= 50;
                this.cash.decrementCash(7);
            }
            else if (this.changeNeeded >= 20 && this.cash.getQuantity()[8] > 0) {
                this.change.put(0.2, this.change.get(0.2) + 1);
                this.changeNeeded -= 20;
                this.cash.decrementCash(8);
            }
            else if (this.changeNeeded >= 10 && this.cash.getQuantity()[9] > 0) {
                this.change.put(0.1, this.change.get(0.1) + 1);
                this.changeNeeded -= 10;
                this.cash.decrementCash(9);
            }
            else if (this.changeNeeded >= 5 && this.cash.getQuantity()[10] > 0) {
                this.change.put(0.05, this.change.get(0.05) + 1);
                this.changeNeeded -= 5;
                this.cash.decrementCash(10);
            }

            else {
                break;
            }
        }

        return this.change;
    }

    /** Get the amount of change required to return **/
    public int getChangeNeeded() {
        return this.changeNeeded;
    }

    /** Set the value of the change **/
    public void setValue(Double key, Integer value){
        change.put(key, value);
    }
}
