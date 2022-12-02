package assignment_2.view;

import assignment_2.model.Model;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

/** Page containing list of last five products bought by the user currently logged in **/
public class ListOfFive implements Page{
    private Screen view;
    private Model model;
    private VBox page;
    private VBox titleBox;
    private VBox noProductsBox;
    private VBox listOfProductsBox;

    private Label title = new Label("Last 5 products bought");
    private Label noProductsBought = new Label("No purchase history");

    /** List of five recently bought products **/
    public ListOfFive(Screen view, Model model){
        this.model = model;
        this.view = view;
        this.init();
    }

    /** Initialises UI elements such as buttons and text fields **/
    public void init(){
        this.title.setStyle("-fx-text-fill: black; -fx-font-size: 30px;");
        this.noProductsBought.setStyle("-fx-text-fill: red; -fx-font-size: 17px;");
        this.titleBox = new VBox(this.title);
        this.titleBox.setAlignment(Pos.TOP_LEFT);
        this.titleBox.setPadding(new Insets(30, 100, 15, 25));

        this.noProductsBox = new VBox(this.noProductsBought);
        this.noProductsBox.setAlignment(Pos.TOP_LEFT);
        this.noProductsBought.setPadding(new Insets(0, 0, 50, 25));

        this.listOfProductsBox = new VBox();
        
        this.page = new VBox(this.titleBox);
        this.page.setMinHeight(250);
        this.page.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        this.updateList();
    }

    /** Update list if a new user logs in **/
    public void updateList(){

        /* First, clear old list */
        this.page.getChildren().clear();
        this.page.getChildren().add(this.titleBox);
        this.listOfProductsBox.getChildren().clear();

        /* If list of 5 products is empty, display message */
        if (this.model.getCurrentUser().getLastFiveProducts().size() == 0){
            this.page.getChildren().add(this.noProductsBox);

        /* Otherwise, display last 5 boughts items */
        } else {
            String labelText = "";
            for (int i = 0; i < this.model.getCurrentUser().getLastFiveProducts().size(); i++){
                String productName = this.model.getCurrentUser().getLastFiveProducts().get(i).getProductName();
                String category = this.model.getCurrentUser().getLastFiveProducts().get(i).getProductCategory().getCategoryName();
                labelText += String.format("%s (%s)\n", productName, category.substring(0, category.length() - 1));
            }
            Label productLabel = new Label(labelText);
            productLabel.setTextAlignment(TextAlignment.LEFT);
            productLabel.setStyle("-fx-text-fill: black; -fx-font-size: 17px;");
            this.listOfProductsBox.getChildren().add(productLabel);
            this.listOfProductsBox.setAlignment(Pos.TOP_LEFT);
            this.listOfProductsBox.setPadding(new Insets(0, 0, 20, 25));
            this.page.getChildren().add(this.listOfProductsBox);
        }
    }

    /** Returns page as a vertical box **/
    public VBox getPage(){
        return this.page;
    }
    
}
