package assignment_2.view;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/** Interface used for all pages displayed on the screen **/
public interface Page {

    public void init() throws FileNotFoundException, IOException, ParseException;
    public VBox getPage();
}
