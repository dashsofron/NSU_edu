package UI.records;

import Database.DBRequests;
import Database.books.models.BookStatus;
import Database.files.models.BookTakingRecord;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Date;

public class AddRecordsController {
    @FXML
    TextField idReaderField;
    @FXML
    TextField idBookField;
    @FXML
    DatePicker startDateField;
    @FXML
    DatePicker endDateField;
    @FXML
    TextArea infoArea;
    @FXML
    ComboBox<BookStatus> statusField;
    private final DBRequests dbRequests = DBRequests.getInstance();
    String idL="Идентификатор : ";
    String infoStart = "Нужно ввести или выбрать:\n ";



    public void initController(){
        statusField.getItems().
                addAll(BookStatus.ВЗЯТО, BookStatus.ВОЗВРАЩЕНО, BookStatus.ГОТОВИТСЯ, BookStatus.ДОСТУПНО, BookStatus.ПОТЕРЯНО);
    }

    @FXML
    public void addRecord() {
        infoArea.clear();
        String info = "";
        if (idReaderField.getText().equals("") ||idBookField.getText().equals("") || statusField.getValue() == null||
                startDateField.getValue() == null||endDateField.getValue() == null) {
            if (idReaderField.getText().equals(""))
                info += "Идентификатор читателя\n";
            if (idBookField.getText().equals(""))
                info += "Идентификатор книги\n";
            if (statusField.getValue() == null)
                info += "Статус\n";
            if (startDateField.getValue() == null)
                info += "Дату взятия\n";
            if (endDateField.getValue() == null)
                info += "Дату возврата\n";
            infoArea.setText(infoStart + info);
            return;
        }
        BookTakingRecord record=new BookTakingRecord();
        record.setDateOfTaking(Date.valueOf(startDateField.getValue()));
        record.setStatus(statusField.getValue());
        record.setDateOfReturning(Date.valueOf(endDateField.getValue()));
        record.setReaderId(Integer.valueOf(idReaderField.getText()));
        record.setBookId(Integer.valueOf(idBookField.getText()));
        Integer id=dbRequests.addRecord(record);
        infoArea.setText(idL+id);
    }
}
