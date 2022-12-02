package assignment_2.view;

import java.util.ArrayList;

import assignment_2.model.Model;
import assignment_2.model.Product;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/** Page which will be displayed below list of 5 recently bought products **/
public class CurrentTransactionDisplay {

    private Screen view;
    private InitPage initPage;

    private VBox displayBox;
    private Label title;
    private HBox titleBox;
    private Model model;
    private VBox allProductsSelected;
    CurrentTransactionDisplay transactionDisplay;

    /** Displays the current transaction shopping cart **/
    public CurrentTransactionDisplay(Model model, Screen view, InitPage initPage){
        this.model = model;
        this.view = view;
        this.initPage = initPage;
        this.init();
    }

    /** Initialises UI elements such as buttons and text fields **/
    private void init(){
        this.transactionDisplay = this;
        this.displayBox = new VBox();
        this.displayBox.setAlignment(Pos.TOP_LEFT);
        this.displayBox.setPadding(new Insets(30, 0, 50, 25));
        this.displayBox.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, 
                            new BorderWidths(1, 0, 0, 0)))); 

        this.title = new Label("Current shopping cart");
        this.title.setStyle("-fx-text-fill: black; -fx-font-size: 30px;");
        this.titleBox = new HBox(this.title);
        this.titleBox.setPadding(new Insets(0, 0, 15, 0));

        this.allProductsSelected = new VBox(5);
        this.displayBox.getChildren().addAll(this.titleBox, this.allProductsSelected);

        this.updateProductSelection();
    }

    /** Updates product selection after products have been bought **/
    public void updateProductSelection(){
        this.allProductsSelected.getChildren().clear();

        /* If no products have been selected yet */
        if (this.model.getTransactionHandler().getShoppingBag().size() == 0){
            Label newLabel = new Label("No products added to chart");
            newLabel.setStyle("-fx-text-fill: red; -fx-font-size: 17px;");
            HBox labelBox = new HBox(newLabel);
            this.allProductsSelected.getChildren().add(labelBox);
        } else {
            /* Traverse through all items in shopping bag and display using labels */
            for (int i = 0; i < this.model.getTransactionHandler().getShoppingBag().size(); i++){
                ArrayList<Product> productKeys = new ArrayList<Product>(this.model.getTransactionHandler().
                                                                                getShoppingBag().keySet());

                Product currentProduct = productKeys.get(i);
                Label newLabel = new Label(String.format("%d x $%.2f: %s", this.model.getTransactionHandler().getShoppingBag().get(currentProduct), currentProduct.getPrice(), currentProduct.getProductName()));

                newLabel.setStyle("-fx-text-fill: black; -fx-font-size: 17px;");
                HBox labelBox = new HBox(newLabel);
                labelBox.setPadding(new Insets(20, 0, 0, 0));
                this.allProductsSelected.getChildren().add(newLabel);
            }

            /* Create a label for the total amount */
            Button totalAmount = new Button();
            totalAmount.setText(String.format("Total amount = $%.2f", this.model.getTransactionHandler().getTotal()));
            totalAmount.setStyle("-fx-text-fill: black; -fx-font-size: 17px;");
            totalAmount.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, null, null)));
            HBox buttonBox = new HBox(totalAmount);
            buttonBox.setPadding(new Insets(10, 0, 0, 0));
            this.allProductsSelected.getChildren().add(buttonBox);

            /* Create a button for proceed to payment */
            Button proceedPaymentBtn = new Button("Proceed to payment");
            proceedPaymentBtn.setStyle("-fx-text-fill: black; -fx-font-size: 17px;");
            proceedPaymentBtn.setBackground(new Background(new BackgroundFill(Color.rgb(184,216,190), null, null)));
            HBox payBox = new HBox(proceedPaymentBtn);
            payBox.setPadding(new Insets(5, 0, 0, 0));
            this.checkIfProceedPaymentPressed(proceedPaymentBtn);
            this.allProductsSelected.getChildren().add(payBox);
        }
    }

    /** Redirects user to payment **/
    public void checkIfProceedPaymentPressed(Button btn){
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Redirect user to new page that let them pay
                view.changeToPaymentPage();
                
                /* CHECK IF USER'S INACTIVITY WAS LONGER THAN THRESHOLD */
                if(model.getTimeTracker().checkInactivity() == false){
                    view.timeOut();
                    return;
                }
                model.getTimeTracker().resetLastActivity();
            }
        });
    }

    /** Returns page as a vertical box **/
    public VBox getDisplay(){
        return this.displayBox;
    }
}