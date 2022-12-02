package assignment_2;

import org.junit.jupiter.api.Test;

import assignment_2.model.Product;
import assignment_2.model.ProductCategory;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.BeforeEach;

public class ProductCategoryTest {

    ProductCategory productCategory;

    @BeforeEach
    public void init(){
        this.productCategory = new ProductCategory("test");
    }

    /* Check that the category name is initialized */
    @Test
    public void nameInit(){
        assertEquals("test", this.productCategory.getCategoryName());
    }

    /* Check that list of products is initialized */
    @Test
    public void listProdInit(){
        assertNotNull(this.productCategory.getAllProducts());
    }

    /* Check that you can add a product to list (SIZE) */
    @Test
    public void sizeAddList(){
        Product product = new Product("prodName", this.productCategory, "0000", 3.5);
        this.productCategory.addProduct(product);
        assertEquals(1, this.productCategory.getAllProducts().size());
    }

    /* Check that you can add a product to list (SIZE) */
    @Test
    public void nameAddList(){
        Product product = new Product("prodName", this.productCategory, "0000", 3.5);
        this.productCategory.addProduct(product);
        assertEquals("prodName", this.productCategory.getAllProducts().get(0).getProductName());
    }
}
