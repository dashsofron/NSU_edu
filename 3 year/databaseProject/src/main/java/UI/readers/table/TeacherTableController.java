package UI.readers.table;
import Database.readers.models.ReaderEntity;
import Database.readers.models.ReaderType;
import Database.readers.models.StudentEntity;
import Database.readers.models.TeacherEntity;
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

public class TeacherTableController {
    @FXML
    TableView<TeacherEntity> readersTable;
    @FXML
    TableColumn<TeacherEntity,Integer> idColumn;
    @FXML
    TableColumn<TeacherEntity,String> nameColumn;
    @FXML
    TableColumn<TeacherEntity,String> lastName;
    @FXML
    TableColumn<TeacherEntity,String> fatherName;
    @FXML
    TableColumn<TeacherEntity, LocalDate> regDate;
    @FXML
    TableColumn<TeacherEntity,LocalDate> leaveDate;
    @FXML
    TableColumn<TeacherEntity,Integer> ticketNum;
    @FXML
    TableColumn<TeacherEntity, ReaderType> type;
    @FXML
    TableColumn<TeacherEntity,String> cathedra;
    @FXML
    TableColumn<TeacherEntity,String> degree;
    List<ReaderEntity> teachers;
    public void setTeachers(List<ReaderEntity> teachers){
        this.teachers=teachers;
        idColumn.setCellValueFactory(new PropertyValueFactory<TeacherEntity, Integer>("readerId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<TeacherEntity, String>("readerName"));
        lastName.setCellValueFactory(new PropertyValueFactory<TeacherEntity, String>("readerLastName"));
        fatherName.setCellValueFactory(new PropertyValueFactory<TeacherEntity, String>("fatherName"));
        type.setCellValueFactory(new PropertyValueFactory<TeacherEntity, ReaderType>("readerType"));
        regDate.setCellValueFactory(new PropertyValueFactory<TeacherEntity, LocalDate>("registrationDate"));
        leaveDate.setCellValueFactory(new PropertyValueFactory<TeacherEntity, LocalDate>("leavingDate"));
        ticketNum.setCellValueFactory(new PropertyValueFactory<TeacherEntity, Integer>("ticketNumber"));
        cathedra.setCellValueFactory(new PropertyValueFactory<TeacherEntity,String>("cathedra"));
        degree.setCellValueFactory(new PropertyValueFactory<TeacherEntity, String>("degree"));

        List<TeacherEntity> t=new ArrayList<>();
        for(ReaderEntity r:teachers)
            t.add((TeacherEntity) r);
        ObservableList<TeacherEntity> readersO = FXCollections.observableArrayList(t);
        readersTable.setItems(readersO);
    }

}
