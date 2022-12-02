package assignment_2.view;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

import assignment_2.model.Model;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

/** Page displayed when a Seller, Cashier or Owner chooses to generate reports **/
public class ReportPage implements Page{

    private VBox reportPage;
    private Screen view;
    private Model model;

    private Button generateButton;
    private HBox btnBox;

    private Label titleLabel;
    private VBox labelBox;


    /** Generates the report page **/
    public ReportPage(Screen view, Model model) throws FileNotFoundException, IOException, ParseException{
        this.view = view;
        this.model = model;
        this.init();
    }

    /** Initialises UI elements such as buttons and text fields **/
    @Override
    public void init() throws FileNotFoundException, IOException, ParseException {
        this.reportPage = new VBox();
        this.generateButton = new Button("Generate Reports");
        this.generateButton.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, null, null)));
        this.btnBox = new HBox(this.generateButton);
        HBox.setHgrow(this.btnBox, Priority.ALWAYS);
        this.btnBox.setAlignment(Pos.TOP_CENTER);
        this.btnBox.setPadding(new Insets(50));

        this.labelBox = new VBox();
        labelBox.setAlignment(Pos.TOP_CENTER);
        HBox.setHgrow(labelBox, Priority.ALWAYS);
        this.titleLabel = new Label("The following reports have been generated:");
        this.titleLabel.setStyle("-fx-text-fill: black; -fx-font-size: 23px;");

        // Check if button is being pressed
        this.checkBtnPress();
        this.reportPage.getChildren().addAll(this.btnBox, this.labelBox);
    }

    /** EventHandler checking if "generate report" button has been pressed **/
    private void checkBtnPress(){
        this.generateButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                labelBox.getChildren().clear();
                labelBox.getChildren().add(titleLabel);
                try {

                    /* If user is seller, generate seller reports */
                    if (model.getCurrentUser().getUserName().equals("seller")){
                        model.getReportGenerator().sellerReports();

                        // Add one label for each report generated
                        Label newLabel = new Label("availableItems.txt (containing all available products)\n" +
                        "soldItems.txt (containing product details including number of sold items)");
                        newLabel.setTextAlignment(TextAlignment.CENTER);
                        newLabel.setStyle("-fx-text-fill: black; -fx-font-size: 15px;");
                        labelBox.getChildren().addAll(newLabel);
                    
                    /* If user is owner, generate owner reports */
                    } else if (model.getCurrentUser().getUserName().equals("owner")){
                        model.getReportGenerator().ownerReports();

                         // Add one label for each report generated
                        Label newLabel = new Label("availableItems.txt (containing all available products)\n" +
                        "soldItems.txt (containing product details including number of sold items)\n" +
                        "availableChange.txt (containing list of available change)\n" +
                        "transactionTimes.txt (containing transaction details including date and time)\n" +
                        "users.txt (containing all current users)\n" +
                        "cancelledTransactions.txt (containing cancelled transactions details)");
                        newLabel.setTextAlignment(TextAlignment.CENTER);
                        newLabel.setStyle("-fx-text-fill: black; -fx-font-size: 15px;");
                        labelBox.getChildren().add(newLabel);


                    /* If user is cashier, generate cashier reports */
                    } else if (model.getCurrentUser().getUserName().equals("cashier")){
                        model.getReportGenerator().cashierReports();

                        // Add one label for each report generated
                        Label newLabel = new Label("availableChange.txt (containing list of available change)\n" +
                        "transactionTimes.txt (containing transaction details including date and time)");
                        newLabel.setTextAlignment(TextAlignment.CENTER);
                        newLabel.setStyle("-fx-text-fill: black; -fx-font-size: 15px;");
                        labelBox.getChildren().addAll(newLabel);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                                
            }
            
        });
    }

    /** Returns page as a vertical box **/
    @Override
    public VBox getPage() {
        return this.reportPage;
    }
    
}
