package assignment_2.model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/** Responsible for the program logic **/
public class Model {
    private ArrayList<User> allUsers; 
    private User currentUser;
    private ProductHandler productHandler;
    private TransactionHandler transactionHandler;
    private Cash cashHandler;
    private TimeTracker timeTracker;
    private User anonymousDefaultUser;
    private ReportGenerator reportGenerator;
    private Cash cash;
    private ChangeCalculator changeCalculator;

    public Model() throws IOException{
        this.init();
        this.initUsers();
    }

    /** Initialises the class object **/
    public void init() throws IOException{
        this.cash = new Cash();
        this.timeTracker = new TimeTracker();
        this.productHandler = new ProductHandler();
        this.transactionHandler = new TransactionHandler(this);
        this.cashHandler = new Cash();
        this.changeCalculator = new ChangeCalculator(this.cashHandler);

        // Retrieve all products and product categories
        this.productHandler.retrieveCategories("src/main/resources/productCategories.txt");
        this.productHandler.retrieveProducts("src/main/resources/products.txt");
        this.reportGenerator = new ReportGenerator(this);
    }

    /** Returns change calculator **/
    public ChangeCalculator getChangeCalculator(){
        return this.changeCalculator;
    }

    /** Initialises all users **/
    public void initUsers(){
        this.allUsers = new ArrayList<User>();

        User anonymous = new User("anonymous", "", "customer");
        this.anonymousDefaultUser = anonymous;
        User owner = new User("owner", "owner123", "owner");
        User cashier = new User("cashier", "cashier123", "cashier");
        User seller = new User("seller", "seller123", "seller");

        this.allUsers.add(owner);
        this.allUsers.add(cashier);
        this.allUsers.add(seller);
        this.allUsers.add(anonymous);
        this.currentUser = anonymous;

    }

    /** Adds a new user to the system **/
    public User addUser(String newUsername, String newPassword) {
        User newUser = new User(newUsername, newPassword, "customer");

        this.allUsers.add(newUser);

        return newUser;
    }

    /** Returns current logged in user **/
    public User getCurrentUser(){
        return this.currentUser;
    }

    /** Returns a list of all users in the system **/
    public ArrayList<User> getAllUsers(){
        return this.allUsers;
    }

    /** Sets the current user **/
    public void setUser(User user){
        this.currentUser = user;
    }

    /** Set the user to the anonymous user. Used for automatic logout **/
    public void setAnonymousUser() {
        this.currentUser = this.allUsers.get(3);
    }

    /** Returns the product handler **/
    public ProductHandler getProductHandler(){
        return this.productHandler;
    }

    /** Returns the transaction handler **/
    public TransactionHandler getTransactionHandler(){
        return this.transactionHandler;
    }

    /** Returns the cash handler **/
    public Cash getCashHandler(){
        return this.cashHandler;
    }

    /** Removes a user from the system **/
    public void removeUser(String username){
        for (int i = 0; i<this.allUsers.size(); i++){
            if (this.allUsers.get(i).getUserName().equals(username)){
                this.allUsers.remove(i);
                break;
            }
        }
    }
    
    /** Returns the time tracker **/
    public TimeTracker getTimeTracker(){
        return this.timeTracker;
    }

    /** Returns the anonymous default user **/
    public User getAnonymousUser(){
        return this.anonymousDefaultUser;
    }

    /** Returns the report generator **/
    public ReportGenerator getReportGenerator(){
        return this.reportGenerator;
    }

}
