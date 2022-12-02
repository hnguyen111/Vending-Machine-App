package assignment_2.view;

import assignment_2.model.Model;
import assignment_2.model.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

/** Log in page where user can either log in or sign up **/
public class LogInPage implements Page{

    private VBox logInPage;
    private VBox allText;

    private HBox buttons;
    private Button signInButton;
    private Button createUserButton;
    private Model model;

    private HBox entirePasswordArea;
    private PasswordField passwordField;
    private Label passwordLabel;

    private HBox entireUserNameArea;
    private TextField usernameField;
    private Label usernameLabel;
    private Screen view;
    private Header header;

    /** Log in/create user page **/
    public LogInPage(Screen view, Model model, Header header){
        this.model = model;
        this.view = view;
        this.header = header;
        this.init();
    }

    /** Initialises UI elements such as buttons and text fields **/
    public void init(){
        this.logInPage = new VBox();
        this.allText = new VBox();
        this.signInButton = new Button("Sign in");
        this.checkIfSignButtonPressed();

        this.createUserButton = new Button("Create user");
        this.checkIfCreateUserButtonPressed();

        this.usernameLabel = new Label("Enter username: ");
        this.usernameField = new TextField();
        this.entireUserNameArea = new HBox(this.usernameLabel, this.usernameField);
        this.entireUserNameArea.setPadding(new Insets(15));
        this.entireUserNameArea.setAlignment(Pos.CENTER);

        this.passwordLabel = new Label("Enter password: ");
        this.passwordField = new PasswordField();
        this.entirePasswordArea = new HBox(this.passwordLabel, this.passwordField);
        this.entirePasswordArea.setPadding(new Insets(15));
        this.entirePasswordArea.setAlignment(Pos.CENTER);

        this.buttons = new HBox(this.signInButton, this.createUserButton);
        buttons.setAlignment(Pos.CENTER);

        this.allText.getChildren().add(this.entireUserNameArea);
        this.allText.getChildren().add(this.entirePasswordArea);
        this.allText.getChildren().add(this.buttons);
        this.allText.setMinWidth(1280);
        this.allText.setAlignment(Pos.CENTER);
      
        HBox.setHgrow(this.allText, Priority.ALWAYS);
        VBox.setVgrow(this.allText, Priority.ALWAYS);

        this.logInPage.setMinSize(1280, 645);
        this.logInPage.getChildren().add(this.allText);
        this.logInPage.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
    }

    /** EventHandler which checks if sign in button has been pressed **/
    private void checkIfSignButtonPressed(){
        this.signInButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                System.out.println("Signing in");

                // Traverse through all users in system and see if it exists
                for (int i = 0; i < model.getAllUsers().size(); i++){
                    if (usernameField.getText().equals(model.getAllUsers().get(i).getUserName()) &&
                        passwordField.getText().equals(model.getAllUsers().get(i).getPassword())){
                            System.out.println("Successfully logged in as: ");
                            model.setUser(model.getAllUsers().get(i));
                            init();
                            header.changeLoginText();
                            header.setMenuBarVisibility(true);

                            view.changeToInitPage();
                            System.out.println(model.getCurrentUser().getUserName());
                            break;
                        }

                    if (usernameField.getText().equals(model.getAllUsers().get(i).getUserName())){
                        Label errorMessage = new Label("Incorrect password. Try again");
                        errorMessage.setTextFill(Color.RED);
                        if (allText.getChildren().size() > 3) {
                            allText.getChildren().remove(0);
                        }
                        allText.getChildren().add(0, errorMessage);
                        break;
                    }

                    //Ensure username and password fields are not empty
                    if (usernameField.getText().equals("") || passwordField.getText().equals("")) {
                        Label errorMessage = new Label("Enter both a username and password to sign in.");
                        errorMessage.setTextFill(Color.RED);

                        if (allText.getChildren().size() > 3) {
                            allText.getChildren().remove(0);
                        }
                        allText.getChildren().add(0, errorMessage);
                        break;
                    }

                    // If the username/password doesn't match any users
                    else if (i == model.getAllUsers().size() - 1){

                        Label errorMessage = new Label("User does not exist. Try creating a new user.");
                        errorMessage.setTextFill(Color.RED);
                        if (allText.getChildren().size() > 3) {
                            allText.getChildren().remove(0);
                        }
                        allText.getChildren().add(0, errorMessage);
                        break;

                    }
                }
                model.getTimeTracker().resetLastActivity();  
            }
        });
    }

    /** EventHandler which checks if create user button was pressed **/
    private void checkIfCreateUserButtonPressed(){
        this.createUserButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("Signing in");

                // Traverse through all users in system and see if it exists
                for (int i = 0; i < model.getAllUsers().size(); i++){
                    if (usernameField.getText().equals(model.getAllUsers().get(i).getUserName())) {
                        Label errorMessage = new Label("User already exists. Try and sign in.");
                        errorMessage.setTextFill(Color.RED);
                        if (allText.getChildren().size() > 3) {
                            allText.getChildren().remove(0);
                        }
                        allText.getChildren().add(0, errorMessage);
                        break;
                    }

                    //Ensure username and password fields are not empty
                    if (usernameField.getText().equals("") || passwordField.getText().equals("")) {
                        Label errorMessage = new Label("Enter both a username and password to create a user.");
                        errorMessage.setTextFill(Color.RED);

                        if (allText.getChildren().size() > 3) {
                            allText.getChildren().remove(0);
                        }
                        allText.getChildren().add(0, errorMessage);
                        break;
                    }


                    // If the username/password doesn't match any users
                    else if (i == model.getAllUsers().size() - 1){
                        User newUser = model.addUser(usernameField.getText(), passwordField.getText());
                        System.out.println("Successfully created new user and logged in as: ");
                        model.setUser(newUser);
                        init();
                        header.changeLoginText();
                        header.setMenuBarVisibility(true);
                        view.changeToInitPage();
                        System.out.println(model.getCurrentUser().getUserName());
                        break;

                    }
                }
                model.getTimeTracker().resetLastActivity();  
            }
        });
    }

    /** Returns page as a vertical box **/
    public VBox getPage(){
        return this.logInPage;
    }
}
