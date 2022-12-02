package assignment_2;

import java.io.IOException;
import java.text.ParseException;
import assignment_2.model.Model;
import assignment_2.view.Screen;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application{
    private Model model;
    private Screen view;

    @Override
    public void start(Stage primaryStage) throws IOException, ParseException {
        this.model = new Model();
        this.view = new Screen(this.model);
        primaryStage.setWidth(1280);
        primaryStage.setHeight(720); 
        primaryStage.setScene(this.view.getScene());
        primaryStage.setTitle("Vending Machine Application - Lite Snacks");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
