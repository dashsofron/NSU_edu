package UI.readers;

import Database.DBRequests;
import Database.readers.models.ReaderType;
import Database.search.DateSearch;
import UI.ReaderUiEntity;
import UI.StudentUiEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GetStudentController {
    private final DBRequests dbRequests = DBRequests.getInstance();

    @FXML
    ComboBox<String> readerTypeBox;
    @FXML
    TextField idField;
    @FXML
    TextField nameField;
    @FXML
    TextField lastnameField;
    @FXML
    TextField fathernameField;
    @FXML
    TextField bookNumField;
    @FXML
    TextField paymentField;
    @FXML
    TextField punishmentNumField;
    @FXML
    TextField debtBookNumField;
    @FXML
    DatePicker regDateField;
    @FXML
    DatePicker leavDateField;
    @FXML
    TextField departmentField;
    @FXML
    TextField groupField;

    @FXML
    TableView<StudentUiEntity> readersTable;
    @FXML
    TableColumn<StudentUiEntity,Integer> idColumn;
    @FXML
    TableColumn<StudentUiEntity,String> nameColumn;
    @FXML
    TableColumn<StudentUiEntity,String> lastNameColumn;
    @FXML
    TableColumn<StudentUiEntity,String> fatherNameColumn;
    @FXML
    TableColumn<StudentUiEntity, LocalDate> regDateColumn;
    @FXML
    TableColumn<StudentUiEntity,LocalDate> leaveDateColumn;
    @FXML
    TableColumn<StudentUiEntity,Integer> ticketNumColumn;
    @FXML
    TableColumn<StudentUiEntity, ReaderType> typeColumn;
    @FXML
    TableColumn<StudentUiEntity,Integer> bookNumColumn;
    @FXML
    TableColumn<StudentUiEntity,Integer> debtColumn;
    @FXML
    TableColumn<StudentUiEntity,Integer> punishmentNumColumn;
    @FXML
    TableColumn<StudentUiEntity,Integer> paymentColumn;
    @FXML
    TableColumn<StudentUiEntity,String> departmentColumn;
    @FXML
    TableColumn<StudentUiEntity,Integer> groupColumn;
    @FXML
    ComboBox<DateSearch> optionTake;
    @FXML
    ComboBox<DateSearch> optionReturn;

    @FXML
    public void dropFilter(){
        idField.clear();
        optionReturn.setValue(DateSearch.РАВНО);
        optionTake.setValue(DateSearch.РАВНО);
        paymentField.clear();
        readerTypeBox.setValue(ReaderType.СТУДЕНТ.name());
        lastnameField.clear();
        fathernameField.clear();
        bookNumField.clear();
        punishmentNumField.clear();
        debtBookNumField.clear();
        regDateField.setValue(null);
        leavDateField.setValue(null);
        groupField.clear();
        departmentField.clear();
    }
    public void initController() {
        readerTypeBox.setValue(ReaderType.СТУДЕНТ.name());

        optionTake.getItems().setAll(DateSearch.БОЛЬШЕ,DateSearch.МЕНЬШЕ,DateSearch.РАВНО);
        optionReturn.getItems().setAll(DateSearch.БОЛЬШЕ,DateSearch.МЕНЬШЕ,DateSearch.РАВНО);
        optionReturn.setValue(DateSearch.РАВНО);
        optionTake.setValue(DateSearch.РАВНО);
        getReaders();
    }

    public void getReaders() {
        List<StudentUiEntity> readers=new ArrayList<>();
        StudentUiEntity filter=new StudentUiEntity(null, nameField.getText(), lastnameField.getText(),
                fathernameField.getText(), ReaderType.СТУДЕНТ, regDateField.getValue(),
                leavDateField.getValue(),null,bookNumField.getText(),
                debtBookNumField.getText(), punishmentNumField.getText(),
                paymentField.getText(),departmentField.getText(),groupField.getText());
        readers = dbRequests.getStudentsWithFilter(filter,optionTake.getValue(),optionReturn.getValue());
        idColumn.setCellValueFactory(new PropertyValueFactory<StudentUiEntity, Integer>("readerId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<StudentUiEntity, String>("readerName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<StudentUiEntity, String>("readerLastName"));
        fatherNameColumn.setCellValueFactory(new PropertyValueFactory<StudentUiEntity, String>("fatherName"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<StudentUiEntity, ReaderType>("readerType"));
        regDateColumn.setCellValueFactory(new PropertyValueFactory<StudentUiEntity, LocalDate>("registrationDate"));
        leaveDateColumn.setCellValueFactory(new PropertyValueFactory<StudentUiEntity, LocalDate>("leavingDate"));
        bookNumColumn.setCellValueFactory(new PropertyValueFactory<StudentUiEntity, Integer>("bookNumField"));
        debtColumn.setCellValueFactory(new PropertyValueFactory<StudentUiEntity, Integer>("debtField"));
        punishmentNumColumn.setCellValueFactory(new PropertyValueFactory<StudentUiEntity, Integer>("punishmentNumField"));
        paymentColumn.setCellValueFactory(new PropertyValueFactory<StudentUiEntity, Integer>("paymentField"));
        groupColumn.setCellValueFactory(new PropertyValueFactory<StudentUiEntity, Integer>("group"));
        departmentColumn.setCellValueFactory(new PropertyValueFactory<StudentUiEntity, String>("department"));
        ObservableList<StudentUiEntity> readersO = FXCollections.observableArrayList(readers);
        readersTable.setItems(readersO);

    }
}
