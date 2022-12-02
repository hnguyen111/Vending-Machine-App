package assignment_2;

import org.junit.jupiter.api.Test;

import assignment_2.model.Product;
import assignment_2.model.ProductCategory;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.BeforeEach;

public class ProductTest {

    Product product;
    ProductCategory productCategory;

    @BeforeEach
    public void init(){
        this.productCategory = new ProductCategory("catName");
        this.product = new Product("prodName", this.productCategory, "0000", 3.5);
    }

    /* Check that product name is initialized */
    @Test
    public void nameInit(){
        assertEquals("prodName", this.product.getProductName());
    }

    /* Check that product category is initialized */
    @Test
    public void catInit(){
        assertEquals(this.productCategory, this.product.getProductCategory());
    }

    /* Check that quantity is initialized to 7 */
    @Test
    public void initQuantity(){
        assertEquals(7, this.product.getQuantityAvailable());
    }

    /* Check that capacity is initialized to 15 */
    @Test
    public void initCapacity(){
        assertEquals(15, this.product.getCapacity());
    }

    /* Check that you can reduce quantity is initialized to 15 */
    @Test
    public void reduceCap(){
        this.product.reduceQuantity(5);
        assertEquals(2, this.product.getQuantityAvailable());
    }

    /* Check that name can be updated correctly */
    @Test
    public void setNameTest(){
        this.product.setName("water");
        assertTrue(this.product.getProductName().equals("water"));
    }

    /* Check that product code can be updated correctly */
    @Test
    public void setCodeTest(){
        this.product.setCode("0001");
        assertTrue(this.product.getProductCode().equals("0001"));
    }

    /* Check that category can be updated correctly */
    @Test
    public void setCategoryTest(){
        this.product.setCategory(new ProductCategory("drink"));
        assertTrue(this.product.getProductCategory().getCategoryName().equals("drink"));
    }

    /* Check that quantity can be updated correctly */
    @Test
    public void setQuantityTest(){
        this.product.setQuantity(17);
        assertEquals(this.product.getQuantityAvailable(), 17);
    }

    /* Check that price can be updated correctly */
    @Test
    public void setPriceTest(){
        this.product.setPrice(5.0);
        assertEquals(this.product.getPrice(), 5.0);
    }

    /* Check that it's possible to increase temporary quantity being put on hold */
    @Test
    public void incTempQuan(){
        this.product.increaseTempQuantity();
        int expected = 1;
        int actual = this.product.getTempQuantity();
        assertEquals(expected, actual);
    }

    /* Check that it's possible to reduce temporary quantity being put on hold */
    @Test
    public void reduceTempQuan(){
        this.product.increaseTempQuantity();
        this.product.increaseTempQuantity();
        this.product.reduceTempQuantity();
        int expected = 1;
        int actual = this.product.getTempQuantity();
        assertEquals(expected, actual);
    }

    /* Check that it's possible to increase amount sold */
    @Test
    public void increaseSold(){
        this.product.increaseAmountSold(5);
        int expected = 5;
        int actual = this.product.getAmountSold();
        assertEquals(expected, actual);
    }

}
