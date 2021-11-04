package UI.punishments;

import Database.DBRequests;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class DeletePunishmentController {
    private final DBRequests dbRequests = DBRequests.getInstance();
    @FXML
    TextField idField;
    @FXML
    Button deleteButton;
    @FXML
    TextArea infoArea;
    String info = "Нужно ввести идентификатор книги:\n ";


    @FXML
    public void deletePunishment() {
        if (idField.getText().equals("")) {
            infoArea.setText(info);
            return;
        }
//        try {
            dbRequests.deletePunishment(Integer.valueOf(idField.getText()));
            infoArea.setText("Выполнено");
//        } catch (Exception ignore) {
//        }
    }
}
