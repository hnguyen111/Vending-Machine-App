package assignment_2.view;

import java.util.ArrayList;
import assignment_2.model.Model;
import assignment_2.model.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/** Page displayed when an "Owner" user is removing another user's role **/
public class RemovingRole implements Page{
    private Screen view;
    private Model model;
    private VBox page;
    private VBox titleBox;

    private Label title = new Label("Remove A Seller/Cashier/Owner User");

    private Button removeBtn;
    private VBox removeBtnBox;

    private Menu cashierMenu;
    private MenuBar cashierMenuBar;
    private HBox cashierMenuBox;
    private Menu sellerMenu;
    private MenuBar sellerMenuBar;
    private HBox sellerMenuBox;
    private Menu ownerMenu;
    private MenuBar ownerMenuBar;
    private HBox ownerMenuBox;

    private Label cashierLabel;
    private HBox cashierBox;
    private Label sellerLabel;
    private HBox sellerBox;
    private Label ownerLabel;
    private HBox ownerBox;

    private Label outputMessage;

    private String cashierUsername;
    private String sellerUsername;
    private String ownerUsername;

    private boolean error;
    private String message;

    /** Allows owner to remove a role **/

    public RemovingRole(Screen view, Model model){
        this.model = model;
        this.view = view;
        this.init();
    }

    /** Initialises UI elements such as buttons and text fields **/
    public void init(){
        this.title.setStyle("-fx-text-fill: black; -fx-font-size: 40px;");
        this.titleBox = new VBox(this.title);
        this.titleBox.setAlignment(Pos.TOP_CENTER);
        this.titleBox.setPadding(new Insets(10, 100, 15, 100));

        this.cashierBox = new HBox();
        this.sellerBox = new HBox();
        this.ownerBox = new HBox();
        this.removeBtn = new Button("Remove");
        this.removeBtnBox = new VBox(this.removeBtn);
        this.outputMessage = new Label("");
        this.outputMessage.setVisible(false);
        
        this.page = new VBox(this.cashierBox, this.sellerBox, this.ownerBox, this.removeBtnBox, this.outputMessage);
        this.page.setAlignment(Pos.CENTER);
        
        HBox.setHgrow(this.titleBox, Priority.ALWAYS);
        this.page.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        this.refreshPage();
    }

    /** Refresh page and clear user input **/
    public void refreshPage(){
        this.cashierBox.getChildren().clear();
        this.sellerBox.getChildren().clear();
        this.ownerBox.getChildren().clear();

        ArrayList<String> cashierUsernames = new ArrayList<String>();
        ArrayList<String> sellerUsernames = new ArrayList<String>();
        ArrayList<String> ownerUsernames = new ArrayList<String>();
        for(User user: this.model.getAllUsers()){
            if (user.getUserType().equals("cashier")){
                cashierUsernames.add(user.getUserName());
            } else if (user.getUserType().equals("seller")){
                sellerUsernames.add(user.getUserName());
            } else if (user.getUserType().equals("owner") && 
                    !model.getCurrentUser().getUserName().equals(user.getUserName())){
                ownerUsernames.add(user.getUserName());
            }
        }

        createCashierMenu(cashierUsernames);
        this.cashierMenuBox = new HBox(this.cashierMenuBar);
        createSellerMenu(sellerUsernames);
        this.sellerMenuBox = new HBox(this.sellerMenuBar);
        createOwnerMenu(ownerUsernames);
        this.ownerMenuBox = new HBox(this.ownerMenuBar);
        
        this.removeBtn.setPrefSize(150, 20);
        this.removeBtn.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, null, null)));
        removeBtnBox.setAlignment(Pos.CENTER);
        removeBtnBox.setPadding(new Insets(15, 40, 0, 160));
        checkRemoveButtonPressed();

        this.cashierLabel = new Label("Remove a cashier  ");
        this.cashierBox.getChildren().addAll(this.cashierLabel, this.cashierMenuBox);
        this.sellerLabel = new Label("Remove a seller     ");
        this.sellerBox.getChildren().addAll(this.sellerLabel, this.sellerMenuBox);
        this.ownerLabel = new Label("Remove a owner   ");
        this.ownerBox.getChildren().addAll(this.ownerLabel, this.ownerMenuBox);

        this.cashierBox.setPadding(new Insets(15));
        this.cashierBox.setAlignment(Pos.CENTER);
        this.sellerBox.setPadding(new Insets(15));
        this.sellerBox.setAlignment(Pos.CENTER);
        this.ownerBox.setPadding(new Insets(15));
        this.ownerBox.setAlignment(Pos.CENTER);

        this.cashierUsername = null;
        this.sellerUsername = null;
        this.ownerUsername = null;
        this.error = false;
        this.message = "";
    }

    /** Create a menu containing cashiers **/
    private void createCashierMenu(ArrayList<String> cashierUsernames){
        this.cashierMenu = new Menu("Current cashiers ");
        this.cashierMenuBar = new MenuBar(this.cashierMenu);
        for (int i = 0; i < cashierUsernames.size(); i++){
            MenuItem newItem = new MenuItem(cashierUsernames.get(i));
            this.cashierMenu.getItems().add(newItem);
            this.checkCashierPressed(newItem);
        }   
    }

    /** Creates a menu containing sellers **/
    private void createSellerMenu(ArrayList<String> sellerUsernames){
        this.sellerMenu = new Menu("Current sellers    ");
        this.sellerMenuBar = new MenuBar(this.sellerMenu);
        for (int i = 0; i < sellerUsernames.size(); i++){
            MenuItem newItem = new MenuItem(sellerUsernames.get(i));
            this.sellerMenu.getItems().add(newItem);
            this.checkSellerPressed(newItem);
        }   
    }

    /** Creates a menu containing owners **/
    private void createOwnerMenu(ArrayList<String> ownerUsernames){
        this.ownerMenu = new Menu("Current owners   ");
        this.ownerMenuBar = new MenuBar(this.ownerMenu);
        for (int i = 0; i < ownerUsernames.size(); i++){
            MenuItem newItem = new MenuItem(ownerUsernames.get(i));
            this.ownerMenu.getItems().add(newItem);
            this.checkOwnerPressed(newItem);
        }   
    }

    /** EventHandler checking if "cashier" menu item is pressed **/
    private void checkCashierPressed(MenuItem item){
        item.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                /* CHECK IF USER'S INACTIVITY WAS LONGER THAN THRESHOLD */
                if(model.getTimeTracker().checkInactivity() == false){
                    view.timeOut();
                    return;
                }
                model.getTimeTracker().resetLastActivity();  

                cashierUsername = item.getText();
                cashierMenu.setText(cashierUsername);                            
            }   
        });
    }

    /** EventHandler checking if "seller" menu item is pressed **/
    private void checkSellerPressed(MenuItem item){
        item.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                /* CHECK IF USER'S INACTIVITY WAS LONGER THAN THRESHOLD */
                if(model.getTimeTracker().checkInactivity() == false){
                    view.timeOut();
                    return;
                }
                model.getTimeTracker().resetLastActivity();  

                sellerUsername = item.getText();
                sellerMenu.setText(sellerUsername);                            
            }   
        });
    }

    /** EventHandler checking if "owner" menu item is pressed **/
    private void checkOwnerPressed(MenuItem item){
        item.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                /* CHECK IF USER'S INACTIVITY WAS LONGER THAN THRESHOLD */
                if(model.getTimeTracker().checkInactivity() == false){
                    view.timeOut();
                    return;
                }
                model.getTimeTracker().resetLastActivity();  

                ownerUsername = item.getText();
                ownerMenu.setText(ownerUsername);                            
            }   
        });
    }

    /** EventHandler checking if "remove" button is pressed **/
    public void checkRemoveButtonPressed(){
        this.removeBtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                /* CHECK IF USER'S INACTIVITY WAS LONGER THAN THRESHOLD */
                if(model.getTimeTracker().checkInactivity() == false){
                    view.timeOut();
                    return;
                }
                model.getTimeTracker().resetLastActivity();  
                
                
                outputMessage.setVisible(true);

                error = false;
                if (cashierUsername == null && sellerUsername == null && ownerUsername == null){
                    outputMessage.setText("Choose one seller/cashier/owner");
                    error = true;
                }

                if(!error){
                    message = "Removed ";
                    if (cashierUsername != null){
                        model.removeUser(cashierUsername);
                        message = String.format(message + cashierUsername);
                    }  
                    if (sellerUsername != null){
                        model.removeUser(sellerUsername);
                        if (cashierUsername != null){
                            message = String.format(message + ", " + sellerUsername);
                        } else {
                            message = String.format(message + sellerUsername);
                        }
                        
                    }
                    if (ownerUsername != null){
                        model.removeUser(ownerUsername);
                        if (cashierUsername != null || sellerUsername != null){
                            message = String.format(message + ", " + ownerUsername);
                        } else {
                            message = String.format(message + ownerUsername);
                        }
                    }
                    outputMessage.setText(message);
                    refreshPage();              
                }             
            }
        });
    }

    /** Returns page as a vertical box **/
    public VBox getPage(){
        return this.page;
    }
    
}