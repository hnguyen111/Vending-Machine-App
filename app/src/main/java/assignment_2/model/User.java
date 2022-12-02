package assignment_2.model;

import java.util.ArrayList;

/** A User object can be either Customer, Seller, Cashier or Owner **/
public class User {

    private String userName;
    private String password;
    private String userType;
    private ArrayList<Product> lastFiveProducts;

    public User(String userName, String password, String userType){
        this.userName = userName;
        this.password = password;
        this.userType = userType;
        this.lastFiveProducts = new ArrayList<Product>();
    }

    /** Returns user name **/
    public String getUserName(){
        return this.userName;
    }

    /** Returns password **/
    public String getPassword(){
        return this.password;
    }

    /** Returns user type (ie. cashier, seller, owner or customer) **/
    public String getUserType(){
        return this.userType;
    }

    /** Sets user type **/
    public String setUserType(String newRole){
        return this.userType = newRole;
    }

    /** Adds last five products to list **/
    public void addLastFiveProduct(Product product) {

        if (this.lastFiveProducts.contains(product)) {
            this.lastFiveProducts.remove(product);
        }

        this.lastFiveProducts.add(0, product);

        if (this.lastFiveProducts.size() > 5) {
            this.lastFiveProducts.remove(5);

        }
    }

    /** Returns last five products **/
    public ArrayList<Product> getLastFiveProducts(){
        return this.lastFiveProducts;
    }
    
}



