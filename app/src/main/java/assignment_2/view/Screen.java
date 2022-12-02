package assignment_2.view;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Timer;
import java.util.TimerTask;

import assignment_2.model.Model;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;

/** Main class for the UI display and contains the canvas, header and all pages **/
public class Screen extends TimerTask {
    private Scene scene;
    private BorderPane canvas;
    private Header header;
    private Model model;
    private InitPage initPage;
    private ListOfFive listOfFive;
    private SellerPage sellerPage;
    private CashierPage cashierPage;
    private RemovingRole removingRole;
    private AddingRole addingRole;
    private ReportPage reportPage;
    private LogInPage logInPage;

    public Screen(Model model) throws IOException, ParseException{
        this.model = model;
        this.initScreen();
    }

    /** Initialises UI elements such as buttons and text fields **/
    private void initScreen() throws IOException, ParseException{
        this.canvas = new BorderPane();
        this.scene = new Scene(canvas);
        this.header = new Header(this.model, this);

        this.listOfFive = new ListOfFive(this, model);
        this.initPage = new InitPage(this, model);
        this.logInPage = new LogInPage(this, this.model, this.header);
        this.sellerPage = new SellerPage(this, model);
        this.cashierPage = new CashierPage(this, model);
        this.removingRole = new RemovingRole(this, model);
        this.addingRole  = new AddingRole(this, model);
        this.reportPage = new ReportPage(this, model);
        this.canvas.setCenter((this.initPage.getScrollPane()));
        this.canvas.setTop(this.header.getHeader());

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if(!model.getTimeTracker().checkInactivity()){
                        timeOut();
                    }
                });
            }
        }, 1000, 1000);
    };
    

    /** Function which will be called when user has been inactive for too long **/
    public void timeOut(){

        // Display pop up message on screen
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("User Inactivity");
        alert.setHeaderText("Transaction has been cancelled and user has been logged out due to inactivity");
        alert.show();

        this.model.getTransactionHandler().cancelTransaction(model.getCurrentUser().getUserName(),
                                                        "timeout");

        // Log out and reset current shopping cart
        this.model.getTransactionHandler().resetShoppingBag();
        this.header.removeMenuItems();
        this.model.setUser(this.model.getAnonymousUser());
        this.header.changeLoginText(); 

        // Redirect user back to init page
        this.initPage = new InitPage(this, this.model);
        this.canvas.setCenter(this.initPage.getScrollPane());
        this.model.getTimeTracker().resetLastActivity();
    }

    /** Changes displayed page to log in page **/
    public void changeToLogInPage(){
        this.canvas.setCenter(this.logInPage.getPage());
    }
    /* Changes displayed page to default page */
    public void changeToInitPage(){
        this.initPage = new InitPage(this, this.model);
        this.canvas.setCenter(this.initPage.getScrollPane());
    }

    /** Changes displayed page to seller page **/
    public void changeToSellerPage(){
        this.sellerPage = new SellerPage(this, this.model);
        this.canvas.setCenter(this.sellerPage.getPage());
    }

    /** Changes displayed page to cashier page **/
    public void changeToCashierPage(){
        this.cashierPage.refreshPage();
        this.canvas.setCenter(this.cashierPage.getPage());
    }

    /** Changes displayed page to payment page **/
    public void changeToPaymentPage() {
        Payment paymentPage = new Payment(this, this.model);

        this.canvas.setCenter(paymentPage.getPage());
    }

    /** Changes displayed page to role removing page **/
    public void changeToRemovingRolePage(){
        this.removingRole.refreshPage();
        this.canvas.setCenter(this.removingRole.getPage());
    }

    /** Changes displayed page to role adding page **/
    public void changeToAddingRolePage(){
        this.addingRole.refreshPage();
        this.canvas.setCenter(this.addingRole.getPage());
    }

    /** Changes displayed page to report page **/
    public void changeToReportPage() throws FileNotFoundException, IOException, ParseException{
        this.reportPage = new ReportPage(this, this.model);
        this.canvas.setCenter(this.reportPage.getPage());
    }

    /** Returns the current scene **/
    public Scene getScene(){
        return this.scene;
    }

    /** Returns the borderpane **/
    public BorderPane getCanvas(){
        return this.canvas;
    }

    /** Returns the default page **/
    public InitPage getInitPage(){
        return this.initPage;
    }

    /** Returns the header **/
    public Header getHeader(){
        return this.header;
    }

    /** Returns the list of five page **/
    public ListOfFive getListOfFive(){
        return this.listOfFive;
    }

    /** Function which runs to keep track of user activity **/
    @Override
    public void run() {
        if (!this.model.getTimeTracker().checkInactivity()){
            this.timeOut();
            return;
        }
    }
}

