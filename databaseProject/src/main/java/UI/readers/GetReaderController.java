package UI.readers;

import Database.DBRequests;
import Database.readers.models.ReaderEntity;
import Database.readers.models.ReaderType;
import Database.search.DateSearch;
import UI.ReaderUiEntity;
import UI.readers.table.StudentsTableController;
import UI.readers.table.TeacherTableController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GetReaderController {
    private final DBRequests dbRequests = DBRequests.getInstance();

    @FXML
    ComboBox<ReaderType> readerTypeBox;
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
    TableView<ReaderUiEntity> readersTable;
    @FXML
    TableColumn<ReaderUiEntity,Integer> idColumn;
    @FXML
    TableColumn<ReaderUiEntity,String> nameColumn;
    @FXML
    TableColumn<ReaderUiEntity,String> lastNameColumn;
    @FXML
    TableColumn<ReaderUiEntity,String> fatherNameColumn;
    @FXML
    TableColumn<ReaderUiEntity,LocalDate> regDateColumn;
    @FXML
    TableColumn<ReaderUiEntity,LocalDate> leaveDateColumn;
    @FXML
    TableColumn<ReaderUiEntity,Integer> ticketNumColumn;
    @FXML
    TableColumn<ReaderUiEntity,ReaderType> typeColumn;
    @FXML
    TableColumn<ReaderUiEntity,Integer> bookNumColumn;
    @FXML
    TableColumn<ReaderUiEntity,Integer> debtColumn;
    @FXML
    TableColumn<ReaderUiEntity,Integer> punishmentNumColumn;
    @FXML
    TableColumn<ReaderUiEntity,Integer> paymentColumn;
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
        readerTypeBox.setValue(null);
        nameField.clear();
        lastnameField.clear();
        fathernameField.clear();
        bookNumField.clear();
        punishmentNumField.clear();
        debtBookNumField.clear();
        regDateField.setValue(null);
        leavDateField.setValue(null);
    }

    public void initController() {
        readerTypeBox.getItems().addAll(
                ReaderType.СТУДЕНТ,
                ReaderType.УЧИТЕЛЬ,
                ReaderType.ЧИТАТЕЛЬ
        );
        optionTake.getItems().setAll(DateSearch.БОЛЬШЕ,DateSearch.МЕНЬШЕ,DateSearch.РАВНО);
        optionReturn.getItems().setAll(DateSearch.БОЛЬШЕ,DateSearch.МЕНЬШЕ,DateSearch.РАВНО);
        optionReturn.setValue(DateSearch.РАВНО);
        optionTake.setValue(DateSearch.РАВНО);
        getReaders();
    }

    @FXML
    public void getReader() throws UnsupportedEncodingException {
        if(readerTypeBox.getValue()!=null){
            switch (readerTypeBox.getValue()) {
                case СТУДЕНТ:
                    openStudentsTable();
                    return;

                case УЧИТЕЛЬ:
                    openTeachersTable();
                    return;
                case ЧИТАТЕЛЬ:getReaders();
                default:getReaders();
            }
        }
        else getReaders();

    }

    public void getReaders() {
        List<ReaderUiEntity> readers=new ArrayList<>();
        ReaderUiEntity filter=new ReaderUiEntity(null, nameField.getText(), lastnameField.getText(),
                fathernameField.getText(), readerTypeBox.getValue(), regDateField.getValue(),
                leavDateField.getValue(),null,bookNumField.getText(),
               debtBookNumField.getText(), punishmentNumField.getText(),
                paymentField.getText());
        readers = dbRequests.getReadersWithFilter(filter,optionTake.getValue(),optionReturn.getValue());
        idColumn.setCellValueFactory(new PropertyValueFactory<ReaderUiEntity, Integer>("readerId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<ReaderUiEntity, String>("readerName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<ReaderUiEntity, String>("readerLastName"));
        fatherNameColumn.setCellValueFactory(new PropertyValueFactory<ReaderUiEntity, String>("fatherName"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<ReaderUiEntity, ReaderType>("readerType"));
        regDateColumn.setCellValueFactory(new PropertyValueFactory<ReaderUiEntity, LocalDate>("registrationDate"));
        leaveDateColumn.setCellValueFactory(new PropertyValueFactory<ReaderUiEntity, LocalDate>("leavingDate"));
        bookNumColumn.setCellValueFactory(new PropertyValueFactory<ReaderUiEntity, Integer>("bookNumField"));
        debtColumn.setCellValueFactory(new PropertyValueFactory<ReaderUiEntity, Integer>("debtField"));
        punishmentNumColumn.setCellValueFactory(new PropertyValueFactory<ReaderUiEntity, Integer>("punishmentNumField"));
        paymentColumn.setCellValueFactory(new PropertyValueFactory<ReaderUiEntity, Integer>("paymentField"));
        ObservableList<ReaderUiEntity> readersO = FXCollections.observableArrayList(readers);
        readersTable.setItems(readersO);

    }

    public void openStudentsTable() {
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/readers/newST.fxml"));
            root = loader.load();
            GetStudentController c = loader.getController();
            c.initController();
            Stage stage = new Stage();
            stage.setTitle("Студенты");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void openTeachersTable() {
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/readers/newT.fxml"));
            root = loader.load();
            GetTeacherController c = loader.getController();
            c.initController();
            Stage stage = new Stage();
            stage.setTitle("Учителя");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
