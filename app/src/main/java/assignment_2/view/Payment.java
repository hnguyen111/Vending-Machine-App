package assignment_2.view;

import assignment_2.model.Model;
import assignment_2.model.Product;
import assignment_2.model.ChangeCalculator;
import assignment_2.model.Cash;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.HashMap;

/** Page displaying payment method, cash insertion and completion of transactions **/
public class Payment implements Page{
    private ChangeCalculator changeCalculator;
    private Cash cash;

    private HBox titleBox;
    private Label title = new Label("Enter cash");
    private Button cancelBtn;
    private HBox cancelBtnBox;

    private double amountPaid;

    private VBox paymentPage;
    private VBox allText;

    private HBox noteButtons;
    private Button hundredButton;
    private Button fiftyButton;
    private Button twentyButton;
    private Button tenButton;
    private Button fiveButton;
    private HBox coinButtons;
    private Button twoButton;
    private Button oneButton;
    private Button fiftyCentsButton;
    private Button twentyCentsButton;
    private Button tenCentsButton;
    private Button fiveCentsButton;
    private Model model;

    private Label amountDueMessage;
    private Label noteHeading;
    private Label coinHeading;

    private Screen view;
    private double amountDue;

    /** Allows the user to pay and receive change **/
    public Payment(Screen view, Model model){
        this.model = model;
        this.view = view;
        this.init();
    }

    /** Initialise the UI and buttons of the page **/
    public void init(){

        this.title.setStyle("-fx-text-fill: black; -fx-font-size: 30px;");
        this.titleBox = new HBox(this.title);

        this.titleBox.setPadding(new Insets(40, 0, 100, 550));
        this.titleBox.setAlignment(Pos.TOP_CENTER);

        this.cancelBtn = new Button("Cancel Transaction");
        this.cancelBtn.setVisible(true);
        this.cancelBtnBox = new HBox(this.cancelBtn);
        this.cancelBtn.setPrefSize(150, 20);
        this.cancelBtn.setBackground(new Background(new BackgroundFill(Color.rgb(255, 127, 127), null, null)));
        cancelBtnBox.setAlignment(Pos.TOP_RIGHT);
        cancelBtnBox.setPadding(new Insets(15, 0,0, 350));
        this.titleBox.getChildren().add(this.cancelBtnBox);
        checkCancelButtonPressed();

        HBox.setHgrow(this.titleBox, Priority.ALWAYS);
        this.amountDue = this.model.getTransactionHandler().getTotal();
        this.amountPaid = 0;

        this.changeCalculator = this.model.getChangeCalculator();
        this.cash = this.model.getCashHandler();
        this.paymentPage = new VBox();
        this.allText = new VBox();

        this.amountDueMessage = new Label(String.format("Total amount = $%.2f", this.model.getTransactionHandler().getTotal()));
        this.noteHeading = new Label("Input notes:");
        this.noteHeading.setStyle("-fx-text-fill: black; -fx-font-size: 16px;");
        this.coinHeading = new Label("Input coins:");
        this.coinHeading.setStyle("-fx-text-fill: black; -fx-font-size: 16px;");
        this.amountDueMessage.setStyle("-fx-text-fill: black; -fx-font-size: 30px;");

        //Make all payment buttons
        this.hundredButton = new Button("$100");
        this.fiftyButton = new Button("$50");
        this.twentyButton = new Button("$20");
        this.tenButton = new Button("$10");
        this.fiveButton = new Button("$5");

        this.checkIfHundredButtonPressed();
        this.checkIfFiftyButtonPressed();
        this.checkIfTwentyButtonPressed();
        this.checkIfTenButtonPressed();
        this.checkIfFiveButtonPressed();

        this.twoButton = new Button("$2");
        this.oneButton = new Button("$1");
        this.fiftyCentsButton = new Button("50c");
        this.twentyCentsButton = new Button("20c");
        this.tenCentsButton = new Button("10c");
        this.fiveCentsButton = new Button("5c");

        this.checkIfTwoButtonPressed();
        this.checkIfOneButtonPressed();
        this.checkIfFiftyCentsButtonPressed();
        this.checkIfTwentyCentsButtonPressed();
        this.checkIfTenCentsButtonPressed();
        this.checkIfFiveCentsButtonPressed();

        this.noteButtons = new HBox(this.hundredButton, this.fiftyButton, this.twentyButton,
                            this.tenButton, this.fiveButton);
        this.coinButtons = new HBox(this.twoButton, this.oneButton, this.fiftyCentsButton,
                this.twentyCentsButton, this.tenCentsButton, this.fiveCentsButton);
        noteButtons.setAlignment(Pos.CENTER);
        coinButtons.setAlignment(Pos.CENTER);

        this.allText.getChildren().add(this.titleBox);
        this.allText.getChildren().add(amountDueMessage);
        this.allText.getChildren().add(this.noteHeading);
        this.allText.getChildren().add(this.noteButtons);
        this.allText.getChildren().add(this.coinHeading);
        this.allText.getChildren().add(this.coinButtons);

        this.allText.setMinWidth(1280);
        this.allText.setAlignment(Pos.TOP_CENTER);
      
        HBox.setHgrow(this.allText, Priority.ALWAYS);
        VBox.setVgrow(this.allText, Priority.ALWAYS);

        this.paymentPage.setMinSize(1280, 645);
        this.paymentPage.getChildren().add(this.allText);
        this.paymentPage.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));

    }

    /** Check if the user presses the hundred dollar button **/
    private void checkIfHundredButtonPressed(){
        this.hundredButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                /* CHECK IF USER'S INACTIVITY WAS LONGER THAN THRESHOLD */
                if(model.getTimeTracker().checkInactivity() == false){
                    view.timeOut();
                    return;
                }
                model.getTimeTracker().resetLastActivity();  

                cashButtonsLogic(0, 100);

            }
        });
    }

    /** Check if the user presses the fifty dollar button **/
    private void checkIfFiftyButtonPressed(){
        this.fiftyButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                /* CHECK IF USER'S INACTIVITY WAS LONGER THAN THRESHOLD */
                if(model.getTimeTracker().checkInactivity() == false){
                    view.timeOut();
                    return;
                }
                model.getTimeTracker().resetLastActivity();  

                cashButtonsLogic(1, 50);

            }
        });
    }

    /** Check if the user presses the twenty dollar button **/
    private void checkIfTwentyButtonPressed(){
        this.twentyButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                /* CHECK IF USER'S INACTIVITY WAS LONGER THAN THRESHOLD */
                if(model.getTimeTracker().checkInactivity() == false){
                    view.timeOut();
                    return;
                }
                model.getTimeTracker().resetLastActivity();  

                cashButtonsLogic(2, 20);

            }
        });
    }

    /* Check if the user presses the ten dollar button */
    private void checkIfTenButtonPressed(){
        this.tenButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                /* CHECK IF USER'S INACTIVITY WAS LONGER THAN THRESHOLD */
                if(model.getTimeTracker().checkInactivity() == false){
                    view.timeOut();
                    return;
                }
                model.getTimeTracker().resetLastActivity();  

                cashButtonsLogic(3, 10);

            }
        });
    }

    /** Check if the user presses the five dollar button **/
    private void checkIfFiveButtonPressed(){
        this.fiveButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                /* CHECK IF USER'S INACTIVITY WAS LONGER THAN THRESHOLD */
                if(model.getTimeTracker().checkInactivity() == false){
                    view.timeOut();
                    return;
                }
                model.getTimeTracker().resetLastActivity();  

                cashButtonsLogic(4, 5);

            }
        });
    }

    /** Check if the user presses the two dollar button **/
    private void checkIfTwoButtonPressed(){
        this.twoButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                /* CHECK IF USER'S INACTIVITY WAS LONGER THAN THRESHOLD */
                if(model.getTimeTracker().checkInactivity() == false){
                    view.timeOut();
                    return;
                }
                model.getTimeTracker().resetLastActivity();  

                cashButtonsLogic(5, 2);

            }
        });
    }

    /** Check if the user presses the one dollar button **/
    private void checkIfOneButtonPressed(){
        this.oneButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                /* CHECK IF USER'S INACTIVITY WAS LONGER THAN THRESHOLD */
                if(model.getTimeTracker().checkInactivity() == false){
                    view.timeOut();
                    return;
                }
                model.getTimeTracker().resetLastActivity();  

                cashButtonsLogic(6, 1);

            }
        });
    }

    /** Check if the user presses the fifty cents button **/
    private void checkIfFiftyCentsButtonPressed(){
        this.fiftyCentsButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                /* CHECK IF USER'S INACTIVITY WAS LONGER THAN THRESHOLD */
                if(model.getTimeTracker().checkInactivity() == false){
                    view.timeOut();
                    return;
                }
                model.getTimeTracker().resetLastActivity();  

                cashButtonsLogic(7, 0.5);

            }
        });
    }

    /** Check if the user presses the twenty cents button **/
    private void checkIfTwentyCentsButtonPressed(){
        this.twentyCentsButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                /* CHECK IF USER'S INACTIVITY WAS LONGER THAN THRESHOLD */
                if(model.getTimeTracker().checkInactivity() == false){
                    view.timeOut();
                    return;
                }
                model.getTimeTracker().resetLastActivity();  

                cashButtonsLogic(8, 0.2);

            }
        });
    }

    /** Check if the user presses the ten cents button **/
    private void checkIfTenCentsButtonPressed(){
        this.tenCentsButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                /* CHECK IF USER'S INACTIVITY WAS LONGER THAN THRESHOLD */
                if(model.getTimeTracker().checkInactivity() == false){
                    view.timeOut();
                    return;
                }
                model.getTimeTracker().resetLastActivity();  

                cashButtonsLogic(9, 0.1);
            }
        });
    }

    /** Check if the user presses the five cents button **/
    private void checkIfFiveCentsButtonPressed(){
        this.fiveCentsButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                /* CHECK IF USER'S INACTIVITY WAS LONGER THAN THRESHOLD */
                if(model.getTimeTracker().checkInactivity() == false){
                    view.timeOut();
                    return;
                }
                model.getTimeTracker().resetLastActivity();  

                cashButtonsLogic(10, 0.05);

            }
        });
    }

    /** Display and calculation of remaining change after different cash buttons are pressed **/
    public void cashButtonsLogic(int index, double cashValue) {
        int amountDueNoDecimal = (int)(amountDue*100);

        //Display an error if the balance is already paid off
        if (amountDue == 0) {
            Label errorLabel = new Label(String.format("Do not enter more cash. Balance already paid off."));
            errorLabel.setTextFill(Color.RED);
            if (allText.getChildren().size() > 8) {
                allText.getChildren().remove(3);
            }
            allText.getChildren().add(3, errorLabel);
        }

        // Calculate the remaining balance after a cash button has been pressed
        else {
            if (amountDue < cashValue) {
                amountDue = 0;
                amountDueNoDecimal = 0;
            }

            else {
                amountDue = amountDue - cashValue;
                amountDueNoDecimal = amountDueNoDecimal - (int)(cashValue * 100);
            }

            this.model.getTransactionHandler().incrementCurrentCash(index);

            //Work out the amount paid and display the amount due
            amountPaid += cashValue;
            Label amountDueLabel = new Label(String.format("Amount due =  $%.2f", amountDue));
            amountDueLabel.setStyle("-fx-text-fill: black; -fx-font-size: 30px;");

            if (allText.getChildren().size() > 6) {
                allText.getChildren().remove(2);
            }
            allText.getChildren().add(2, amountDueLabel);

            //If the amount has been paid off, display a pay button for the user to finalise their input cash
            if (amountDueNoDecimal == 0) {
                Button payButton = new Button("Pay");
                payButton.setStyle("-fx-text-fill: black; -fx-font-size: 17px;");
                payButton.setBackground(new Background(new BackgroundFill(Color.rgb(184,216,190), null, null)));

                checkIfPayButtonPressed(payButton);
                allText.getChildren().add(payButton);

            }
        }
    }

    /** Check if the user presses the pay button and calculate the change denominations needed **/
    private void checkIfPayButtonPressed(Button button){
        button.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                /* CHECK IF USER'S INACTIVITY WAS LONGER THAN THRESHOLD */
                if(model.getTimeTracker().checkInactivity() == false){
                    view.timeOut();
                    return;
                }
                model.getTimeTracker().resetLastActivity();  

                //Display the amount paid and the amount of change they need
                Label changeLabel = new Label(String.format("Amount paid =  $%.2f. Change returned =  $%.2f", amountPaid, amountPaid - model.getTransactionHandler().getTotal()));
                changeLabel.setStyle("-fx-text-fill: black; -fx-font-size: 25px;");
                allText.getChildren().add(changeLabel);

                //Calculate the change
                HashMap<Double, Integer> change = changeCalculator.outputChange(model.getTransactionHandler().getTotal(), amountPaid);

                //Check if the correct amount of change can be returned
                if (changeCalculator.getChangeNeeded() != 0) {
                    Label errorChange = new Label("No possible change is available. Insert different cash or cancel transaction.");
                    errorChange.setStyle("-fx-text-fill: red; -fx-font-size: 16px;");
                    allText.getChildren().add(errorChange);
                    model.getTransactionHandler().setChangeAvailability(false);

                    //Create a button to reenter cash
                    Button resetCashButton = new Button("Retrieve cash and insert a different combination");
                    resetCashButton.setStyle("-fx-text-fill: black; -fx-font-size: 17px;");
                    resetCashButton.setBackground(new Background(new BackgroundFill(Color.rgb(184,216,190), null, null)));
                    allText.getChildren().add(resetCashButton);
                    checkResetCashButtonPressed(resetCashButton);
                }

                else {
                    //Print out the calculated change that will be returned
                    for(Double name: change.keySet()) {

                        double key = name;
                        int value = change.get(name);
                        if (change.get(name) > 0) {
                            Label returnedChange = new Label(String.format("%d x $%.2f", value, key));
                            returnedChange.setStyle("-fx-text-fill: black; -fx-font-size: 16px;");
                            allText.getChildren().add(returnedChange);
                        }
                    }

                    //Create a button to complete the transaction
                    Button completeTransactionButton = new Button("Complete transaction");
                    completeTransactionButton.setStyle("-fx-text-fill: black; -fx-font-size: 17px;");
                    completeTransactionButton.setBackground(new Background(new BackgroundFill(Color.rgb(184,216,190), null, null)));

                    allText.getChildren().add(completeTransactionButton);
                    checkCompleteTransactionButtonPressed(completeTransactionButton);
                }

                System.out.println("Pay");

            }
        });
    }

    /** Check if user presses the cancel button **/
    private void checkCancelButtonPressed(){
        this.cancelBtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                //Create an alert informing the user of the cancellation and move them back to the default page
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("TRANSACTION CANCELLED");
                alert.setHeaderText("Your transaction was cancelled. The cart has been abandoned and you have been logged out.");
                alert.show();

                model.getTransactionHandler().cancelTransaction(model.getCurrentUser().getUserName(),
                                                        "user cancelled");

                resetInitPage();
            }
        });
    }

    /** Check if user presses the complete transaction button **/
    private void checkCompleteTransactionButtonPressed(Button button) {
        button.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                /* CHECK IF USER'S INACTIVITY WAS LONGER THAN THRESHOLD */
                if(model.getTimeTracker().checkInactivity() == false){
                    view.timeOut();
                    return;
                }
                model.getTimeTracker().resetLastActivity();

                //Display pop up message on screen
                displayPopUp();

                //Reset current transaction and page.
                model.getTransactionHandler().incrementNotesCoins();
                model.getTransactionHandler().completeTransaction();
                updateLastProducts();
                model.getTransactionHandler().resetCurrentCashInserted();
                model.getChangeCalculator().resetChange();

                resetInitPage();
            }
        });

    }

    /** Display a pop up when the transaction is complete, informing the user of the products and change **/
    public void displayPopUp() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("TRANSACTION COMPLETE");
        alert.setHeaderText("Transaction has been completed.");

        String contentText =  "You received: \n";

        //Add all products to the string that will be displayed
        for (Product product: this.model.getTransactionHandler().getShoppingBag().keySet()){
            contentText += "\t" + product.getProductName() + "\n";
        }

        contentText += String.format("Please collect your items and $%.2f change.", amountPaid - model.getTransactionHandler().getTotal());

        alert.setContentText(contentText);
        model.getTransactionHandler().getShoppingBag();
        alert.show();
    }

    /** Reset the default page after a transaction has been completed or cancelled **/
    public void resetInitPage() {
        model.getTransactionHandler().resetShoppingBag();
        view.getInitPage().getTransactionDisplay().updateProductSelection();
        view.getInitPage().addProductBoxes();
        view.getHeader().removeMenuItems();

        model.setAnonymousUser();
        view.getListOfFive().updateList();
        view.getHeader().changeLoginText();
        view.getHeader().setMenuBarVisibility(true);

        view.changeToInitPage();
    }

    /** Update the last products display on the default page **/
    public void updateLastProducts() {
        for (Product product: model.getTransactionHandler().getShoppingBag().keySet()) {
            model.getCurrentUser().addLastFiveProduct(product);
        }

        for (Product product: model.getCurrentUser().getLastFiveProducts()) {
            System.out.println(product.getProductName());
        }

    }

    /** Reset the cash if the machine cannot return the correct amount of cash **/
    public void checkResetCashButtonPressed(Button button) {
        button.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                /* CHECK IF USER'S INACTIVITY WAS LONGER THAN THRESHOLD */
                if(model.getTimeTracker().checkInactivity() == false){
                    view.timeOut();
                    return;
                }
                model.getTimeTracker().resetLastActivity();  
                model.getTransactionHandler().setChangeAvailability(true);
                
                //Reset the page to reenter new cash
                System.out.println(allText.getChildren().size());
                model.getTransactionHandler().resetCurrentCashInserted();

                //Get rid of the error message if there is one
                if (allText.getChildren().size() > 11) {
                    allText.getChildren().remove(3);
                }
                allText.getChildren().remove(10);
                allText.getChildren().remove(9);
                allText.getChildren().remove(8);
                allText.getChildren().remove(7);

                amountDue = model.getTransactionHandler().getTotal();
                amountPaid = 0;

                Label amountDueLabel = new Label(String.format("Amount due =  $%.2f", amountDue));
                amountDueLabel.setStyle("-fx-text-fill: black; -fx-font-size: 30px;");

                if (allText.getChildren().size() > 6) {
                    allText.getChildren().remove(2);
                }
                allText.getChildren().add(2, amountDueLabel);

                changeCalculator.resetChange();

            }
        });
    }

    /** Returns page as a vertical box **/
    public VBox getPage(){
        return this.paymentPage;
    }
}
