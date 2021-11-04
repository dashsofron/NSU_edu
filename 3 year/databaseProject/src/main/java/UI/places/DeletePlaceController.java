package UI.places;

import Database.DBRequests;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class DeletePlaceController {

    private final DBRequests dbRequests = DBRequests.getInstance();
    @FXML
    TextField idField;
    @FXML
    TextArea infoArea;
    String info = "Нужно ввести идентификатор места:\n ";

    @FXML
    public void delete() {
        if (idField.getText().equals("")) {
            infoArea.setText(info);
            return;
        }
//        try {
            dbRequests.deletePlace(Integer.parseInt(idField.getText()));
            infoArea.setText("Выполнено");
//        } catch (Exception ignore) {
//        }
    }
}
