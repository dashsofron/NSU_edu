package UI.records;

import Database.DBRequests;
import Database.books.models.BookStatus;
import Database.files.models.BookGenre;
import Database.files.models.BookTakingRecord;
import Database.places.models.Place;
import Database.search.DateSearch;
import UI.BookUIEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Date;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class GetRecordsController {
    private final DBRequests dbRequests = DBRequests.getInstance();
    @FXML
    Button getRecordsButton;
    @FXML
    TableView<BookUIEntity> bookedBooksTable;
    @FXML
    TableColumn<BookUIEntity,Integer> orderedBookIdColumn;
    @FXML
    TableColumn<BookUIEntity,Integer> orderedBookHolderIdColumn;
    @FXML
    TableColumn<BookUIEntity,String> orderedBookNameColumn;
    @FXML
    TableColumn<BookUIEntity,String> orderedBookAuthorColumn;
    @FXML
    TableColumn<BookUIEntity, BookGenre> orderedBookGenreColumn;
    @FXML
    TableColumn<BookUIEntity,LocalDate> orderedBookTakingDateColumn;
    @FXML
    TableColumn<BookUIEntity,LocalDate> orderedBookReturningDateColumn;
    @FXML
    TableColumn<BookUIEntity,String> orderedBookTakingPlaceColumn;
    @FXML
    TableColumn<BookUIEntity,BookStatus> orderedBookStatusColumn;
    @FXML
    CheckBox idCheck;
    @FXML
    TextField idField;
    @FXML
    TextField authorField;
    @FXML
    TextField nameField;
    @FXML
    TextField idUserField;
    @FXML
    ComboBox<BookGenre> genreBox;
    @FXML
    ComboBox<BookStatus> statusBox;
    @FXML
    ComboBox<String> placeBox;
    @FXML
    DatePicker takingDateField;
    @FXML
    DatePicker returningDateField;
    @FXML
    ComboBox<DateSearch> optionTake;
    @FXML
    ComboBox<DateSearch> optionReturn;


    @FXML
    public void dropFilter(){
        idField.clear();
        optionReturn.setValue(DateSearch.РАВНО);
        optionTake.setValue(DateSearch.РАВНО);
        returningDateField.setValue(null);
        takingDateField.setValue(null);
        placeBox.setValue(null);
        statusBox.setValue(null);
        genreBox.setValue(null);
        idUserField.clear();
        nameField.clear();
        authorField.clear();
    }

    public void initController(){
        genreBox.getItems().setAll(BookGenre.IT,BookGenre.АНГЛИЙСКИЙ_ЯЗЫК,BookGenre.БИОЛОГИЯ,BookGenre.ГЕОЛОГИЯ,
                BookGenre.ЭКОНОМИКА,BookGenre.РУССКИЙ_ЯЗЫК,BookGenre.ПРАВО,BookGenre.МАТЕМАТИКА);
        statusBox.getItems().setAll(BookStatus.ПОТЕРЯНО,BookStatus.ЗАКАЗАНО,BookStatus.ДОСТУПНО,BookStatus.ВЗЯТО,BookStatus.ГОТОВИТСЯ,BookStatus.ВОЗВРАЩЕНО);
        optionTake.getItems().setAll(DateSearch.БОЛЬШЕ,DateSearch.МЕНЬШЕ,DateSearch.РАВНО);
        optionReturn.getItems().setAll(DateSearch.БОЛЬШЕ,DateSearch.МЕНЬШЕ,DateSearch.РАВНО);
        List<Place> placeList=dbRequests.getPlaces();
        for(Place place:placeList)
            placeBox.getItems().add(place.getPlaceName());
        optionReturn.setValue(DateSearch.РАВНО);
        optionTake.setValue(DateSearch.РАВНО);
        getRecords();
    }

    @FXML
    public void getRecords() {
        List<BookUIEntity> records;
        if (!idField.getText().equals("")) {
            records = new LinkedList<>();
            BookUIEntity record = dbRequests.getBookTakenRecord(Integer.parseInt(idField.getText()));
            records.add(record);
        } else {
            BookUIEntity filter=new BookUIEntity(null,nameField.getText(),authorField.getText(),genreBox.getValue(),
                    takingDateField.getValue(),returningDateField.getValue(),
                    placeBox.getValue(),statusBox.getValue(),null);

            records = dbRequests.getBooksWithFilter(filter,optionTake.getValue(),optionReturn.getValue());

        }

        orderedBookIdColumn.setCellValueFactory(new PropertyValueFactory<BookUIEntity, Integer>("id"));
        orderedBookHolderIdColumn.setCellValueFactory(new PropertyValueFactory<BookUIEntity, Integer>("readerId"));
        orderedBookNameColumn.setCellValueFactory(new PropertyValueFactory<BookUIEntity, String>("name"));
        orderedBookAuthorColumn.setCellValueFactory(new PropertyValueFactory<BookUIEntity, String>("author"));
        orderedBookGenreColumn.setCellValueFactory(new PropertyValueFactory<BookUIEntity, BookGenre>("genre"));
        orderedBookTakingDateColumn.setCellValueFactory(new PropertyValueFactory<BookUIEntity, LocalDate>("takingDate"));
        orderedBookReturningDateColumn.setCellValueFactory(new PropertyValueFactory<BookUIEntity, LocalDate>("returningDate"));
        orderedBookTakingPlaceColumn.setCellValueFactory(new PropertyValueFactory<BookUIEntity, String>("place"));
        orderedBookStatusColumn.setCellValueFactory(new PropertyValueFactory<BookUIEntity,BookStatus>("status"));

        ObservableList<BookUIEntity> recordsO = FXCollections.observableArrayList(records);

        bookedBooksTable.setItems(recordsO);
    }
    @FXML
    public void enableIdField() {
        if (idCheck.isSelected())
            idField.setDisable(false);
        else
            idField.setDisable(true);
    }
}
