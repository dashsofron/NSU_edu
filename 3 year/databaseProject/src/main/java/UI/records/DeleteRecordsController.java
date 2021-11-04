package UI.records;

import Database.DBRequests;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class DeleteRecordsController {
    @FXML
    Button deleteRecordButton;
    @FXML
    TextField idField;
    @FXML
    TextArea infoArea;
    String info = "Нужно ввести идентификатор книги:\n ";

    private final DBRequests dbRequests = DBRequests.getInstance();

    @FXML
    public void deleteRecord(){
        if (idField.getText().equals("")) {
            infoArea.setText(info);
            return;
        }
//        try {
            dbRequests.deleteRecord(Integer.valueOf(idField.getText()));
            infoArea.setText("Выполнено");
//        } catch (Exception ignore) {
//        }
    }
}
