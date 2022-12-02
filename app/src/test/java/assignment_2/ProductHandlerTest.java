package assignment_2;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import assignment_2.model.Product;
import assignment_2.model.ProductCategory;
import assignment_2.model.ProductHandler;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.util.List;

public class ProductHandlerTest {

    ProductHandler handler;

    @BeforeEach
    public void init(){
        handler = new ProductHandler();
        try {
            handler.retrieveCategories("src/test/resources/productCategories.txt");
            handler.retrieveProducts("src/test/resources/products.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }     
    }

    /* Check if productCategories.txt is read correctly */
    @Test
    public void readProductCategoriesTextFileTest(){
        assertEquals(4, handler.getAllProductCategories().size());
    }

    /* Check if products.txt is read correctly */
    @Test
    public void readProductsTextFileTest(){
        assertEquals(16, handler.getAllProducts().size());
    }

    /* Check that product details can be modified correctly */
    @Test
    public void modifyProductDetailsTest(){

        List<Product> allProducts = handler.getAllProducts();
        Product p = null;
        for (int i = 0; i < allProducts.size(); i++){
            if (allProducts.get(i).getProductCode().equals("0001")){
                p = allProducts.get(i);
                break;
            }
        }

        // modify product name, price and quantity
        handler.modifyProductDetails("0001", "0001", "water", "", 3.2, 8);
        assertTrue(p.getProductCode().equals("0001"));
        assertTrue(p.getProductName().equals("water"));
        assertEquals(3.2, p.getPrice());
        assertEquals(8, p.getQuantityAvailable());
        assertTrue(p.getProductCategory().getCategoryName().equals("Drinks"));

        // product code does not exist
        assertFalse(handler.modifyProductDetails("0000", "0000", "", "", 1, 1));

        // modify category, price and quantity stay the same when they are invalid
        handler.modifyProductDetails("0002", "0002", "", "Chips", -1, -1);
        p = null;
        for (int i = 0; i < allProducts.size(); i++){
            if (allProducts.get(i).getProductCode().equals("0002")){
                p = allProducts.get(i);
                break;
            }
        }
        assertTrue(p.getProductCategory().getCategoryName().equals("Chips"));
        assertEquals(1.8, p.getPrice());
        assertEquals(7, p.getQuantityAvailable());
    }

    /* Check that product name exists based on product name and category */
    @Test
    public void checkProductNameTrue(){
        assertTrue(handler.checkProductNameExists("sprite", "drinks"));        

    }

    /* Check that product name does not exist based on product name and category */
    @Test
    public void checkProductNameFalse(){
        assertFalse(handler.checkProductNameExists("notExist", "drinks"));        

    }
}
