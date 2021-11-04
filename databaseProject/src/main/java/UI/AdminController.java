package UI;

import Database.DBInit;
import UI.admin.UpdateBookStatusController;
import UI.books.AddBookController;
import UI.books.GetBookController;
import UI.books.UpdateBookController;
import UI.places.AddPlaceController;
import UI.places.GetPlaceController;
import UI.places.UpdatePlaceController;
import UI.punishments.*;
import UI.readers.AddReaderController;
import UI.readers.GetReaderController;
import UI.readers.UpdateReaderController;
import UI.records.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

public class AdminController {
    @FXML
    Button addObjectButton;
    @FXML
    Button deleteObjectButton;
    @FXML
    Button updateObjectButton;
    @FXML
    ComboBox<ObjectType> objectTypeBox;
    @FXML
    Button getObjectsButton;
    @FXML
    Button changeBookStatusButton;
    @FXML
    Button updateSchemaButton;

    public void initController() {
        objectTypeBox.getItems().addAll(ObjectType.ВЗЯТИЕ_КНИГИ, ObjectType.КНИГА, ObjectType.МЕСТО, ObjectType.НАКАЗАНИЕ, ObjectType.ЧИТАТЕЛЬ);
    }

    @FXML
    public void addObject() {
        switch (objectTypeBox.getValue()) {
            case ЧИТАТЕЛЬ:
                openAddReaderWindow();
                break;
            case КНИГА:
                openAddBookWindow();
                break;

            case МЕСТО:
                openAddPlaceWindow();
                break;

            case НАКАЗАНИЕ:
                addPunishment();
                break;

            case ВЗЯТИЕ_КНИГИ:
                addRecord();
                break;

        }

    }

    @FXML
    public void deleteObject() {
        switch (objectTypeBox.getValue()) {
            case ЧИТАТЕЛЬ:
                openDeleteReaderWindow();
                break;

            case КНИГА:
                openDeleteBookWindow();
                break;

            case МЕСТО:
                openDeletePlaceWindow();
                break;

            case НАКАЗАНИЕ:
                deletePunishment();
                break;

            case ВЗЯТИЕ_КНИГИ:
                deleteRecord();
                break;

        }
    }

    @FXML
    public void updateObject() {
        switch (objectTypeBox.getValue()) {
            case ЧИТАТЕЛЬ:
                openUpdateReaderWindow();
                break;

            case КНИГА:
                openUpdateBookWindow();
                break;

            case МЕСТО:
                openUpdatePlaceWindow();
                break;

            case НАКАЗАНИЕ:
                updatePunishment();
                break;

            case ВЗЯТИЕ_КНИГИ:
                updateRecord();
                break;
        }
    }

    @FXML
    public void getObjects() {
        switch (objectTypeBox.getValue()) {
            case ЧИТАТЕЛЬ:
                openGetReadersWindow();
                break;

            case КНИГА:
                openGetBooksWindow();
                break;

            case МЕСТО:
                openGetPlaceWindow();
                break;

            case НАКАЗАНИЕ:
                getPunishment();
                break;

            case ВЗЯТИЕ_КНИГИ:
                getRecord();
                break;
        }
    }

    @FXML
    public void changeBookStatus() {
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin/updateBookStatusWindow.fxml"));
            root = loader.load();
            UpdateBookStatusController c = loader.getController();
            c.initController();
            Stage stage = new Stage();
            stage.setTitle("Обновить статус книги");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void updateSchema() throws UnsupportedEncodingException, SQLException {
        DBInit dbInit = DBInit.getInstance();
        dbInit.init();
    }

    public void openGetReadersWindow() {
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/readers/getReadersInfo.fxml"));
            root = loader.load();
            GetReaderController c = loader.getController();
            c.initController();
            Stage stage = new Stage();
            stage.setTitle("Читатели");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void
    openAddReaderWindow() {
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/readers/addReaderWindow.fxml"));
            root = loader.load();
            AddReaderController c = loader.getController();
            c.initController();
            Stage stage = new Stage();
            stage.setTitle("Добавление читателя");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void
    openUpdateReaderWindow() {
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/readers/updateReaderWindow.fxml"));
            root = loader.load();
            UpdateReaderController c = loader.getController();
            c.initController();
            Stage stage = new Stage();
            stage.setTitle("Обновление читателя");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void
    openDeleteReaderWindow() {
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/readers/deleteReaderWindow.fxml"));
            root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Удаление читателя");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void addPunishment() {
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/punishments/addPunishment.fxml"));
            root = loader.load();
            AddPunishmentController c = loader.getController();
            c.initController();
            Stage stage = new Stage();
            stage.setTitle("Добавить наказание");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deletePunishment() {
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/punishments/deletePunishment.fxml"));
            root = loader.load();
            DeletePunishmentController c = loader.getController();
            //c.initController();
            Stage stage = new Stage();
            stage.setTitle("Удалить наказание");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updatePunishment() {
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/punishments/updatePunishment.fxml"));
            root = loader.load();
            UpdatePunishmentController c = loader.getController();
            c.initController();
            Stage stage = new Stage();
            stage.setTitle("Обновить наказание");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getPunishment() {
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/punishments/getPunishment.fxml"));
            root = loader.load();
            GetPunishmentController c = loader.getController();
            c.initController();
            Stage stage = new Stage();
            stage.setTitle("Получить наказание");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addRecord() {
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/records/addRecord.fxml"));
            root = loader.load();
            AddRecordsController c = loader.getController();
            c.initController();
            Stage stage = new Stage();
            stage.setTitle("Добавить записи");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteRecord() {
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/records/deleteRecord.fxml"));
            root = loader.load();
            DeleteRecordsController c = loader.getController();
            //c.initController();
            Stage stage = new Stage();
            stage.setTitle("Удалить запись");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getRecord() {
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/records/getRecord.fxml"));
            root = loader.load();
            GetRecordsController c = loader.getController();
            c.initController();
            Stage stage = new Stage();
            stage.setTitle("Получить запись");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateRecord() {
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/records/updateRecord.fxml"));
            root = loader.load();
            UpdateRecordsController c = loader.getController();
            c.initController();
            Stage stage = new Stage();
            stage.setTitle("Обновить запись");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openAddPlaceWindow() {
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/places/addPlace.fxml"));
            root = loader.load();
            AddPlaceController c = loader.getController();
            c.initController();
            Stage stage = new Stage();
            stage.setTitle("Добавление места хранения");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void openDeletePlaceWindow() {
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/places/deletePlace.fxml"));
            root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Удаление места хранения");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openUpdatePlaceWindow() {
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/places/updatePlace.fxml"));
            root = loader.load();
            UpdatePlaceController c = loader.getController();
            c.initController();
            Stage stage = new Stage();
            stage.setTitle("Обновление места хранения");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openGetPlaceWindow() {
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/places/getPlaces.fxml"));
            root = loader.load();
            GetPlaceController c=loader.getController();
            c.initController();
            Stage stage = new Stage();
            stage.setTitle("Получение места хранения");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openGetPlacesByParamsWindow() {

    }


    public void openAddBookWindow() {
        {
            Parent root;
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/books/addBooks.fxml"));
                root = loader.load();
                AddBookController c = loader.getController();
                c.initController();
                Stage stage = new Stage();
                stage.setTitle("Добавление книг");
                stage.setScene(new Scene(root));
                stage.initModality(Modality.APPLICATION_MODAL);

                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void openDeleteBookWindow() {
        {
            Parent root;
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/books/deleteBooks.fxml"));
                root = loader.load();
                Stage stage = new Stage();
                stage.setTitle("Удаление книг");
                stage.setScene(new Scene(root));
                stage.initModality(Modality.APPLICATION_MODAL);

                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void openUpdateBookWindow() {
        {
            Parent root;
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/books/updateBooks.fxml"));
                root = loader.load();
                UpdateBookController c = loader.getController();
                c.initController();
                Stage stage = new Stage();
                stage.setTitle("Обновление книг");
                stage.setScene(new Scene(root));
                stage.initModality(Modality.APPLICATION_MODAL);

                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void openGetBooksWindow() {
        {
            Parent root;
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/books/getBooks.fxml"));
                root = loader.load();
                GetBookController c=loader.getController();
                c.initController();
                Stage stage = new Stage();
                stage.setTitle("Получение книг");
                stage.setScene(new Scene(root));
                stage.initModality(Modality.APPLICATION_MODAL);

                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
