package assignment_2.model;

import java.util.ArrayList;
import java.util.List;

/** Manages details for product categories such as category name and a list of all products **/
public class ProductCategory {
    private String categoryName;
    private List<Product> allProducts;

    public ProductCategory(String categoryName){
        this.categoryName = categoryName;
        this.allProducts = new ArrayList<Product>();
    }

    /** Returns category name **/
    public String getCategoryName(){
        return this.categoryName;
    }

    /** Adds a product to the category's list of products **/
    public void addProduct(Product product){
        this.allProducts.add(product);
    }

    /** Remove a product from the list of products **/
    public void removeProduct(Product product){
        this.allProducts.remove(product);
    }

    /** Returns a list of all products in category **/
    public List<Product> getAllProducts(){
        return this.allProducts;
    }
}
