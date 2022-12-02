package assignment_2.view;

import java.util.ArrayList;

import assignment_2.model.Model;
import assignment_2.model.Product;
import assignment_2.model.ProductCategory;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/** Page which will be shown on screen upon application start **/
public class InitPage implements Page{
    private Screen view;
    private Model model;
    private CurrentTransactionDisplay transactionDisplay;

    // Separating the init page into two parts: list of 5 last bought products and purchase product
    private VBox initPage;
    private HBox bothSidesOfPages;
    private VBox listSide;
    private VBox purchaseSide;
    private HBox titleBox;

    private Label title = new Label("Make a purchase");
    private Label chooseProductLabel = new Label("Please choose a product");
    private Label chooseQuantityLabel = new Label("Please choose quantity");

    private Menu categoryMenu;
    private Menu productMenu;

    private VBox allProductsBox;

    private Button addToCartBtn;
    private HBox addBtnBox;

    private Product currentProductSelection;

    private Button cancelBtn;
    private HBox cancelBtnBox;

    private ScrollPane scrollPane;
    private Pane pane;

    public InitPage(Screen view, Model model){
        this.model = model;
        this.view = view;
        this.init();
    }

    /** Initialises UI elements such as buttons and text fields **/
    public void init(){

        // Initialise stuff for the purchase side of the page
        this.title.setStyle("-fx-text-fill: black; -fx-font-size: 30px;");
        this.chooseProductLabel.setStyle("-fx-text-fill: black; -fx-font-size: 35px;");
        this.chooseQuantityLabel.setStyle("-fx-text-fill: black; -fx-font-size: 35px;");
        this.productMenu = new Menu("Product Items");

        this.titleBox = new HBox(this.title);
        this.allProductsBox = new VBox();
        this.addProductBoxes();

        this.titleBox.setPadding(new Insets(30, 0, 10, 25));
        this.titleBox.setAlignment(Pos.TOP_LEFT);

        this.addToCartBtn = new Button("Add to Cart");
        this.addBtnBox = new HBox(this.addToCartBtn);
        this.addToCartBtn.setPrefSize(150, 50);
        this.addToCartBtn.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, null, null)));

        this.cancelBtn = new Button("Cancel Transaction");
        this.cancelBtn.setVisible(true);
        this.cancelBtnBox = new HBox(this.cancelBtn);
        this.cancelBtn.setPrefSize(150, 20);
        this.cancelBtn.setBackground(new Background(new BackgroundFill(Color.rgb(255, 127, 127), null, null)));
        cancelBtnBox.setAlignment(Pos.TOP_RIGHT);
        cancelBtnBox.setPadding(new Insets(15, 0,0, 350));
        this.titleBox.getChildren().add(this.cancelBtnBox);
        checkCancelButtonPressed();

        // Add everything to both sides
        this.transactionDisplay = new CurrentTransactionDisplay(this.model, this.view, this);
        this.listSide = new VBox(this.view.getListOfFive().getPage(), this.transactionDisplay.getDisplay());
        this.listSide.setMinWidth(500);
        this.listSide.setMinHeight(600);
        this.listSide.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, 
                            new BorderWidths(0, 1, 0, 0))));   
        VBox.setVgrow(this.listSide, Priority.ALWAYS);
        this.purchaseSide = new VBox(this.titleBox, this.allProductsBox);
        HBox.setHgrow(this.purchaseSide, Priority.ALWAYS);
        this.bothSidesOfPages = new HBox(this.listSide, this.purchaseSide);
        this.initPage = new VBox(this.bothSidesOfPages);
        this.purchaseSide.setMinWidth(760);
        HBox.setHgrow(this.titleBox, Priority.ALWAYS);
        this.initPage.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));

        // Make it a scrollpane
        this.pane = new Pane();
        this.scrollPane = new ScrollPane(this.pane);
        this.scrollPane.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        this.pane.getChildren().add(this.initPage);
    }

    /** Adds boxes containing all products and their categories **/
    public void addProductBoxes(){
        this.allProductsBox.getChildren().clear();

        /* Traverse through all categories and add to box together with their products */
        for (int i = 0; i < this.model.getProductHandler().getAllProductCategories().size(); i++){
            ProductCategory currentCategory = this.model.getProductHandler().getAllProductCategories().get(i);
            VBox categoryBox = new VBox();
            categoryBox.setPadding(new Insets(0, 0, 20, 25));
            categoryBox.setAlignment(Pos.TOP_LEFT);
            HBox addToCartBtnBox = new HBox();
            ArrayList<Button> allCategoryQuantities = new ArrayList<Button>();
            ArrayList<Product> allCategoryProducts = new ArrayList<Product>();

            // Create category label
            Text categoryLabel = new Text(currentCategory.getCategoryName());
            categoryLabel.setFont(Font.font("Courier New", FontWeight.BOLD, FontPosture.ITALIC, 20));
            categoryLabel.setUnderline(true);
            categoryBox.getChildren().add(categoryLabel);

            /* Traverse through all products and add to vertical box */
            for (int j = 0; j < currentCategory.getAllProducts().size(); j++){
                Product currentProduct = currentCategory.getAllProducts().get(j);
                Text productLabel = new Text("$" + String.format("%.2f", currentProduct.getPrice()) + " " + currentProduct.getProductName() + " (" + currentProduct.getQuantityAvailable() + " in stock)");
                productLabel.setStyle("-fx-text-fill: black; -fx-font-size: 16px;");

                // Add plus and minus buttons
                Button quantityAdded = new Button(" ");
                Button plusButton = new Button("+");
                Button minusButton = new Button("-");
                HBox quantityButtonBox = new HBox(quantityAdded);
                allCategoryQuantities.add(quantityAdded);
                allCategoryProducts.add(currentProduct);
                quantityAdded.setPrefWidth(30);
                quantityAdded.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, null, null)));
                plusButton.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
                plusButton.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, BorderWidths.DEFAULT)));
                minusButton.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
                minusButton.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
                minusButton.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, BorderWidths.DEFAULT)));

                // Check if the buttons have been pressed
                this.checkAddQuantity(currentProduct, plusButton, quantityAdded, categoryBox, addToCartBtnBox, 
                                                                    allCategoryQuantities, allCategoryProducts);
                this.checkReduceQuantity(currentProduct, minusButton, quantityAdded, categoryBox, addToCartBtnBox);

                // Add to horizontal box
                HBox productBox = new HBox(5);
                productBox.getChildren().addAll(quantityButtonBox, plusButton, minusButton, productLabel);
                productBox.setAlignment(Pos.TOP_LEFT);
                productBox.setPadding(new Insets(5, 0, 5, 0));
                categoryBox.getChildren().addAll(productBox);
            }
            categoryBox.getChildren().addAll(addToCartBtnBox);
            this.allProductsBox.getChildren().add(categoryBox);
        }
    }

    /** EventHandler which checks if "plus" button on a product has been pressed **/
    private void checkAddQuantity(Product product, Button plusButton, Button currentQuantityBtn, 
                                            VBox categoryBox, HBox addToCartBtnBox, ArrayList<Button> allCategoryQuantities, 
                                            ArrayList<Product> allCategoryProducts){
        plusButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                /* CHECK IF USER'S INACTIVITY WAS LONGER THAN THRESHOLD */
                if(model.getTimeTracker().checkInactivity() == false){
                    view.timeOut();
                    return;
                }
                model.getTimeTracker().resetLastActivity();  

                // If we have enough items in stock, increase quantity
                if (product.getTempQuantity() < product.getQuantityAvailable()){

                    // If currently no items added, set to 1
                    if (currentQuantityBtn.getText().equals(" ")){
                        currentQuantityBtn.setText("1");
                    
                    // Otherwise, convert to Integer then back to string
                    } else {
                        String currentQuantityStr = currentQuantityBtn.getText();
                        int currentQuant = Integer.valueOf(currentQuantityStr);
                        currentQuantityBtn.setText(String.valueOf(currentQuant + 1));
                    }
                    product.increaseTempQuantity();

                    // Also update/create button at the bottom of category box to add to chart
                    addToCartBtnBox.getChildren().clear();
                    Button addButton = new Button("Add to cart");
                    addButton.setBackground(new Background(new BackgroundFill(Color.rgb(184,216,190), null, null)));
                    addButton.setPrefWidth(95);
                    addToCartBtnBox.getChildren().add(addButton);

                    // Check if button is pressed
                    checkAddCartPressed(product, currentQuantityBtn, addButton, addToCartBtnBox, allCategoryQuantities, allCategoryProducts);

                // Cannot add more times if we currently do not have that many in stock
                } else {

                }
            }
        });
    }

    /** EventHandler which checks if "minus" button on product has been pressed **/
    private void checkReduceQuantity(Product product, Button minusButton, Button currentQuantityBtn, 
                                                    VBox categoryBox, HBox addToCartBtnBox){
        minusButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                /* CHECK IF USER'S INACTIVITY WAS LONGER THAN THRESHOLD */
                if(model.getTimeTracker().checkInactivity() == false){
                    view.timeOut();
                    return;
                }
                model.getTimeTracker().resetLastActivity();  

                // Reduce quantity
                if (product.getTempQuantity() > 0 && !currentQuantityBtn.getText().equals(" ")){
                    String currentQuantityStr = currentQuantityBtn.getText();
                    int currentQuant = Integer.valueOf(currentQuantityStr);
                    currentQuantityBtn.setText(String.valueOf(currentQuant - 1));
                    product.reduceTempQuantity();

                    // If quantity is now 0, get rid of text and addToCartBtn
                    if (product.getTempQuantity() == 0 || currentQuantityBtn.getText().equals("0")){
                        currentQuantityBtn.setText(" ");
                        addToCartBtnBox.getChildren().clear();
                    }
                }
            }
        });
    }

    /** Check if user adds items to shopping bag **/
    private void checkAddCartPressed(Product product, Button quantityBtn, Button addToCartBtn, HBox addToCartBtnBox,
                                        ArrayList<Button> allQuantityButtons, ArrayList<Product> allCategoryProducts){
        addToCartBtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                /* CHECK IF USER'S INACTIVITY WAS LONGER THAN THRESHOLD */
                if(model.getTimeTracker().checkInactivity() == false){
                    view.timeOut();
                    return;
                }
                model.getTimeTracker().resetLastActivity();  

                // Initialise quantity again 
                for (int i = 0; i < allQuantityButtons.size(); i++){
                    if (!allQuantityButtons.get(i).getText().equals(" ")){
                        Integer quantityAdded = Integer.valueOf(allQuantityButtons.get(i).getText());
                        model.getTransactionHandler().addToShoppingBag(allCategoryProducts.get(i), quantityAdded);
                        allQuantityButtons.get(i).setText(" ");
                    }
                }
                transactionDisplay.updateProductSelection();
                addProductBoxes();
                displaySuccessfullyAdded(quantityBtn.getText(), addToCartBtnBox);
            }
        });
    }

    /** Creates a label that will be displayed when a transaction was successful **/
    private void displaySuccessfullyAdded(String quantityString, HBox addToCartBox){
        Label success = new Label(String.format("Successfully added items to shopping cart"));
        success.setStyle("-fx-text-fill: green; -fx-font-size: 17px;");
        HBox successBox = new HBox(success);
        successBox.setAlignment(Pos.BOTTOM_LEFT);
        addToCartBox.getChildren().clear();
        addToCartBox.getChildren().add(successBox);
    }

    /** Check if user presses the cancel button **/
    private void checkCancelButtonPressed(){
        this.cancelBtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                /* CHECK IF USER'S INACTIVITY WAS LONGER THAN THRESHOLD */
                if(model.getTimeTracker().checkInactivity() == false){
                    view.timeOut();
                    return;
                }
                model.getTimeTracker().resetLastActivity();

                // Display pop up message on screen
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("TRANSACTION CANCELLED");
                alert.setHeaderText("Transaction has been cancelled and user has been logged out.");
                alert.show();

                model.getTransactionHandler().cancelTransaction(model.getCurrentUser().getUserName(),
                                                        "user cancelled");

                // Log out and reset current shopping cart
                model.getTransactionHandler().resetShoppingBag();
                view.getHeader().removeMenuItems();
                model.setUser(model.getAnonymousUser());
                view.getHeader().changeLoginText();

                // Reset page
                transactionDisplay.updateProductSelection();
                addProductBoxes();

            }
        });
    }

    /** Returns transaction display **/
    public CurrentTransactionDisplay getTransactionDisplay() {
        return this.transactionDisplay;
    }

    /** Returns page as a vertical box **/
    public VBox getPage(){
        return this.initPage;
    }

    /** Returns page as a scrollpane **/
    public ScrollPane getScrollPane(){
        return this.scrollPane;
    }
}
