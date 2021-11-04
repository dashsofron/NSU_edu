package UI.user;

import Database.DBRequests;
import Database.books.models.Book;
import Database.files.models.BookTakingRecord;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.sql.Date;

public class ContinueOrderController {
    @FXML
    TextField bookIdField;
    @FXML
    Button prolongButton;
    @FXML
    TextArea infoArea;
    @FXML
    DatePicker returningDateField;
    private final DBRequests dbRequests = DBRequests.getInstance();

    @FXML
    public void prolong() {
        BookTakingRecord record=dbRequests.getRecordByBookId(Integer.parseInt(bookIdField.getText()));
        record.setDateOfReturning(Date.valueOf(returningDateField.getValue()));
        dbRequests.updateRecord(record);
        infoArea.setText("Выполнено");


    }
}
