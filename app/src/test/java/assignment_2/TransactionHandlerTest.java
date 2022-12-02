package assignment_2;

import org.junit.jupiter.api.Test;

import assignment_2.model.Model;
import assignment_2.model.Product;
import assignment_2.model.ProductCategory;
import assignment_2.model.TransactionHandler;
import assignment_2.model.Transaction;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Executable;

import org.junit.jupiter.api.BeforeEach;

public class TransactionHandlerTest {

    TransactionHandler transactionHandler;
    Model model;
    
    @BeforeEach
    public void init() throws IOException{
        this.model = new Model();
        this.transactionHandler = new TransactionHandler(this.model);
    }

    /* Check that shopping bag is initialized */
    @Test
    public void shoppingBagInit(){
        assertNotNull(this.transactionHandler.getShoppingBag());
    }

    /* Check that shopping bag is empty */
    @Test
    public void shoppingBagInitEmpty(){
        assertEquals(0, this.transactionHandler.getShoppingBag().size());
    }

    /* Check that you can add to shopping bag and the bag is empty after reseting it*/
    @Test
    public void shoppingBagAddSize(){
        ProductCategory productCategory = new ProductCategory("cat");
        Product product = new Product("prodName", productCategory, "0000", 3.5);
        this.transactionHandler.addToShoppingBag(product, 5);
        assertEquals(1, this.transactionHandler.getShoppingBag().size());

        this.transactionHandler.resetShoppingBag();
        assertEquals(0, this.transactionHandler.getShoppingBag().size());
    }

    /* Check that adding a product twice increases the quantity in shopping bag */
    @Test
    public void sizeAddProductTwice(){
        ProductCategory productCategory = new ProductCategory("cat");
        Product product = new Product("prodName", productCategory, "0000", 3.5);
        this.transactionHandler.addToShoppingBag(product, 2);
        this.transactionHandler.addToShoppingBag(product, 2);
        assertEquals(1, this.transactionHandler.getShoppingBag().size());
    }

    /* Check that adding a product twice increases the quantity in shopping bag */
    @Test
    public void quantityAddProductTwice(){
        ProductCategory productCategory = new ProductCategory("cat");
        Product product = new Product("prodName", productCategory, "0000", 3.5);
        this.transactionHandler.addToShoppingBag(product, 2);
        this.transactionHandler.addToShoppingBag(product, 2);
        assertEquals(4, this.transactionHandler.getShoppingBag().get(product).intValue());
    }

    /* Check that we get the current total */
    @Test
    public void checkCurrentTotal(){
        double expected = 0;
        double actual = this.transactionHandler.getTotal();
        assertEquals(expected, actual);
    }

    /* Check if incrementing cash inserted works */
    @Test
    public void incrementCashInserted(){
        this.transactionHandler.incrementCurrentCash(0);
        this.transactionHandler.incrementNotesCoins();
        int expected = 6;
        int actual = this.model.getCashHandler().getQuantity()[0];
        assertEquals(expected, actual);
    }

    /* Check if it's possible to reset current cash inserted */
    @Test
    public void resetCash(){
        for (int i = 0; i < this.transactionHandler.getCurrentCashInserted().length; i++){
            this.transactionHandler.incrementCurrentCash(i);
        }
        int expected = 0;
        this.transactionHandler.resetCurrentCashInserted();
        for (int i = 0; i < this.transactionHandler.getCurrentCashInserted().length; i++){
            assertEquals(expected, this.transactionHandler.getCurrentCashInserted()[i]);
        }
    }

    /* Check a transaction can be cancelled*/
    @Test
    public void cancelTransaction(){
        this.transactionHandler.cancelTransaction("seller", "timeout");
        Transaction t = this.transactionHandler.getAllTransactions().
                            get(this.transactionHandler.getAllTransactions().size()-1);
        assertTrue(t.getUsername().equals("seller"));
        assertTrue(t.getCancellationReason().equals("timeout"));
    }
}
