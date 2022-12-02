package assignment_2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import assignment_2.model.User;
import assignment_2.model.Product;
import assignment_2.model.ProductCategory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserTest {
    private User owner;
    private User seller;
    private User cashier;

    @BeforeEach
    public void init(){
        this.owner = new User("owner", "owner123", "owner");
        this.seller = new User("seller", "seller123", "seller");
        this.cashier = new User("cashier", "cashier123", "cashier");
    }

    /* Check that owner's password is initialised */
    @Test
    void getPasswordOwnerNotNull(){
        assertNotNull(this.owner.getPassword());
    }

    /* Check that seller's password is initialised */
    @Test void getPasswordSellerAdminNotNull(){
        assertNotNull(this.seller.getPassword());
    }

    /* Check that cashier's password is initialised */
    @Test void getPasswordCashierAdminNotNull(){
        assertNotNull(this.cashier.getPassword());
    }

    /* Check that normal owner's password is correct */
    @Test void getCorrectOwnerPassword(){
        String expected = "owner123";
        String actual = this.owner.getPassword();
        assertEquals(expected, actual);
    }

    /* Check that seller's password is correct */
    @Test void getCorrectSellerPassword(){
        String expected = "seller123";
        String actual = this.seller.getPassword();
        assertEquals(expected, actual);
    }

    /* Check that cashier's password is correct */
    @Test void getCorrectCashierPassword(){
        String expected = "cashier123";
        String actual = this.cashier.getPassword();
        assertEquals(expected, actual);
    }

    /* Check that user's last five products get returned when empty */
    @Test void getLastFiveEmpty(){
        int expected = 0;
        int actual = this.owner.getLastFiveProducts().size();
        assertEquals(expected, actual);
    }

    /* Check that user's last five product list can be updated*/
    @Test void addLastFiveProduct(){
        ProductCategory productCategory = new ProductCategory("catName");
        Product product1 = new Product("prodName1", productCategory, "0000", 3.5);
        this.cashier.addLastFiveProduct(product1);
        assertTrue(this.cashier.getLastFiveProducts().get(0).getProductCode().equals("0000"));

        Product product2 = new Product("prodName2", productCategory, "0001", 3.5);
        this.cashier.addLastFiveProduct(product2);
        Product product3 = new Product("prodName3", productCategory, "0002", 3.5);
        this.cashier.addLastFiveProduct(product3);
        Product product4 = new Product("prodName4", productCategory, "0003", 3.5);
        this.cashier.addLastFiveProduct(product4);
        Product product5 = new Product("prodName5", productCategory, "0004", 3.5);
        this.cashier.addLastFiveProduct(product5);
        Product product6 = new Product("prodName6", productCategory, "0005", 3.5);
        this.cashier.addLastFiveProduct(product6);

        assertTrue(this.cashier.getLastFiveProducts().get(0).getProductCode().equals("0005"));
        assertTrue(this.cashier.getLastFiveProducts().get(4).getProductCode().equals("0001"));

        // the product to be added exists in the list, the list should contain 5 distinct products
        this.cashier.addLastFiveProduct(product4);

        assertTrue(this.cashier.getLastFiveProducts().get(0).getProductCode().equals("0003"));
        assertTrue(this.cashier.getLastFiveProducts().get(4).getProductCode().equals("0001"));
    }

    /* Check that user type can be set correctly*/
    @Test void setUserTypeTest(){
        this.seller.setUserType("owner");
        assertTrue(this.seller.getUserType().equals("owner"));
    }
}
