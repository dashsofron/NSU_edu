package UI.readers;

import Database.DBRequests;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class DeleteReaderController {
    private final DBRequests dbRequests = DBRequests.getInstance();
    @FXML
    TextField readerIdField;
    @FXML
    TextArea infoArea;
    @FXML
    Button deleteRederButton;
    String info = "Нужно ввести идентификатор книги:\n ";

    @FXML
    public void deleteReader() {
        infoArea.clear();
        if (readerIdField.getText().equals("")) {
            infoArea.setText(info);
            return;
        }
        try {
            dbRequests.deleteReader(Integer.valueOf(readerIdField.getText()));
            infoArea.setText("Выполнено");
        } catch (Exception ignore) {
        }
    }
}
