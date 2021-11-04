package UI;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class ErrorController {
    @FXML
    TextArea errorInfo;

    public void setError(String error){
        errorInfo.setText(error);
    }
}
