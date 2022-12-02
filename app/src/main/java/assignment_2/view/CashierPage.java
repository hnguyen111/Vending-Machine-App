package assignment_2.view;

import assignment_2.model.Model;
import assignment_2.model.Cash;
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

/** Manages the cashier page where the cashier can modify quantity of cash **/
public class CashierPage implements Page{
    private Screen view;
    private Model model;
    private VBox cashierPage;
    private VBox titleBox;

    private Label title = new Label("Modify Quantity Of Coins/Notes");

    private Button updateBtn;
    private VBox updateBtnBox;

    private Menu cashMenu;
    private MenuBar cashMenuBar;
    private HBox cashMenuBox;

    private Label cashLabel;
    private HBox cashBox;

    private Label quantity;
    private TextField quantityTextField;
    private HBox quantityBox;

    private Label outputMessage;

    private String type;

    boolean error;

    /** Allows cashier and owner to change the amount of cash that exists in the machine **/
    public CashierPage(Screen view, Model model){
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

        this.cashBox = new HBox();
        this.quantityBox = new HBox();

        this.updateBtn = new Button("Update");
        this.updateBtnBox = new VBox(this.updateBtn);
        this.updateBtn.setPrefSize(150, 20);
        this.updateBtn.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, null, null)));
        updateBtnBox.setAlignment(Pos.CENTER);
        updateBtnBox.setPadding(new Insets(0, 40, 0, 160));
        checkUpdateButtonPressed();

        this.outputMessage = new Label("");
        this.outputMessage.setVisible(false);

        this.cashierPage = new VBox(this.cashBox, this.quantityBox, this.updateBtnBox, this.outputMessage);

        this.cashierPage.setAlignment(Pos.CENTER);
        
        HBox.setHgrow(this.titleBox, Priority.ALWAYS);
        this.cashierPage.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        this.refreshPage();
    }

    /** Refreshes the page and gets rid of user input **/
    public void refreshPage(){
        this.cashBox.getChildren().clear();
        this.quantityBox.getChildren().clear();

        createCashMenu();
        this.cashMenuBox = new HBox(this.cashMenuBar);
        this.cashLabel = new Label("Note/Coin           ");
        this.cashBox.getChildren().addAll(this.cashLabel, this.cashMenuBox);
        this.cashBox.setAlignment(Pos.TOP_CENTER);

        this.quantity = new Label("Quantity  ");
        this.quantityTextField = new TextField("");
        this.quantityBox.getChildren().addAll(this.quantity, this.quantityTextField);

        this.quantityBox.setPadding(new Insets(20));
        this.quantityBox.setAlignment(Pos.CENTER);

        this.error = false;
        this.type = null;
    }

    /** Creates a menu containing all cash options **/
    private void createCashMenu(){
        this.cashMenu = new Menu("  Notes/Coins  ");
        this.cashMenuBar = new MenuBar(this.cashMenu);
        String[] cash = {"$100", "$50", "$20", "$10", "$5", "$2", "$1", "50c", "20c", "10c", "5c"};
        for (int i = 0; i < cash.length; i++){
            cash[i] = String.format(cash[i] + " : " + model.getCashHandler().getQuantity()[i]);
            MenuItem newItem = new MenuItem(cash[i]);
            this.cashMenu.getItems().add(newItem);
            this.checkCashTypePressed(newItem);
        }   
    }

    /** EventHandler which checks if "cash type" button has been pressed **/
    private void checkCashTypePressed(MenuItem item){
        item.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                /* CHECK IF USER'S INACTIVITY WAS LONGER THAN THRESHOLD */
                if(model.getTimeTracker().checkInactivity() == false){
                    view.timeOut();
                    return;
                } 
                model.getTimeTracker().resetLastActivity();  

                type = item.getText();
                cashMenu.setText(type);
                outputMessage.setText("");                        
            }   
        });
    }

    /** Eventhandler checking if "update" button has been pressed **/
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
                
                outputMessage.setVisible(true);
                Cash handler = model.getCashHandler();                
                String quantity = quantityTextField.getText();
                int q = 0;

                error = false;
                if (type == null){
                    outputMessage.setText("Choose one type of cash");
                    error = true;
                } else if (quantity.equals("")){
                    outputMessage.setText("Quantity is required");
                    error = true;
                } else if (!quantity.equals("")){
                    try {
                        q = Integer.parseInt(quantity);
                        if (q < 0){
                            outputMessage.setText("Invalid quantity: cannot be negative");
                            error = true;
                        }
                    } catch (NumberFormatException e){
                        outputMessage.setText("Invalid quantity: needs to be an integer");
                        error = true;
                    }
                }

                if(!error){
                    outputMessage.setText("Updated");

                    // coins
                    if (type.charAt(0) == '5'){
                        if (type.charAt(1) == 'c')
                            handler.set5cQuantity(q);
                        else
                            handler.set50cQuantity(q);
                    }
                    else if (type.charAt(0) == '1')
                        handler.set10cQuantity(q);
                    else if (type.charAt(0) == '2')
                        handler.set20cQuantity(q);
                        
                    // notes    
                    else if (type.charAt(1) == '1'){
                        if (type.charAt(2) == ' ')
                            handler.set1DollarQuantity(q);
                        else if (type.charAt(3) == ' ')
                            handler.set10DollarQuantity(q);
                        else
                            handler.set100DollarQuantity(q); 
                    }                   
                    else if (type.charAt(1) == '2'){
                        if (type.charAt(2) == ' ')
                            handler.set2DollarQuantity(q);
                        else
                            handler.set20DollarQuantity(q);
                    }
                    else if (type.charAt(1) == '5'){
                        if (type.charAt(2) == ' ')
                            handler.set5DollarQuantity(q);
                        else
                            handler.set50DollarQuantity(q);
                    }
                    refreshPage();
                } 
            }
        });
    }

    /** Returns page as a vertical box **/
    public VBox getPage(){
        return this.cashierPage;
    }
    
}
