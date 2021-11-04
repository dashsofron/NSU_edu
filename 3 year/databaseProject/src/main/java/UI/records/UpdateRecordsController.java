package UI.records;

import Database.DBRequests;
import Database.books.models.BookStatus;
import Database.files.models.BookTakingRecord;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Date;

public class UpdateRecordsController {
    private final DBRequests dbRequests = DBRequests.getInstance();
    @FXML
    TextField idReaderField;
    @FXML
    TextField idBookField;
    @FXML
    DatePicker startDateField;
    @FXML
    DatePicker endDateField;
    @FXML
    Button addRecordButton;
    @FXML
    TextField idRecordField;
    @FXML
    TextArea infoArea;
    @FXML
    CheckBox getOldInfoCheck;
    String infoStart = "Нужно ввести или выбрать:\n ";


    @FXML
    ComboBox<BookStatus> statusField;

    public void initController(){
        statusField.getItems().
                addAll(BookStatus.ВЗЯТО, BookStatus.ВОЗВРАЩЕНО, BookStatus.ГОТОВИТСЯ, BookStatus.ДОСТУПНО, BookStatus.ПОТЕРЯНО);

    }

    @FXML
    public void getOldInfo() {
        cleanFields();
        if (getOldInfoCheck.isSelected()&&idRecordField.getText() != null) {
            BookTakingRecord record = dbRequests.getRecord(Integer.valueOf(idRecordField.getText()));
            if (record != null) {
                idRecordField.setText(record.getRecordId().toString());
                startDateField.setValue(record.getDateOfTaking().toLocalDate());
                endDateField.setValue(record.getDateOfReturning().toLocalDate());
                idReaderField.setText(record.getReaderId().toString());
                idBookField.setText(record.getBookId().toString());
                statusField.setValue(record.getStatus());

            }
        }

    }

    public void cleanFields() {
        startDateField.setValue(null);
        endDateField.setValue(null);
        idReaderField.clear();
        idBookField.clear();
        statusField.setValue(null);
    }

    @FXML
    public void updateRecord() {
        infoArea.clear();
        String info = "";
        if (idRecordField.getText().equals("") ||idReaderField.getText().equals("") ||idBookField.getText().equals("") || statusField.getValue() == null||
                startDateField.getValue() == null||endDateField.getValue() == null) {
            if (idRecordField.getText().equals(""))
                info += "Идентификатор записи\n";
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
        BookTakingRecord record = new BookTakingRecord();
        record.setRecordId(Integer.valueOf(idRecordField.getText()));
        record.setDateOfTaking(Date.valueOf(startDateField.getValue()));
        record.setStatus(statusField.getValue());
        record.setDateOfReturning(Date.valueOf(endDateField.getValue()));
        record.setReaderId(Integer.valueOf(idReaderField.getText()));
        record.setBookId(Integer.valueOf(idBookField.getText()));
        dbRequests.updateRecord(record);
        infoArea.setText("Выполнено");
    }
}
