package UI;
//выгрузить инфу по читателю, инфу по взятым им книгам и инфу по наказаниям (выгружать из таблицы наказаний и книг по его id)

import Database.DBRequests;
import Database.books.models.BookStatus;
import Database.files.models.BookGenre;
import Database.files.models.Punishment;
import Database.files.models.PunishmentStatus;
import Database.files.models.PunishmentType;
import Database.readers.models.ReaderEntity;
import Database.readers.models.ReaderType;
import Database.readers.models.StudentEntity;
import Database.readers.models.TeacherEntity;
import Database.user.User;
import UI.user.CancelOrderController;
import UI.user.ShowBookController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class UserController {
    private final DBRequests dbRequests = DBRequests.getInstance();
    @FXML
    CheckBox extraInfoCheckBox;
    @FXML
    Label extraInfoField2Label;
    @FXML
    Label extraInfoField1Label;
    @FXML
    TextField passwordField;
    @FXML
    TextField loginField;
    @FXML
    TextField idField;
    @FXML
    TextField nameField;
    @FXML
    TextField fatherNameField;
    @FXML
    TextField lastNameField;
    @FXML
    ComboBox<ReaderType> readerType;
    @FXML
    TextField extraInfoField1;
    @FXML
    TextField extraInfoField2;
    @FXML
    Button askForDataChangeButton;
    @FXML
    TextArea commentTextArea;
    @FXML
    TableView<BookUIEntity> takenBooksTable;
    @FXML
    TableColumn<BookUIEntity, Integer> takenBookIdColumn;
    @FXML
    TableColumn<BookUIEntity, String> takenBookNameColumn;
    @FXML
    TableColumn<BookUIEntity, String> takenBookAuthorColumn;
    @FXML
    TableColumn<BookUIEntity, BookGenre> takenBookGenreColumn;
    @FXML
    TableColumn<BookUIEntity, LocalDate> takenBookTakingDateColumn;
    @FXML
    TableColumn<BookUIEntity, LocalDate> takenBookReturningDateColumn;
    @FXML
    TableColumn<BookUIEntity, String> takenBookTakingPlaceColumn;
    @FXML
    TableView<Punishment> punishmentTable;
    @FXML
    TableColumn<Punishment, PunishmentType> punishmentTypeColumn;
    @FXML
    TableColumn<Punishment, String> punishmentReasonColumn;
    @FXML
    TableColumn<Punishment, PunishmentStatus> punishmentStatusColumn;
    @FXML
    TableColumn<Punishment, LocalDate> punishmentStartColumn;
    @FXML
    TableColumn<Punishment, LocalDate> punishmentEndColumn;
    @FXML
    TableColumn<Punishment, Integer> punishmentPaymentColumn;
    @FXML
    Button orderBookButton;
    @FXML
    Button cancelOrderBookButton;
    @FXML
    Button showBookButton;
    @FXML
    Label statusInfoLabel;
    @FXML
    Button continueBookOrderButton;
    @FXML
    TableView<BookUIEntity> bookedBooksTable;
    @FXML
    TableColumn<BookUIEntity, Integer> orderedBookIdColumn;
    @FXML
    TableColumn<BookUIEntity, String> orderedBookNameColumn;
    @FXML
    TableColumn<BookUIEntity, String> orderedBookAuthorColumn;
    @FXML
    TableColumn<BookUIEntity, BookGenre> orderedBookGenreColumn;
    @FXML
    TableColumn<BookUIEntity, LocalDate> orderedBookTakingDateColumn;
    @FXML
    TableColumn<BookUIEntity, LocalDate> orderedBookReturningDateColumn;
    @FXML
    TableColumn<BookUIEntity, BookStatus> orderedBookStatusColumn;
    @FXML
    TableColumn<BookUIEntity, String> orderedBookTakingPlaceColumn;
    private Integer readerId;


    public void initController(User user) {
        this.readerId = user.getReaderId();
        loginField.setText(user.getUsername());
        passwordField.setText(user.getPassword());
        ReaderEntity reader = dbRequests.getReader(readerId);
        readerType.getItems().add(reader.getReaderType());

        idField.setText(readerId.toString());
        nameField.setText(reader.getReaderName());
        fatherNameField.setText(reader.getFatherName());
        lastNameField.setText(reader.getReaderLastName());
        readerType.setValue(reader.getReaderType());
        switch (reader.getReaderType()) {
            case СТУДЕНТ:
                StudentEntity student = (StudentEntity) reader;
                extraInfoField1Label.setText("Факультет");
                extraInfoField1.setText(student.getDepartment());
                extraInfoField2Label.setText("Группа");
                extraInfoField2.setText(student.getGroup().toString());
                break;
            case УЧИТЕЛЬ:
                TeacherEntity teacher = (TeacherEntity) reader;
                extraInfoField1Label.setText("Кафедра");
                extraInfoField1.setText(teacher.getCathedra());
                extraInfoField2Label.setText("Должность");
                extraInfoField2.setText(teacher.getDegree());
                break;
        }


        takenBookIdColumn.setCellValueFactory(new PropertyValueFactory<BookUIEntity, Integer>("id"));
        takenBookNameColumn.setCellValueFactory(new PropertyValueFactory<BookUIEntity, String>("name"));
        takenBookAuthorColumn.setCellValueFactory(new PropertyValueFactory<BookUIEntity, String>("author"));
        takenBookGenreColumn.setCellValueFactory(new PropertyValueFactory<BookUIEntity, BookGenre>("genre"));
        takenBookTakingDateColumn.setCellValueFactory(new PropertyValueFactory<BookUIEntity, LocalDate>("takingDate"));
        takenBookReturningDateColumn.setCellValueFactory(new PropertyValueFactory<BookUIEntity, LocalDate>("returningDate"));
        takenBookTakingPlaceColumn.setCellValueFactory(new PropertyValueFactory<BookUIEntity, String>("place"));


        orderedBookIdColumn.setCellValueFactory(new PropertyValueFactory<BookUIEntity, Integer>("id"));
        orderedBookNameColumn.setCellValueFactory(new PropertyValueFactory<BookUIEntity, String>("name"));
        orderedBookAuthorColumn.setCellValueFactory(new PropertyValueFactory<BookUIEntity, String>("author"));
        orderedBookGenreColumn.setCellValueFactory(new PropertyValueFactory<BookUIEntity, BookGenre>("genre"));
        orderedBookTakingDateColumn.setCellValueFactory(new PropertyValueFactory<BookUIEntity, LocalDate>("takingDate"));
        orderedBookReturningDateColumn.setCellValueFactory(new PropertyValueFactory<BookUIEntity, LocalDate>("returningDate"));
        orderedBookTakingPlaceColumn.setCellValueFactory(new PropertyValueFactory<BookUIEntity, String>("place"));
        orderedBookStatusColumn.setCellValueFactory(new PropertyValueFactory<BookUIEntity, BookStatus>("status"));


        punishmentTypeColumn.setCellValueFactory(new PropertyValueFactory<Punishment, PunishmentType>("punishmentType"));
        punishmentReasonColumn.setCellValueFactory(new PropertyValueFactory<Punishment, String>("reason"));
        punishmentStatusColumn.setCellValueFactory(new PropertyValueFactory<Punishment, PunishmentStatus>("status"));
        punishmentStartColumn.setCellValueFactory(new PropertyValueFactory<Punishment, LocalDate>("startDate"));
        punishmentEndColumn.setCellValueFactory(new PropertyValueFactory<Punishment, LocalDate>("endDate"));
        punishmentPaymentColumn.setCellValueFactory(new PropertyValueFactory<Punishment, Integer>("payment"));
        update();


    }


    @FXML
    public void showExtraFields() {
        if (extraInfoCheckBox.isSelected()) {
            extraInfoField1.setVisible(true);
            extraInfoField2.setVisible(true);
            extraInfoField1Label.setVisible(true);
            extraInfoField2Label.setVisible(true);
        } else {
            extraInfoField1.setVisible(false);
            extraInfoField2.setVisible(false);
            extraInfoField1Label.setVisible(false);
            extraInfoField2Label.setVisible(false);
        }
    }

    @FXML
    public void prepareExtraFields() {
        switch (readerType.getValue()) {
            case СТУДЕНТ:
                extraInfoField1Label.setText("Факультет");
                extraInfoField2Label.setText("Группа");
                break;

            case ЧИТАТЕЛЬ:
                extraInfoField1Label.setText("Кафедра");
                extraInfoField2Label.setText("Должность");
                break;
            default:
                break;
        }
    }

    @FXML
    public void askForDataChange() {
    }

    //    @FXML
//    public void orderBook() throws IOException {
//        openOrder();
//    }
    @FXML
    public void showBook() throws IOException {
        openShowBook();
    }

    @FXML
    public void continueBookOrder() throws IOException {
        prolongOrder();
        updateTakenBooks();
    }

    @FXML
    public void cancelOrderBook() throws IOException {
        openOrderCanceling();
        updateOrders();
    }

    @FXML
    public void update() {
        updateOrders();
        updateTakenBooks();
        updatePunishments();
    }

    public void updateOrders() {
        List<BookUIEntity> orderedBooksList = dbRequests.getOrderedBooks(readerId);
        ObservableList<BookUIEntity> orderedBooks = FXCollections.observableArrayList(orderedBooksList);
        bookedBooksTable.setItems(orderedBooks);

    }

    public void updateTakenBooks() {

        List<BookUIEntity> takenBooksList = dbRequests.getTakenBooks(readerId);
        ObservableList<BookUIEntity> takenBooks = FXCollections.observableArrayList(takenBooksList);
        takenBooksTable.setItems(takenBooks);
    }

    public void updatePunishments() {
        List<Punishment> punishmentList = dbRequests.getPunishments(readerId);
        ObservableList<Punishment> punishments = FXCollections.observableArrayList(punishmentList);
        punishmentTable.setItems(punishments);

    }

    public void openOrderCanceling() throws IOException {
        Parent root;
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/user/cancelOrderWindow.fxml"));
            root = loader.load();
//            LoginController c = loader.getController();
//            c.initController();
            CancelOrderController controller = loader.getController();
            controller.init(readerId);
            //controller.readerId=Integer.parseInt(idField.getText());
            Stage stage = new Stage();
            stage.setTitle("Отмена заказа");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public void openOrder() throws IOException {
//        Parent root;
//        try {
//
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/user/orderBookWindow.fxml"));
//            root = loader.load();
////            LoginController c = loader.getController();
////            c.initController();
//            Stage stage = new Stage();
//            stage.setTitle("Заказ книги");
//            stage.setScene(new Scene(root));
//            stage.show();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public void prolongOrder() throws IOException {
        Parent root;
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/user/prolongBookWindow.fxml"));
            root = loader.load();
//            LoginController c = loader.getController();
//            c.initController();

            Stage stage = new Stage();
            stage.setTitle("Продление заказа");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openShowBook() throws IOException {
        Parent root;
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/user/showBookWindow.fxml"));
            root = loader.load();
            ShowBookController c = loader.getController();
            c.initController();
            c.setUserId(readerId);

            Stage stage = new Stage();
            stage.setTitle("Просмотр книг");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
