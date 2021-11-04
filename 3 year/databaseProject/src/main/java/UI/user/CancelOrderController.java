package UI.user;

import Database.DBRequests;
import Database.books.models.Book;
import Database.books.models.BookStatus;
import Database.files.models.BookTakingRecord;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class CancelOrderController {
    private final DBRequests dbRequests = DBRequests.getInstance();
    @FXML
    TextField bookIdField;
    @FXML
    Button cancelOrderButton;
    @FXML
    TextArea infoArea;
    public Integer userId;
public void init(Integer userId){
    this.userId=userId;

}
    @FXML
    public void cancelOrder() throws SQLException {
        dbRequests.updateBookStatus(Integer.parseInt(bookIdField.getText()),BookStatus.ВОЗВРАЩЕНО,userId);
        infoArea.setText("Выполнено");

    }
}
