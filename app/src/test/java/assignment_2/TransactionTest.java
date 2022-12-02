package assignment_2;

import org.junit.jupiter.api.Test;

import assignment_2.model.Model;
import assignment_2.model.Product;
import assignment_2.model.ProductCategory;
import assignment_2.model.Transaction;
import assignment_2.model.TransactionHandler;

import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;

public class TransactionTest {

    Transaction transaction;
    Model model;
    
    @BeforeEach
    public void init() throws IOException{
        this.model = new Model();
        this.transaction = new Transaction(this.model);
    }

    /* Check that items sold  is initialized */
    @Test
    public void itemSoldInit(){
        assertNotNull(this.transaction.getItemsSold());
    }

    /* Check that items sold is empty */
    @Test
    public void itemsSoldEmpty(){
        assertEquals(0, this.transaction.getItemsSold().size());
    }

    /* Check that time is initialised */
    @Test
    public void timeInit(){
        LocalDateTime currentTime = LocalDateTime.now();
        assertTrue(ChronoUnit.SECONDS.between(currentTime, this.transaction.getTransactionTime()) < 2);
    }

    /* Check that money paid is initialised */
    @Test
    public void moneyPaidInit(){
        assertNotNull(this.transaction.getMoneyPaid());
    }

    /* Check that all money paid values are initialised to 0 */
    @Test
    public void moneyPaidValuesInit(){
        int expected = 0;

        for (int i = 0; i < this.transaction.getMoneyPaid().length; i++){
            assertEquals(expected, this.transaction.getMoneyPaid()[i]);
        }
    }

    /* Check that returned change is not null */
    @Test
    public void returnedChangeInit(){
        assertNotNull(this.transaction.getReturnedMoney());
    }

    /* Check that all returned change values are 0 */
    @Test
    public void returnedChangeEmpty(){
        int expected = 0;
        ArrayList<Integer> allValues = new ArrayList<Integer>(this.transaction.getReturnedMoney().values());
        for (int i = 0; i < allValues.size(); i++){
            assertEquals(expected, allValues.get(i));
        }
    }

    /* Test if size of shopping bag is accurate after adding product */
    @Test
    public void addingToShoppingbag(){
        Product prod = this.model.getProductHandler().getAllProducts().get(0);
        this.model.getTransactionHandler().addToShoppingBag(prod, 3);
        int expected = 3;
        Transaction newTrans = new Transaction(this.model);
        int actual = newTrans.getItemsSold().get(prod);
        assertEquals(expected, actual);
    }

    /* Check transaction cancellation reason can be set */
    @Test
    public void setCancellationReasonTest(){
        // the reason is initialized as an empty string
        assertTrue(this.transaction.getCancellationReason().equals(""));

        this.transaction.setCancellationReason("timeout");
        assertTrue(this.transaction.getCancellationReason().equals("timeout"));
    }

    /* Check there is no user in a new transaction */
    @Test
    public void getUserNameTest(){
        assertNull(this.transaction.getUsername());
    }

    /* Check we can set user in this transaction */
    @Test
    public void setUserNameTest(){
        this.transaction.setUsername("seller");
        assertTrue(this.transaction.getUsername().equals("seller"));
    }
}
