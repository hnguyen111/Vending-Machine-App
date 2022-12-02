package assignment_2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import assignment_2.model.Cash;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CashTest {

    /* Check that cash array is initialised correctly */
    @Test
    void initTest(){
        Cash c = new Cash();
        assertEquals(c.getQuantity()[0],5);
        assertEquals(c.getQuantity()[10],5);
        assertEquals(c.getQuantity().length,11);
    }

    /* Check that 5c quantity can be updated correctly */
    @Test void set5cQuantityTest(){
        Cash c = new Cash();
        c.set5cQuantity(17);
        assertEquals(c.getQuantity()[10],17);
    }

    /* Check that 10c quantity can be updated correctly */
    @Test void set10cQuantityTest(){
        Cash c = new Cash();
        c.set10cQuantity(17);
        assertEquals(c.getQuantity()[9],17);
    }

    /* Check that 20c quantity can be updated correctly */
    @Test void set20cQuantityTest(){
        Cash c = new Cash();
        c.set20cQuantity(17);
        assertEquals(c.getQuantity()[8],17);
    }

    /* Check that 50c quantity can be updated correctly */
    @Test void set50cQuantityTest(){
        Cash c = new Cash();
        c.set50cQuantity(17);
        assertEquals(c.getQuantity()[7],17);
    }

    /* Check that $1 quantity can be updated correctly */
    @Test void set1DollarcQuantityTest(){
        Cash c = new Cash();
        c.set1DollarQuantity(17);
        assertEquals(c.getQuantity()[6],17);
    }

    /* Check that $2 quantity can be updated correctly */
    @Test void set2DollarQuantityTest(){
        Cash c = new Cash();
        c.set2DollarQuantity(17);
        assertEquals(c.getQuantity()[5],17);
    }

    /* Check that $5 quantity can be updated correctly */
    @Test void set5DollarQuantityTest(){
        Cash c = new Cash();
        c.set5DollarQuantity(17);
        assertEquals(c.getQuantity()[4],17);
    }

    /* Check that $10 quantity can be updated correctly */
    @Test void set10DollarQuantityTest(){
        Cash c = new Cash();
        c.set10DollarQuantity(17);
        assertEquals(c.getQuantity()[3],17);
    }

    /* Check that $20 quantity can be updated correctly */
    @Test void set20DollarQuantityTest(){
        Cash c = new Cash();
        c.set20DollarQuantity(17);
        assertEquals(c.getQuantity()[2],17);
    }

    /* Check that $50 quantity can be updated correctly */
    @Test void set50DollarQuantityTest(){
        Cash c = new Cash();
        c.set50DollarQuantity(17);
        assertEquals(c.getQuantity()[1],17);
    }

    /* Check that $100 quantity can be updated correctly */
    @Test void set100DollarQuantityTest(){
        Cash c = new Cash();
        c.set100DollarQuantity(17);
        assertEquals(c.getQuantity()[0],17);
    }

    /* Check that we can increment cash quantity*/
    @Test void incrementCashTest(){
        Cash c = new Cash();
        int quantity = c.getQuantity()[0];
        c.incrementCash(0);
        assertEquals(quantity+1, c.getQuantity()[0]);
    }

    /* Check that we can decrement cash quantity */
    @Test void decrementCashTest(){
        Cash c = new Cash();
        int quantity = c.getQuantity()[0];
        c.decrementCash(0);
        assertEquals(quantity-1, c.getQuantity()[0]);
    }
}
