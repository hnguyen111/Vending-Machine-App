package assignment_2.view;

import assignment_2.model.Model;
import assignment_2.model.ProductHandler;
import assignment_2.model.Product;
import assignment_2.model.ProductCategory;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.print.PrintColor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.control.TextField;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

/** Page displayed when a seller is modifying item details **/
public class SellerPage implements Page{
    private Screen view;
    private Model model;
    private VBox sellerPage;
    private VBox titleBox;

    private Label title = new Label("Modify Item Details");

    private Button updateBtn;
    private VBox updateBtnBox;

    private Label productCodeLabel;
    private Label name;
    private Label category;
    private Label price;
    private Label quantity;
    private Label outputMessage;

    private TextField nameTextField;
    private Menu categoryMenu;
    private MenuBar categoryMenuBar;
    private HBox categoryBox;
    private HBox categoryMenuBox;
    private TextField priceTextField;
    private TextField quantityTextField;
    private TextField codeTextField;

    private Menu codeMenu;
    private MenuBar codeMenuBar;
    private VBox codeMenuBox;
    private HBox nameBox;
    private HBox priceBox;
    private HBox quantityBox;
    private HBox productCodeBox;

    private Product selectedProduct;

    private Label codeLabel;
    private VBox codeBox;
    private String code;
    private String categoryChosen;
    private int modifiedQuantity;

    public SellerPage(Screen view, Model model){
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
        
        this.updateBtn = new Button("Update");
        this.updateBtnBox = new VBox(this.updateBtn);
        this.updateBtn.setPrefSize(150, 20);
        this.updateBtn.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, null, null)));
        updateBtnBox.setAlignment(Pos.CENTER);
        updateBtnBox.setPadding(new Insets(0, 0, 0, 0));
        checkUpdateButtonPressed();

        this.productCodeLabel = new Label("Product code");
        productCodeLabel.setPadding(new Insets(0, 20, 0, 0));
        this.name = new Label("Product name");
        name.setPadding(new Insets(0, 20, 0, 0));
        this.category = new Label("Product category");
        category.setPadding(new Insets(0, 23, 0, 0));
        this.price = new Label("Product price");
        price.setPadding(new Insets(0, 20, 0, 0));
        this.quantity = new Label("Product quantity");
        quantity.setPadding(new Insets(0, 15, 0, 0));

        this.nameTextField = new TextField("");
        this.priceTextField = new TextField("");
        this.quantityTextField = new TextField("");
        this.codeTextField = new TextField("");

        createCodeMenu();
        this.codeMenuBox = new VBox(this.codeMenuBar);
        this.codeLabel = new Label("Select a product to modify");
        this.codeLabel.setStyle("-fx-text-fill: black; -fx-font-size: 30px;");
        this.codeBox = new VBox(this.codeLabel, this.codeMenuBox);
        this.codeBox.setAlignment(Pos.CENTER);
        this.codeMenuBox.setMaxWidth(150);
        this.codeBox.setPadding(new Insets(10, 0, 30, 0));

        this.nameBox = new HBox(this.name, this.nameTextField);
        this.priceBox = new HBox(this.price, this.priceTextField);
        this.quantityBox = new HBox(this.quantity, this.quantityTextField);
        this.productCodeBox = new HBox(this.productCodeLabel, this.codeTextField);
        this.categoryBox = new HBox(this.category);

        this.createCategoryMenu();
        this.outputMessage = new Label("");
        this.outputMessage.setVisible(false);

        this.sellerPage = new VBox(this.codeBox, this.productCodeBox, this.nameBox, this.categoryBox,
            this.priceBox, this.quantityBox, this.updateBtnBox, this.outputMessage);

        this.nameBox.setPadding(new Insets(15));
        this.nameBox.setAlignment(Pos.CENTER);
        this.categoryBox.setPadding(new Insets(15));
        this.categoryBox.setAlignment(Pos.CENTER);
        this.productCodeBox.setPadding(new Insets(15));
        this.productCodeBox.setAlignment(Pos.CENTER);
        this.priceBox.setPadding(new Insets(15));
        this.priceBox.setAlignment(Pos.CENTER);
        this.quantityBox.setPadding(new Insets(15));
        this.quantityBox.setAlignment(Pos.CENTER);
        this.sellerPage.setAlignment(Pos.CENTER);
        
        this.sellerPage.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
    }

    /** Creates menu containing categories **/
    private void createCategoryMenu(){
        this.categoryMenu = new Menu("Select category");
        this.categoryMenuBar = new MenuBar(this.categoryMenu);

        if (this.categoryBox.getChildren().size() > 1){
            this.categoryBox.getChildren().remove(this.categoryBox.getChildren().size() - 1);
        }
        this.categoryBox.getChildren().add(this.categoryMenuBar);

        for (ProductCategory p : model.getProductHandler().getAllProductCategories()){
            MenuItem newItem = new MenuItem(p.getCategoryName());
            this.categoryMenu.getItems().add(newItem);
            this.checkCategoryChosen(newItem);
        }  
    }

    /** EventHandler checking if category has been chosen **/
    private void checkCategoryChosen(MenuItem item){
        item.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                /* CHECK IF USER'S INACTIVITY WAS LONGER THAN THRESHOLD */
                if(model.getTimeTracker().checkInactivity() == false){
                    view.timeOut();
                    return;
                }
                model.getTimeTracker().resetLastActivity();  
                outputMessage.setText("");

                categoryChosen = item.getText();
                categoryMenu.setText(categoryChosen);                            
            }   
        });
    }

    /** Set category as current selected product's category **/
    private void setCategory(){
        this.categoryMenu.setText(this.selectedProduct.getProductCategory().getCategoryName());
    }

    /** Creates a menu containing product codes **/
    private void createCodeMenu(){
        this.codeMenu = new Menu("Select product");
        this.codeMenuBar = new MenuBar(this.codeMenu);
        
        for (Product p : model.getProductHandler().getAllProducts()){
            MenuItem newItem = new MenuItem(p.getProductCode() + ": " + p.getProductName());
            this.codeMenu.getItems().add(newItem);
            this.checkCodePressed(newItem);
        }   
    }

    /** EventHandler checking if product codes have been pressed **/
    private void checkCodePressed(MenuItem item){
        item.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                /* CHECK IF USER'S INACTIVITY WAS LONGER THAN THRESHOLD */
                if(model.getTimeTracker().checkInactivity() == false){
                    view.timeOut();
                    return;
                }
                model.getTimeTracker().resetLastActivity();  
                outputMessage.setText("");

                // Find the product
                String productCode = item.getText().substring(0, 4);
                System.out.println(productCode);
                selectedProduct = model.getProductHandler().getProductByCode(productCode);

                // Set prompt text to see what the name, price etc used to be
                nameTextField.setText(selectedProduct.getProductName());
                priceTextField.setText(String.format("%.2f", selectedProduct.getPrice()).replace(',', '.'));
                quantityTextField.setText(String.format("%d", selectedProduct.getQuantityAvailable()));
                codeTextField.setText(selectedProduct.getProductCode());
                modifiedQuantity = selectedProduct.getQuantityAvailable();
                categoryChosen = selectedProduct.getProductCategory().getCategoryName();
                setCategory();
                
                code = item.getText();
                codeMenu.setText(code);                            
            }   
        });
    }

    /** EventHandler checking if "update" button has been pressed **/
    public void checkUpdateButtonPressed(){
        this.updateBtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                /* CHECK IF USER'S INACTIVITY WAS LONGER THAN THRESHOLD */
                if(model.getTimeTracker().checkInactivity() == false){
                    view.timeOut();
                    return;
                }
                model.getTimeTracker().resetLastActivity();  

                ProductHandler handler = model.getProductHandler();
                outputMessage.setVisible(true);
                String name = nameTextField.getText().toLowerCase();
                String category = categoryMenu.getText().toLowerCase();
                String price = priceTextField.getText().toLowerCase();
                double p = 0;
                String quantity = quantityTextField.getText();
                if (quantity != null){
                    modifiedQuantity = Integer.parseInt(quantity);
                }
                
                int q = 0;
                boolean error = false;

                /* Check if code is in correct format (must be only 4 digits) */
                if (codeTextField.getText().length() != 4){
                    outputMessage.setText("Invalid code: Length must be 4");
                    error = true;
                
                /* Check if code already exists */
                } else {
                    if (model.getProductHandler().getProductByCode(codeTextField.getText()) != null &&
                        !selectedProduct.getProductCode().equals(codeTextField.getText())){
                            outputMessage.setText("Invalid code: Already taken. Please choose a different code");
                            error = true;
                    }
                }
                
                if (code == null || codeMenu.getText().equals("Select product")){
                    outputMessage.setText("Choose a product to modify from the menu");
                    error = true;
                } else if (nameTextField.getText().equals("")){
                    outputMessage.setText("Please enter a product name");
                    error = true;
                } else if (quantityTextField.getText().equals("")){
                    outputMessage.setText("Please enter a quantity");
                    error = true;
                } else if (priceTextField.getText().equals("")){
                    outputMessage.setText("Please enter a price");
                    error = true;
                }

                /* Check if product already exists */
                boolean exists = model.getProductHandler().checkProductNameExists(nameTextField.getText(), categoryMenu.getText());
                if (exists && (!selectedProduct.getProductName().toLowerCase().equals(nameTextField.getText().toLowerCase()) ||
                                !selectedProduct.getProductCategory().getCategoryName().toLowerCase().equals(categoryChosen.toLowerCase()))){

                                    outputMessage.setText("Invalid: this product already exists");
                                    error = true;
                    }

                if (!price.equals("")){
                    try {
                        p = Double.parseDouble(price);
                        if (p < 1){
                            outputMessage.setText("Invalid price: needs to be above 0");
                            error = true;
                        } else if (p > Integer.MAX_VALUE){
                            outputMessage.setText("Invalid price: cannot be above 2147483647");
                            error = true;
                        }
                    } catch (NumberFormatException e){
                        outputMessage.setText("Invalid price: needs to be a number");
                        error = true;
                    }
                } 
                if (modifiedQuantity < 0){
                            outputMessage.setText("Invalid quantity: cannot be negative");
                            error = true;
                }
                else if (modifiedQuantity > 15){
                    outputMessage.setText("Invalid quantity: cannot be greater than 15");
                    error = true;
                }
                if(!error){
                    outputMessage.setText("Updated the product details");
                    handler.modifyProductDetails(code.substring(0, 4), codeTextField.getText(), nameTextField.getText(), category, p, modifiedQuantity);                   
                    resetPage();
                }             
            }
        });
    }

    /** Resets the page and clears user input **/
    public void resetPage(){
        this.codeMenuBox.getChildren().clear();
        this.createCodeMenu();
        this.codeMenuBox.getChildren().add(this.codeMenuBar);
        nameTextField.clear();
        priceTextField.clear();
        quantityTextField.clear();
        codeTextField.clear();
        this.categoryMenu.setText("Select category");
    }

    /** Returns page as a vertical box **/
    public VBox getPage(){
        return this.sellerPage;
    }
}
