package UI.books;

import Database.DBRequests;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class DeleteBookController {
    private final DBRequests dbRequests = DBRequests.getInstance();
    @FXML
    Button deleteButton;
    @FXML
    TextField idField;
    @FXML
    TextArea infoArea;
    String info = "Нужно ввести идентификатор книги:\n ";

    @FXML
    public void deleteBook() {
        if (idField.getText().equals("")) {
            infoArea.setText(info);
            return;
        }
//        try {
            dbRequests.deleteBook(Integer.parseInt(idField.getText()));
            infoArea.setText("Выполнено");
//        } catch (Exception ignore) {
//        }
    }
}
