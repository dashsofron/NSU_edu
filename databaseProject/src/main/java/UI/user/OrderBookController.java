package UI.user;

import Database.DBRequests;
import Database.books.models.Book;
import Database.books.models.BookStatus;
import Database.files.models.BookTakingRecord;
import Database.places.models.Place;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Date;
import java.sql.SQLException;

public class OrderBookController {
    @FXML
    TextField bookIdField;
    @FXML
    CheckBox showAvailableBooksCheck;
    @FXML
    ComboBox<Place> bookTakenPlaceBox;
    @FXML
    Button orderBookButton;
    @FXML
    DatePicker dateOfTaking;
    @FXML
    TextArea infoArea;
    private final DBRequests dbRequests = DBRequests.getInstance();

    public Integer userId;
    public void setUserId(Integer userId){
        this.userId=userId;
    }
    @FXML
    public void orderBook() throws SQLException {
        try {
            dbRequests.updateBookStatus(Integer.parseInt(bookIdField.getText()), BookStatus.ЗАКАЗАНО, userId);
            infoArea.setText("Выполнено");
        }
        catch (Exception e){
            System.err.println("can't order book");
        }

    }

}
