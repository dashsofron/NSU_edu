package UI.admin;

import Database.DBRequests;
import Database.books.models.BookStatus;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class UpdateBookStatusController {
    private final DBRequests dbRequests = DBRequests.getInstance();
    @FXML
    TextField bookIdField;
    @FXML
    ComboBox<BookStatus> newStatus;
    @FXML
    TextArea infoTextArea;
    @FXML
    Button updateBookStatusButton;
    String infoStart = "Нужно ввести или выбрать:\n ";

    public void initController() {
        newStatus.getItems().addAll(
                BookStatus.ВЗЯТО, BookStatus.ВОЗВРАЩЕНО, BookStatus.ГОТОВИТСЯ, BookStatus.ДОСТУПНО, BookStatus.ПОТЕРЯНО);
    }

    @FXML
    public void updateBookStatus() throws SQLException {
        infoTextArea.clear();
        if (bookIdField.getText().equals("") || newStatus.getValue() == null) {
            String info = "";
            if (bookIdField.getText().equals(""))
                info += "Идентификатор книги\n";
            if (newStatus.getValue() == null)
                info += "Новый статус";
            infoTextArea.setText(infoStart + info);
            return;
        }
        try {
            dbRequests.updateBookStatus(Integer.parseInt(bookIdField.getText()), newStatus.getValue(), null);
            infoTextArea.setText("Выполнено");
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

}
