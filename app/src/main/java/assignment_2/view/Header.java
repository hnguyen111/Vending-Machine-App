package assignment_2.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.text.ParseException;

import assignment_2.model.Model;

/** Header containing log in button, menu and page titles. Will be displayed at the top of the screen. **/
public class Header {

    private Button logInButton;
    private HBox logInBox;
    private Model model;
    private HBox header;
    private Screen view;
    private Label title;
    private HBox titleBox;
    private Menu menu;
    private HBox menuBox;
    private MenuBar menuBar;
    private MenuItem buyProductsMenu;
    private MenuItem sellerPageMenu;
    private MenuItem cashierPageMenu;
    private MenuItem addRolePageMenu;
    private MenuItem removeRolePageMenu;
    private MenuItem reportPageMenu;

    /** Creates the header for all pages of the application **/
    public Header(Model model, Screen view){
        this.view = view;
        this.model = model;
        this.initHeader();
    }

    /** Initialises the header with log in/out buttons etc **/
    public void initHeader(){
        this.createMenu();
        this.header = new HBox();
        this.header.getChildren().add(this.menuBox);
        this.header.getChildren().get(0).setVisible(true);

        this.title = new Label("Lite Snacks Vending Machine");
        this.title.setStyle("-fx-text-fill: white; -fx-font-size: 35px;");
        this.titleBox = new HBox(this.title);
        this.titleBox.setAlignment(Pos.CENTER_RIGHT);
        HBox.setHgrow(this.titleBox, Priority.ALWAYS);
        this.title.setVisible(true);
        this.header.getChildren().add(this.titleBox);

        // Wrap log-in button in horizontal box to make repositioning etc easier
        if (model.getCurrentUser().getUserName().equals("anonymous")){
            this.logInButton = new Button("Log in");
        } else {
            this.logInButton = new Button("Log out");
        }

        this.logInButton.setPrefSize(75, 50);
        this.logInButton.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, null, null)));
        this.checkForLogInClick();
        this.logInBox = new HBox(this.logInButton);
        this.logInBox.setPadding(new Insets(20));
        HBox.setHgrow(this.logInBox, Priority.ALWAYS);
        this.logInBox.setAlignment(Pos.CENTER_RIGHT);

        this.header.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
        this.header.setMinSize(1280, 75);
        this.header.getChildren().add(this.logInBox);
    }

    /** Create the menu bar **/
    protected void createMenu(){
        this.menu = new Menu("Menu");
        this.menuBar = new MenuBar(this.menu);
        this.menuBox = new HBox(this.menuBar);
        this.menuBox.setPadding(new Insets(20));
        this.menuBox.setAlignment(Pos.CENTER_LEFT);
        VBox.setVgrow(this.menuBox, Priority.ALWAYS);
        this.menuBar.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, null, new Insets(0))));

        // Add the initial products menu
        this.buyProductsMenu = new MenuItem("Buy products");
        this.menu.getItems().addAll(this.buyProductsMenu);
        this.checkBuyProductsClicked();
    }

    /** Returns log in button **/
    public Button getLogInButton(){
        return this.logInButton;
    }

    /** Listens for mouse click on log-in button **/
    private void checkForLogInClick(){
        this.logInButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                /* CHECK IF USER'S INACTIVITY WAS LONGER THAN THRESHOLD */
                if(model.getTimeTracker().checkInactivity() == false){
                    view.timeOut();
                    return;
                }
                model.getTimeTracker().resetLastActivity();

                loginLogic();
            }
        });
    }

    /** Allow a user to log in or log out **/
    public void loginLogic() {
        // If user is not already logged in, prompt user with log in options
        if (model.getCurrentUser() == null || model.getCurrentUser().getUserName().equals("anonymous")){
            if (title != null){
                title.setVisible(false);
            }
            System.out.println("Button pressed: logging in");
            view.changeToLogInPage();
        }

        // If user is already logged in, give them the option to log out
        else {
            System.out.println("Button pressed: logging out");

            removeMenuItems();

            if (title != null){
                title.setVisible(false);
            }
            model.setUser(model.getAnonymousUser());
            setMenuBarVisibility(true);
            changeLoginText();
            view.getListOfFive().updateList();
            view.changeToInitPage();
        }
    }

    /** Remove the added menu items when a user type logs out **/
    public void removeMenuItems() {
        if (model.getCurrentUser().getUserType().equals("seller")) {
            int indexToDelete = 2;
            menu.getItems().remove(indexToDelete);
            menu.getItems().remove(indexToDelete - 1);
        }

        if (model.getCurrentUser().getUserType().equals("cashier")) {
            int indexToDelete = 2;
            menu.getItems().remove(indexToDelete);
            menu.getItems().remove(indexToDelete - 1);
        }

        if (model.getCurrentUser().getUserType().equals("owner")) {
            int indexToDelete = 5;
            menu.getItems().remove(indexToDelete);
            menu.getItems().remove(indexToDelete - 1);
            menu.getItems().remove(indexToDelete - 2);
            menu.getItems().remove(indexToDelete - 3);
            menu.getItems().remove(indexToDelete - 4);
        }
    }

    /** If user is logged in, change text on "login" button to "log off" and vice versa **/
    public void changeLoginText(){

        if (!this.model.getCurrentUser().getUserName().equals("anonymous") && 
                                    this.logInButton.getText().equals("Log in")){
            this.logInButton.setText("Log off");

        } else {
            this.logInButton.setText("Log in");
        }
    }

    /** Makes sure the menu bar is visible or not depending on the page **/
    public void setMenuBarVisibility(boolean visibility){
        this.header.getChildren().get(0).setVisible(visibility);

        this.checkBuyProductsClicked();

        //Unnecessary?
        if (visibility){
            setTitle("Vending machine");
            setTitleVisibility(true);
        }

        //Update menu items for each type of user
        if (this.model.getCurrentUser() != null) {
            if (this.model.getCurrentUser().getUserType().equals("seller")) {
                this.sellerPageMenu = new MenuItem("Update products");
                this.reportPageMenu = new MenuItem("Generate reports");
                this.menu.getItems().addAll(this.sellerPageMenu, this.reportPageMenu);

                this.checkSellerPageClicked();
                this.checkReportPageClicked();
            }

            else if (this.model.getCurrentUser().getUserType().equals("cashier")) {
                this.cashierPageMenu = new MenuItem("Update cash");
                this.reportPageMenu = new MenuItem("Generate reports");
                this.menu.getItems().addAll(this.cashierPageMenu, this.reportPageMenu);

                this.checkCashierPageClicked();
                this.checkReportPageClicked();
            }

            else if (this.model.getCurrentUser().getUserType().equals("owner")) {
                this.sellerPageMenu = new MenuItem("Update products");
                this.cashierPageMenu = new MenuItem("Update cash");
                this.addRolePageMenu = new MenuItem("Add a role");
                this.removeRolePageMenu = new MenuItem("Remove a role");
                this.reportPageMenu = new MenuItem("Generate reports");

                this.menu.getItems().addAll(this.sellerPageMenu, this.cashierPageMenu, this.addRolePageMenu, 
                                            this.removeRolePageMenu, this.reportPageMenu);

                this.checkSellerPageClicked();
                this.checkCashierPageClicked();
                this.checkAddRolePageClicked();
                this.checkRemoveRolePageClicked();
                this.checkReportPageClicked();
            }

            view.getListOfFive().updateList();
        }

    }

    /** Sets title of page **/
    public void setTitle(String newTitle){
        this.title = new Label(newTitle);
        this.title.setStyle("-fx-text-fill: white;"+
                "-fx-font-size: 30;");
        this.title.setTextFill(Color.WHITE);
        this.titleBox.getChildren().clear();
        this.titleBox.getChildren().add(this.title);
        this.titleBox.setAlignment(Pos.CENTER_RIGHT);
        HBox.setHgrow(this.titleBox, Priority.ALWAYS);
    }

    /** Sets title visibility **/
    public void setTitleVisibility(boolean bool){
        if (this.title != null){
            this.title.setVisible(bool);
        }
    }

    /** Returns header as a horizontal box **/
    public HBox getHeader(){
        return this.header;
    }

    /** Checks if the add admin menu item is clicked on **/
    private void checkBuyProductsClicked(){
        this.buyProductsMenu.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                /* CHECK IF USER'S INACTIVITY WAS LONGER THAN THRESHOLD */
                if(model.getTimeTracker().checkInactivity() == false){
                    view.timeOut();
                    return;
                }
                model.getTimeTracker().resetLastActivity();  

                setTitleVisibility(true);
                setTitle("Vending machine");
                view.changeToInitPage();
            }
        });
    }

    /** Checks if the reports page menu item is clicked on **/
    private void checkReportPageClicked(){
        this.reportPageMenu.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                /* CHECK IF USER'S INACTIVITY WAS LONGER THAN THRESHOLD */
                if(model.getTimeTracker().checkInactivity() == false){
                    view.timeOut();
                    return;
                }
                model.getTimeTracker().resetLastActivity();  

                setTitleVisibility(true);
                setTitle("Vending machine");
                try {
                    view.changeToReportPage();
                } catch (IOException | ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
    }

    /** Checks if the add admin menu item is clicked on **/
    private void checkSellerPageClicked(){
        this.sellerPageMenu.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                /* CHECK IF USER'S INACTIVITY WAS LONGER THAN THRESHOLD */
                if(model.getTimeTracker().checkInactivity() == false){
                    view.timeOut();
                    return;
                }
                model.getTimeTracker().resetLastActivity();  

                setTitleVisibility(true);
                setTitle("Updating products in system");
                view.changeToSellerPage();
            }
        });
    }

    /** Checks if the cashier page is clicked on **/
    private void checkCashierPageClicked(){
        this.cashierPageMenu.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                /* CHECK IF USER'S INACTIVITY WAS LONGER THAN THRESHOLD */
                if(model.getTimeTracker().checkInactivity() == false){
                    view.timeOut();
                    return;
                }
                model.getTimeTracker().resetLastActivity();  

                setTitleVisibility(true);
                setTitle("Updating cash in system");
                view.changeToCashierPage();
            }
        });
    }

    /** Checks if the add role is clicked on **/
    private void checkAddRolePageClicked(){
        this.addRolePageMenu.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                /* CHECK IF USER'S INACTIVITY WAS LONGER THAN THRESHOLD */
                if(model.getTimeTracker().checkInactivity() == false){
                    view.timeOut();
                    return;
                }
                model.getTimeTracker().resetLastActivity();  

                setTitleVisibility(true);
                setTitle("Adding user roles to the system");
                view.changeToAddingRolePage();
            }
        });
    }

    /** Checks if the remove role is clicked on **/
    private void checkRemoveRolePageClicked(){
        this.removeRolePageMenu.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                /* CHECK IF USER'S INACTIVITY WAS LONGER THAN THRESHOLD */
                if(model.getTimeTracker().checkInactivity() == false){
                    view.timeOut();
                    return;
                }
                model.getTimeTracker().resetLastActivity();  

                setTitleVisibility(true);
                setTitle("Removing user roles from the system");
                view.changeToRemovingRolePage();
            }
        });
    }
}