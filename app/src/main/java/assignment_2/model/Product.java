package assignment_2.model;

/** Class responsible for keeping track of a product's details, such as name, code, current quantity
 * available and price.
**/
public class Product {
    private String productName;
    private ProductCategory productCategory;
    private int quantity;
    private int capacity;
    private String code;
    private double price;
    private int tempQuantityCart;
    private int amountSold;

    public Product(String productName, ProductCategory productCategory, String code, double price){
        this.productName = productName;
        this.productCategory = productCategory;
        this.quantity = 7;
        this.capacity = 15;
        this.code = code;
        this.price = price;
        this.tempQuantityCart = 0;
        this.amountSold = 0;
    }

    /** Returns product name **/
    public String getProductName(){
        return this.productName;
    }

    /** Returns product category **/
    public ProductCategory getProductCategory(){
        return this.productCategory;
    }

    /** Returns product code **/
    public String getProductCode(){
        return this.code;
    }

    /** Reduces quantity available of product by specified integer **/
    public void reduceQuantity(int q){
        this.quantity = this.quantity - q;
    }

    /** Returns quantity available **/
    public int getQuantityAvailable(){
        return this.quantity;
    }

    /** Returns capacity **/
    public int getCapacity(){
        return this.capacity;
    }

    /** Returns current price **/
    public double getPrice(){
        return this.price;
    }
    
    /** Sets the name of the product by specified name **/
    public void setName(String newName){
        this.productName = newName;
    }

    /** Sets the code of the product by specified code **/
    public void setCode(String newCode){
        this.code = newCode;
    }

    /** Sets the category of the product by specified category **/
    public void setCategory(ProductCategory newProductCategory){
        this.productCategory = newProductCategory;
    }

    /** Sets the quantity by specified integer **/
    public void setQuantity(int newQuantity){
        this.quantity = newQuantity;
    }

    /** Sets the price by specified double value **/
    public void setPrice(double newPrice){
        this.price = newPrice;
    }

    /** Reduces temporary quantity put on hold in a transaction **/
    public void reduceTempQuantity(){
        this.tempQuantityCart--;
    }

    /** Increases temporary quantity put on hold in a transaction **/
    public void increaseTempQuantity(){
        this.tempQuantityCart++;
    }

    /** Returns temporary quantity put on hold in transaction **/
    public int getTempQuantity(){
        return this.tempQuantityCart;
    }

    /** Reduces temporary quantity put on hold **/
    public void resetTempQuantity(){
        this.tempQuantityCart = 0;
    }

    /** Increases the amount sold by a specified amount **/
    public void increaseAmountSold(int amount){
        this.amountSold += amount;
    }

    /** Returns amount sold **/
    public int getAmountSold(){
        return this.amountSold;
    }
}
