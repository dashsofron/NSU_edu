package UI.readers;

import Database.DBRequests;
import Database.readers.models.ReaderType;
import Database.search.DateSearch;
import UI.ReaderUiEntity;
import UI.StudentUiEntity;
import UI.TeacherUiEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GetTeacherController {
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
    TextField cathedraField;
    @FXML
    TextField degreeField;


    @FXML
    TableView<TeacherUiEntity> readersTable;
    @FXML
    TableColumn<TeacherUiEntity,Integer> idColumn;
    @FXML
    TableColumn<TeacherUiEntity,String> nameColumn;
    @FXML
    TableColumn<TeacherUiEntity,String> lastNameColumn;
    @FXML
    TableColumn<TeacherUiEntity,String> fatherNameColumn;
    @FXML
    TableColumn<TeacherUiEntity, LocalDate> regDateColumn;
    @FXML
    TableColumn<TeacherUiEntity,LocalDate> leaveDateColumn;
    @FXML
    TableColumn<TeacherUiEntity,Integer> ticketNumColumn;
    @FXML
    TableColumn<TeacherUiEntity, ReaderType> typeColumn;
    @FXML
    TableColumn<TeacherUiEntity,Integer> bookNumColumn;
    @FXML
    TableColumn<TeacherUiEntity,Integer> debtColumn;
    @FXML
    TableColumn<TeacherUiEntity,Integer> punishmentNumColumn;
    @FXML
    TableColumn<TeacherUiEntity,Integer> paymentColumn;
    @FXML
    TableColumn<TeacherUiEntity,String> cathedraColumn;
    @FXML
    TableColumn<TeacherUiEntity,Integer> degreeColumn;
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
        readerTypeBox.setValue(ReaderType.УЧИТЕЛЬ.name());
        lastnameField.clear();
        fathernameField.clear();
        bookNumField.clear();
        punishmentNumField.clear();
        debtBookNumField.clear();
        regDateField.setValue(null);
        leavDateField.setValue(null);
        degreeField.clear();
        cathedraField.clear();

    }
    public void initController() {

        readerTypeBox.setValue(ReaderType.УЧИТЕЛЬ.name());
        optionTake.getItems().setAll(DateSearch.БОЛЬШЕ,DateSearch.МЕНЬШЕ,DateSearch.РАВНО);
        optionReturn.getItems().setAll(DateSearch.БОЛЬШЕ,DateSearch.МЕНЬШЕ,DateSearch.РАВНО);
        optionReturn.setValue(DateSearch.РАВНО);
        optionTake.setValue(DateSearch.РАВНО);
        getReaders();
    }

    public void getReaders() {
        List<TeacherUiEntity> readers=new ArrayList<>();
        TeacherUiEntity filter=new TeacherUiEntity(null, nameField.getText(), lastnameField.getText(),
                fathernameField.getText(), ReaderType.УЧИТЕЛЬ, regDateField.getValue(),
                leavDateField.getValue(),null,bookNumField.getText(),
                debtBookNumField.getText(), punishmentNumField.getText(),
                paymentField.getText(),cathedraField.getText(),degreeField.getText());
        readers = dbRequests.getTeachersWithFilter(filter,optionTake.getValue(),optionReturn.getValue());
        idColumn.setCellValueFactory(new PropertyValueFactory<TeacherUiEntity, Integer>("readerId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<TeacherUiEntity, String>("readerName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<TeacherUiEntity, String>("readerLastName"));
        fatherNameColumn.setCellValueFactory(new PropertyValueFactory<TeacherUiEntity, String>("fatherName"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<TeacherUiEntity, ReaderType>("readerType"));
        regDateColumn.setCellValueFactory(new PropertyValueFactory<TeacherUiEntity, LocalDate>("registrationDate"));
        leaveDateColumn.setCellValueFactory(new PropertyValueFactory<TeacherUiEntity, LocalDate>("leavingDate"));
        bookNumColumn.setCellValueFactory(new PropertyValueFactory<TeacherUiEntity, Integer>("bookNumField"));
        debtColumn.setCellValueFactory(new PropertyValueFactory<TeacherUiEntity, Integer>("debtField"));
        punishmentNumColumn.setCellValueFactory(new PropertyValueFactory<TeacherUiEntity, Integer>("punishmentNumField"));
        paymentColumn.setCellValueFactory(new PropertyValueFactory<TeacherUiEntity, Integer>("paymentField"));
        degreeColumn.setCellValueFactory(new PropertyValueFactory<TeacherUiEntity, Integer>("degree"));
        cathedraColumn.setCellValueFactory(new PropertyValueFactory<TeacherUiEntity, String>("cathedra"));
        ObservableList<TeacherUiEntity> readersO = FXCollections.observableArrayList(readers);
        readersTable.setItems(readersO);

    }
}
