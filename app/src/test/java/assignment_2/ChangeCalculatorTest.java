package assignment_2;

import assignment_2.model.ChangeCalculator;
import assignment_2.model.Cash;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ChangeCalculatorTest {

    private ChangeCalculator changeCalculator;
    private Cash cash;

    @BeforeEach
    public void init(){
        this.cash = new Cash();
        this.changeCalculator = new ChangeCalculator(this.cash);
    }

    /* check that no change is left after a successful transaction */
    @Test void getChangeNeededCorrect(){
        int expected = 0;
        this.changeCalculator.outputChange(50, 60);
        int actual = this.changeCalculator.getChangeNeeded();
        assertEquals(actual, expected);
    }

    /* Check $100 change is calculated correctly */
    @Test void check100CalculatedCorrectly(){
        int expected = 1;
        HashMap<Double, Integer> output = this.changeCalculator.outputChange(50, 150);
        int actual = output.get(100.0);
        assertEquals(actual, expected);
    }

    /* Check $50 change is calculated correctly */
    @Test void check50CalculatedCorrectly(){
        int expected = 1;
        HashMap<Double, Integer> output = this.changeCalculator.outputChange(50, 100);
        int actual = output.get(50.0);
        assertEquals(actual, expected);
    }

    /* Check $20 change is calculated correctly */
    @Test void check20CalculatedCorrectly(){
        int expected = 1;
        HashMap<Double, Integer> output = this.changeCalculator.outputChange(50, 70);
        int actual = output.get(20.0);
        assertEquals(actual, expected);
    }

    /* Check $10 change is calculated correctly */
    @Test void check10CalculatedCorrectly(){
        int expected = 1;
        HashMap<Double, Integer> output = this.changeCalculator.outputChange(50, 60);
        int actual = output.get(10.0);
        assertEquals(actual, expected);
    }

    /* Check $5 change is calculated correctly */
    @Test void check5CalculatedCorrectly(){
        int expected = 1;
        HashMap<Double, Integer> output = this.changeCalculator.outputChange(50, 55);
        int actual = output.get(5.0);
        assertEquals(actual, expected);
    }

    /* Check $2 change is calculated correctly */
    @Test void check2CalculatedCorrectly(){
        int expected = 1;
        HashMap<Double, Integer> output = this.changeCalculator.outputChange(50, 52);
        int actual = output.get(2.0);
        assertEquals(actual, expected);
    }

    /* Check $1 change is calculated correctly */
    @Test void check1CalculatedCorrectly(){
        int expected = 1;
        HashMap<Double, Integer> output = this.changeCalculator.outputChange(50, 51);
        int actual = output.get(1.0);
        assertEquals(actual, expected);
    }

    /* Check 50c change is calculated correctly */
    @Test void check50cCalculatedCorrectly(){
        int expected = 1;
        HashMap<Double, Integer> output = this.changeCalculator.outputChange(50, 50.5);
        int actual = output.get(0.5);
        assertEquals(actual, expected);
    }

    /* Check 20c change is calculated correctly */
    @Test void check20calculatedCorrectly(){
        int expected = 1;
        HashMap<Double, Integer> output = this.changeCalculator.outputChange(50, 50.2);
        int actual = output.get(0.2);
        assertEquals(actual, expected);
    }

    /* Check 10c change is calculated correctly */
    @Test void check10cCalculatedCorrectly(){
        int expected = 1;
        HashMap<Double, Integer> output = this.changeCalculator.outputChange(50, 50.1);
        int actual = output.get(0.1);
        assertEquals(actual, expected);
    }

    /* Check 5c change is calculated correctly */
    @Test void check5cCalculatedCorrectly(){
        int expected = 1;
        HashMap<Double, Integer> output = this.changeCalculator.outputChange(50, 50.05);
        int actual = output.get(0.05);
        assertEquals(actual, expected);
    }

    /* Check reset change */
    @Test void resetChange() {
        int expected = 0;
        this.changeCalculator.resetChange();

        int hundred = this.changeCalculator.getChange().get(100.0);
        int fifty = this.changeCalculator.getChange().get(50.0);
        int twenty = this.changeCalculator.getChange().get(20.0);
        int ten = this.changeCalculator.getChange().get(10.0);
        int five = this.changeCalculator.getChange().get(5.0);
        int two = this.changeCalculator.getChange().get(2.0);
        int one = this.changeCalculator.getChange().get(1.0);
        int fiftyCents = this.changeCalculator.getChange().get(0.5);
        int twentyCents = this.changeCalculator.getChange().get(0.2);
        int tenCents = this.changeCalculator.getChange().get(0.1);
        int fiveCents = this.changeCalculator.getChange().get(0.05);

        assertEquals(hundred, expected);
        assertEquals(fifty, expected);
        assertEquals(twenty, expected);
        assertEquals(ten, expected);
        assertEquals(five, expected);
        assertEquals(two, expected);
        assertEquals(one, expected);
        assertEquals(fiftyCents, expected);
        assertEquals(twentyCents, expected);
        assertEquals(tenCents, expected);
        assertEquals(fiveCents, expected);


    }

    /* Check composite change is calculated correctly */
    @Test void checkCompositeChangeCalculatedCorrectly(){
        int expected = 1;
        HashMap<Double, Integer> output = this.changeCalculator.outputChange(50, 83.60);
        int twenty = output.get(20.0);
        int ten = output.get(10.0);
        int two = output.get(2.0);
        int one = output.get(1.0);
        int fiftyCents = output.get(0.5);
        int tenCents = output.get(0.1);
        assertEquals(twenty, expected);
        assertEquals(ten, expected);
        assertEquals(two, expected);
        assertEquals(one, expected);
        assertEquals(fiftyCents, expected);
        assertEquals(tenCents, expected);
    }

    /* Check it handles when an amount of change runs out */
    @Test void checkNoChangeLeft(){
        //Get rid of all five cent coins
        while (this.cash.getQuantity()[10] != 0) {
            this.cash.decrementCash(10);
        }

        int expectedFiveCentsUsed = 0;
        HashMap<Double, Integer> output = this.changeCalculator.outputChange(50, 50.05);
        int fiveCents = output.get(0.05);

        int expectedAmountLeft = 5;
        int actualAmountLeft = this.changeCalculator.getChangeNeeded();

        assertEquals(fiveCents, expectedFiveCentsUsed);
        assertEquals(actualAmountLeft, expectedAmountLeft);
    }
}
