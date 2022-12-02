package assignment_2;

import assignment_2.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ReportGeneratorTest {

    private ReportGenerator reportGenerator;
    private Model model;

    @BeforeEach
    public void init() throws IOException{
        this.model = new Model();
        this.reportGenerator = new ReportGenerator(this.model);

        // Complete a transaction
        Product prod = this.model.getProductHandler().getAllProducts().get(0);
        this.model.getTransactionHandler().addToShoppingBag(prod, 2);
    }

    /* Testing that function does not return null when there are transactions completed */
    @Test
    public void notNullTransaction(){
        this.model.getTransactionHandler().completeTransaction();
        assertNotNull(this.reportGenerator.transactions());
    }

    /* Testing to see if inserted cash shows up */
    @Test
    public void insertedCash(){
        for (int i = 0; i < this.model.getTransactionHandler().getCurrentCashInserted().length; i++){
            this.model.getTransactionHandler().incrementCurrentCash(i);
        }
        this.model.getTransactionHandler().completeTransaction();
        assertNotNull(this.reportGenerator.transactions());
    }

    /* Testing to see if returned change shows up */
    @Test
    public void insertedCashGettingChange(){
        this.model.getTransactionHandler().incrementCurrentCash(2);
        this.model.getChangeCalculator().setValue(5.00, 1);
        this.model.getTransactionHandler().completeTransaction();
        assertNotNull(this.reportGenerator.transactions());
    }

    /* See report of cancelled transactions*/
    @Test
    public void cancelledTransactions() throws IOException{
        this.model.getTransactionHandler().completeTransaction();
        this.model.getTransactionHandler().cancelTransaction("seller", "timeout");
        this.reportGenerator.ownerReports();
    }
}
