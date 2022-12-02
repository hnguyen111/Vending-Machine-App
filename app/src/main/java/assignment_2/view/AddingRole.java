package assignment_2.view;

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
import javafx.scene.control.TextField;

import java.util.ArrayList;

/** Page displayed when an "Owner" user adds/modifies user roles **/
public class AddingRole implements Page{
    private Screen view;
    private Model model;
    private VBox page;
    private VBox titleBox;

    private Label title = new Label("Add A Seller/Cashier/Owner User");

    private Button addBtn;
    private VBox addBtnBox;

    private Menu roleMenu;
    private MenuBar roleMenuBar;
    private HBox roleMenuBox;

    private Label roleLabel;
    private HBox roleBox;

    private Label username;
    private TextField usernameTextField;
    private HBox usernameBox;

    String role;

    private Label outputMessage;

    private boolean isExisted;
    private boolean error;

    private Menu userMenu;
    private MenuBar userMenuBar;
    private Label userLabel;
    private HBox userBox;
    private HBox userMenuBox;
    private String message;
    private String stringUsername;
    private String usernameButton;

    /** Allows the owner to add roles to existing users **/
    public AddingRole(Screen view, Model model){
        this.model = model;
        this.view = view;
        this.init();
    }

    /** Initialises all UI elements such as buttons and text fields **/
    public void init(){
        this.title.setStyle("-fx-text-fill: black; -fx-font-size: 40px;");
        this.titleBox = new VBox(this.title);
        this.titleBox.setAlignment(Pos.TOP_CENTER);
        this.titleBox.setPadding(new Insets(10, 100, 15, 100));

        this.isExisted = false;
        this.error = false;

        this.userBox = new HBox();

        createRoleMenu();
        this.roleMenuBox = new HBox(this.roleMenuBar);
        this.roleLabel = new Label("Choose a role   ");
        this.roleBox = new HBox(this.roleLabel, this.roleMenuBox);
        this.roleBox.setAlignment(Pos.CENTER);
        this.roleBox.setPadding(new Insets(15, 140, 15, 70));
        
        this.addBtn = new Button("Add");
        this.addBtnBox = new VBox(this.addBtn);
        this.addBtn.setPrefSize(150, 20);
        this.addBtn.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, null, null)));
        addBtnBox.setAlignment(Pos.CENTER);
        addBtnBox.setPadding(new Insets(15, 40, 0, 160));
        checkAddButtonPressed();

        this.outputMessage = new Label("");
        this.outputMessage.setVisible(false);

        this.page = new VBox(this.userBox, this.roleBox, this.addBtnBox, this.outputMessage);
        this.page.setAlignment(Pos.CENTER);
        
        HBox.setHgrow(this.titleBox, Priority.ALWAYS);
        this.page.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));

        this.refreshPage();
    }

    /** Refreshes the page at startup and after a user has been changed **/
    public void refreshPage(){
        this.userBox.getChildren().clear();

        ArrayList<User> users = new ArrayList<User>();
        for(User user: this.model.getAllUsers()){
            if (this.model.getCurrentUser() != user && !user.getUserName().equals("anonymous")) {
                users.add(user);
            }
        }

        createUserMenu(users);
        this.userMenuBox = new HBox(this.userMenuBar);

        this.userLabel = new Label("Choose a user   ");
        this.userBox.getChildren().addAll(this.userLabel, this.userMenuBox);
        this.userBox.setAlignment(Pos.CENTER);
        this.userBox.setPadding(new Insets(15, 120, 15, 70));

        this.stringUsername = null;
        this.message = "";
    }

    /** Create the user menu **/
    private void createUserMenu(ArrayList<User> users){
        this.userMenu = new Menu("Current users");
        this.userMenuBar = new MenuBar(this.userMenu);
        for (int i = 0; i < users.size(); i++){
            MenuItem newItem = new MenuItem(String.format("%s (%s)", users.get(i).getUserName(), users.get(i).getUserType()));
            this.userMenu.getItems().add(newItem);
            this.checkUserPressed(newItem);
        }
    }

    /** Check if the user presses any of the user buttons **/
    private void checkUserPressed(MenuItem item){
        item.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                /* CHECK IF USER'S INACTIVITY WAS LONGER THAN THRESHOLD */
                if(model.getTimeTracker().checkInactivity() == false){
                    view.timeOut();
                    return;
                }
                model.getTimeTracker().resetLastActivity();

                stringUsername = item.getText().split(" ", 2)[0];
                usernameButton = item.getText();
                userMenu.setText(usernameButton);
            }
        });
    }

    /** Creates a menu containing all roles **/
    private void createRoleMenu(){
        this.roleMenu = new Menu("Roles");
        this.roleMenuBar = new MenuBar(this.roleMenu);
        String[] roles = {"cashier", "seller", "owner"};
        for (int i = 0; i < roles.length; i++){
            MenuItem newItem = new MenuItem(roles[i]);
            this.roleMenu.getItems().add(newItem);
            this.checkRolePressed(newItem);
        }   
    }

    /** Contains EventHandler which checks if "role" button has been pressed **/
    private void checkRolePressed(MenuItem item){
        item.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                /* CHECK IF USER'S INACTIVITY WAS LONGER THAN THRESHOLD */
                if(model.getTimeTracker().checkInactivity() == false){
                    view.timeOut();
                    return;
                }
                model.getTimeTracker().resetLastActivity();  

                role = item.getText();
                roleMenu.setText(role);                            
            }   
        });
    }

    /** EventHandler which checks if "add" button has been pressed **/
    public void checkAddButtonPressed(){
        this.addBtn.setOnAction(new EventHandler<ActionEvent>() {

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
                isExisted = false;

                if (stringUsername == null){
                    outputMessage.setText("Enter a username");
                    error = true;
                } else if (role == null) {
                    outputMessage.setText("Choose a role");
                    error = true;
                } if (stringUsername.equals(role)){
                    outputMessage.setText(String.format("User is already a %s. Choose a different role.", role));
                    error = true;
                } else {
                    for (User user : model.getAllUsers()){
                        if (user.getUserName().equals(stringUsername)){
                            isExisted = true;
                            break;
                        }
                    }
                    if (!isExisted){
                        outputMessage.setText("Username does not exist");
                        error = true;
                    }
                }

                if(!error && isExisted){
                    for (User user : model.getAllUsers()){
                        if (user.getUserName().equals(stringUsername)){
                            user.setUserType(role);
                            break;
                        }
                    }
                    outputMessage.setText("Added");
                    error = false;
                    isExisted = false;
                    roleMenu.setText("Roles");
                    refreshPage();
                }
            }
        });
    }

    /** Returns the page as a vertical box **/
    public VBox getPage(){
        return this.page;
    }  
}
