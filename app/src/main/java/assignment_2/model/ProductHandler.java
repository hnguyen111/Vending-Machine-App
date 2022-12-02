package assignment_2.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/** Manages all products and responsible for generating products from text files **/
public class ProductHandler {
    private String productsFilePath;
    private String productsCategoryFilePath;
    private List<ProductCategory> allProductCategories;
    private List<Product> allProducts;

    public ProductHandler(){
        this.init();
    }

    /** Initialises all attributes for the Product object **/
    private void init(){
        this.allProductCategories = new ArrayList<ProductCategory>();
        this.allProducts = new ArrayList<Product>();
        this.productsFilePath = "app/src/main/resources/products.txt";
        this.productsCategoryFilePath = "app/src/main/resources/productCategories.txt";
    }

    /** Check that we do not already have this product in the system **/
    private boolean checkProductExists(String productCode){
        for (int i = 0; i < this.allProductCategories.size(); i++){
            ProductCategory category = this.allProductCategories.get(i);
            for (int j = 0; j < category.getAllProducts().size(); j++){
                if (category.getAllProducts().get(j).getProductCode().equals(productCode)){
                    return true;
                }
            } 
        }
        return false;
    }

    /** Check if product exists using product name and category **/
    public boolean checkProductNameExists(String productName, String proCat){
        for (int i = 0; i < this.allProductCategories.size(); i++){
            ProductCategory category = this.allProductCategories.get(i);
            for (int j = 0; j < category.getAllProducts().size(); j++){
                if (category.getAllProducts().get(j).getProductName().toLowerCase().equals(productName.toLowerCase()) && 
                    category.getCategoryName().toLowerCase().equals(proCat.toLowerCase())){
                    return true;
                }
            } 
        }
        return false;
    }

    /** Check that we do not already have this category in the system **/
    public ProductCategory checkCategoryExists(String name){
        for (int i = 0; i < this.allProductCategories.size(); i++){
            if (this.allProductCategories.get(i).getCategoryName()
            .toLowerCase().equals(name.toLowerCase())){
                return this.allProductCategories.get(i);
            }
        }
        return null;
    }

    /** Traverses through the products.txt file and creates Product objects **/
    public void retrieveProducts(String path) throws FileNotFoundException{
        File file = new File(path);
        Scanner sc = new Scanner(file);

        while(sc.hasNextLine()){
            String line[] = sc.nextLine().split(",");
            String productName = line[1];
            ProductCategory category = checkCategoryExists(line[0]);
            String code = line[2];
            double price = Double.parseDouble(line[3]);

            /* Check that category exists, but name does not */
            if (checkProductExists(code) || category == null){
                continue;
            } 

            /* Create the product with a capital letter */
            String capitalLetter = productName.substring(0, 1).toUpperCase();
            productName = capitalLetter + productName.substring(1);
            
            /* Adds category to list if not already there */
            Product newProduct = new Product(productName, category, code, price);
            category.addProduct(newProduct);
            this.allProducts.add(newProduct);
        }
        sc.close();
    }

    /** Traverses through the productCategories.txt file and creates ProductCategory objects **/
    public void retrieveCategories(String path) throws FileNotFoundException{
        File file = new File(path);
        Scanner sc = new Scanner(file);

        while(sc.hasNextLine()){
            String line = sc.nextLine();

            /* Check that we do not already have this category in the system */
            if (this.checkCategoryExists(line) != null){
                continue;
            }

            /* Create the product category with a capital letter */
            String capitalLetter = line.substring(0, 1).toUpperCase();
            line = capitalLetter + line.substring(1);
            
            /* Adds category to list if not already there */
            ProductCategory newCategory = new ProductCategory(line);
            this.allProductCategories.add(newCategory);
        }

        sc.close();
    }

    /** Returns a list of all products in system **/
    public List<Product> getAllProducts(){
        return this.allProducts;
    }

    /** Returns a list of all product categories in system **/
    public List<ProductCategory> getAllProductCategories(){
        return this.allProductCategories;
    }
    
    /** Modifies product details **/
    public boolean modifyProductDetails(String productCode, String newProductCode, String productName,
                                        String categoryName, double price, int quantity){
        Product p = this.getProductByCode(productCode);
        
        if (p != null){
            if (!productName.equals("")){
                p.setName(productName);
            }

            if (!categoryName.equals("")){
                ProductCategory category = checkCategoryExists(categoryName);
                if (category != null){
                    category.addProduct(p);
                    p.getProductCategory().removeProduct(p);
                    p.setCategory(category);
                }   
                else {
                    return false;
                }        
            }

            p.setCode(newProductCode);

            if (price > 0){
                p.setPrice(price);
            }

            if (quantity >= 0){
                p.setQuantity(quantity);
            }

        } else {
            return false;
        }

        return true;       
    }

    /** Return a product given its code **/
    public Product getProductByCode(String code){
        for (int i = 0; i < this.getAllProducts().size(); i++){
            if (this.getAllProducts().get(i).getProductCode().equals(code)){
                return this.getAllProducts().get(i);
            }
        }
        return null;
    }
}
