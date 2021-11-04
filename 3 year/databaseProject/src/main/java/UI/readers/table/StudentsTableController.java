package UI.readers.table;
import Database.readers.models.ReaderEntity;
import Database.readers.models.ReaderType;
import Database.readers.models.StudentEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class StudentsTableController {
    @FXML
    TableView<StudentEntity> readersTable;
    @FXML
    TableColumn<StudentEntity,Integer> idColumn;
    @FXML
    TableColumn<StudentEntity,String> nameColumn;
    @FXML
    TableColumn<StudentEntity,String> lastName;
    @FXML
    TableColumn<StudentEntity,String> fatherName;
    @FXML
    TableColumn<StudentEntity, LocalDate> regDate;
    @FXML
    TableColumn<StudentEntity,LocalDate> leaveDate;
    @FXML
    TableColumn<StudentEntity,Integer> ticketNum;
    @FXML
    TableColumn<StudentEntity, ReaderType> type;
    @FXML
    TableColumn<StudentEntity,String> department;
    @FXML
    TableColumn<StudentEntity,Integer> group;
    List<ReaderEntity> students;
    public void setStudents(List<ReaderEntity> students){
        this.students=students;

        idColumn.setCellValueFactory(new PropertyValueFactory<StudentEntity, Integer>("readerId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<StudentEntity, String>("readerName"));
        lastName.setCellValueFactory(new PropertyValueFactory<StudentEntity, String>("readerLastName"));
        fatherName.setCellValueFactory(new PropertyValueFactory<StudentEntity, String>("fatherName"));
        type.setCellValueFactory(new PropertyValueFactory<StudentEntity, ReaderType>("readerType"));
        regDate.setCellValueFactory(new PropertyValueFactory<StudentEntity, LocalDate>("registrationDate"));
        leaveDate.setCellValueFactory(new PropertyValueFactory<StudentEntity, LocalDate>("leavingDate"));
        ticketNum.setCellValueFactory(new PropertyValueFactory<StudentEntity, Integer>("ticketNumber"));
        department.setCellValueFactory(new PropertyValueFactory<StudentEntity,String>("department"));
        group.setCellValueFactory(new PropertyValueFactory<StudentEntity, Integer>("group"));

        List<StudentEntity> s=new ArrayList<>();
        for(ReaderEntity r:students)
            s.add((StudentEntity) r);
        ObservableList<StudentEntity> readersO = FXCollections.observableArrayList(s);
        readersTable.setItems(readersO);
    }

}
