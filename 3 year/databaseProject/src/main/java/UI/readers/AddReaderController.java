package UI.readers;

import Database.DBRequests;
import Database.readers.models.ReaderType;
import Database.readers.models.StudentEntity;
import Database.readers.models.TeacherEntity;
import Database.user.User;
import UI.ErrorController;
import UI.UserController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AddReaderController {
    private final String readerIdLabelBeginning = "reader id:";
    private final DBRequests dbRequests = DBRequests.getInstance();
    @FXML
    TextField readerNameField;
    @FXML
    TextField readerFatherNameField;
    @FXML
    TextField readerLastNameField;
    @FXML
    DatePicker readerRegistrationDateField;
    @FXML
    ComboBox<String> readerTypeBox;
    @FXML
    TextField studentDepartment;
    @FXML
    TextField studentGroup;
    @FXML
    TextField teacherCathedra;
    @FXML
    TextField teacherDegree;
    @FXML
    Button addReaderButton;
    @FXML
    TextArea infoArea;
    String idL = "id: ";
    private User user = null;
    private Stage stage = null;

    public void initController(User user, Stage stage) {
        readerTypeBox.getItems().addAll(
                "СТУДЕНТ",
                "УЧИТЕЛЬ",
                "ЧИТАТЕЛЬ"
        );
        this.user = user;
        this.stage = stage;
    }

    public void initController() {
        readerTypeBox.getItems().addAll(
                "СТУДЕНТ",
                "УЧИТЕЛЬ",
                "ЧИТАТЕЛЬ"
        );
        this.user = null;
    }

    @FXML
    public void showExtraFields() {
        String typeGet = readerTypeBox.getSelectionModel().getSelectedItem();
        ReaderType type = ReaderType.valueOf(typeGet);
        switch (type) {
            case СТУДЕНТ:
                teacherCathedra.setVisible(false);
                teacherDegree.setVisible(false);
                studentGroup.setVisible(true);
                studentDepartment.setVisible(true);
                break;
            case УЧИТЕЛЬ:
                teacherCathedra.setVisible(true);
                teacherDegree.setVisible(true);
                studentGroup.setVisible(false);
                studentDepartment.setVisible(false);
                break;
        }
    }

    @FXML
    public void addReader() throws SQLException {

        String moreInfoError = "";
        moreInfoError = checkReader(moreInfoError);
        if (!moreInfoError.equals("")) {
            openErrorWindow(moreInfoError);
            return;
        }
        String typeGet = readerTypeBox.getSelectionModel().getSelectedItem();
        ReaderType type = ReaderType.valueOf(typeGet);
        Integer readerId = null;
        LocalDate localDate = readerRegistrationDateField.getValue();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String formattedString;
        if (localDate != null)
            formattedString = localDate.format(formatter);
        switch (type) {
            case СТУДЕНТ: {
                moreInfoError = checkStudentInfo(moreInfoError);
                if (!moreInfoError.equals("")) {
                    openErrorWindow(moreInfoError);
                    return;
                }

                StudentEntity student = new StudentEntity();
                student.setReaderName(readerNameField.getText());
                student.setReaderLastName(readerLastNameField.getText());
                student.setFatherName(readerFatherNameField.getText());
                student.setReaderType(type);
                if(localDate!=null)
                student.setRegistrationDate(Date.valueOf(localDate));
                student.setDepartment(studentDepartment.getText());
                student.setGroup(Integer.valueOf(studentGroup.getText()));
                readerId = dbRequests.addReader(student);
            }
            break;
            case УЧИТЕЛЬ: {
                moreInfoError = checkTeacherInfo(moreInfoError);
                if (!moreInfoError.equals("")) {
                    openErrorWindow(moreInfoError);
                    return;
                }
                TeacherEntity teacher = new TeacherEntity();
                teacher.setReaderName(readerNameField.getText());
                teacher.setReaderLastName(readerLastNameField.getText());
                teacher.setFatherName(readerFatherNameField.getText());
                teacher.setReaderType(type);
                if(localDate!=null)
                    teacher.setRegistrationDate(Date.valueOf(localDate));

                teacher.setCathedra(teacherCathedra.getText());
                teacher.setDegree(teacherDegree.getText());
                readerId = dbRequests.addReader(teacher);
            }
            break;
            case ЧИТАТЕЛЬ:
        }

        if (readerId != null) {
            infoArea.setText(readerIdLabelBeginning + readerId.toString());
            if (user != null) {
                user.setReaderId(readerId);
                dbRequests.updateUser(user);
                Parent root;
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/user/userMenu.fxml"));

                    root = loader.load();
                    UserController controller = loader.getController();
                    controller.initController(user);
                    //Stage stage = new Stage();
                    stage.setTitle("Меню пользователя");
                    stage.setScene(new Scene(root));
                    stage.show();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else infoArea.setText("can't add new reader");

    }


    public String checkReader(String moreInfo) {
        if (readerNameField.getText().isEmpty())
            moreInfo += "пожалуйста, введите имя читателя \n";
        if (readerLastNameField.getText().isEmpty())
            moreInfo += "пожалуйста, введите фамилию читателя \n";
        if (readerTypeBox.getSelectionModel().isEmpty() || readerTypeBox.getSelectionModel().getSelectedItem().isEmpty())
            moreInfo += "пожалуйста, введите тип читателя \n";
//        if (readerRegistrationDateField.getValue() == null)
//            moreInfo += "пожалуйста, введите дату регистрации \n";
        return moreInfo;
    }

    public String checkStudentInfo(String moreInfo) {
        if (studentDepartment.getText().isEmpty())
            moreInfo += "пожалуйста, введите факультет студента или измените тип читателя \n";
        if (studentGroup.getText().isEmpty())
            moreInfo += "пожалуйста, введите группу студента или измените тип читателя \n";
        if (!studentGroup.getText().matches("\\d{0,7}([\\.]\\d{0,4})?")) {
            moreInfo += "пожалуйста, введите группу в виде числа \n";
        }
        try {
            return new String(moreInfo.getBytes("Cp1251"), StandardCharsets.UTF_8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return moreInfo;
    }

    public String checkTeacherInfo(String moreInfo) {
        if (teacherCathedra.getText().isEmpty())
            moreInfo += "пожалуйста, введите кафедру преподавателя или смените тип читателя \n";
        if (teacherDegree.getText().isEmpty())
            moreInfo += "пожалуйста, введите ученую степень преподавателя или смените тип читателя \n";
        try {
            return new String(moreInfo.getBytes("Cp1251"), StandardCharsets.UTF_8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return moreInfo;
    }


    public void openErrorWindow(String error) {
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/errorWindow.fxml"));

            root = loader.load();
            ErrorController c = loader.getController();
            c.setError(error);
            Stage stage = new Stage();
            stage.setTitle("Ошибка");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addNeededFields(ActionEvent actionEvent) {
        String selectedValue = readerTypeBox.getSelectionModel().getSelectedItem();
        switch (ReaderType.valueOf(selectedValue)) {
            case СТУДЕНТ:
                studentGroup.setVisible(true);
                studentDepartment.setVisible(true);
                teacherCathedra.setVisible(false);
                teacherDegree.setVisible(false);
                break;
            case УЧИТЕЛЬ:
                studentGroup.setVisible(false);
                studentDepartment.setVisible(false);
                teacherCathedra.setVisible(true);
                teacherDegree.setVisible(true);
                break;
            default:
        }
    }
}
