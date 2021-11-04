package UI.user;

import Database.DBRequests;
import Database.books.models.Book;
import Database.books.models.BookStatus;
import Database.books.models.BookType;
import Database.files.models.BookGenre;
import Database.files.models.BookTakingRecord;
import Database.places.models.Place;
import UI.BookUIEntity;
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
import java.util.LinkedList;
import java.util.List;

public class ShowBookController {
    @FXML
    TableView<BookUIEntity> bookedBooksTable;
    @FXML
    TableColumn<BookUIEntity, Integer> suitableBookIdColumn;
    @FXML
    TableColumn<BookUIEntity, String> suitableBookNameColumn;
    @FXML
    TableColumn<BookUIEntity, String> suitableBookAuthorColumn;
    @FXML
    TableColumn<BookUIEntity, BookGenre> suitableBookGenreColumn;
    @FXML
    TableColumn<BookUIEntity, String> suitableBookPlaceColumn;
    @FXML
    TableColumn<BookUIEntity, BookStatus> suitableBookStatusColumn;
    @FXML
    TextField bookNameField;
    @FXML
    TextField bookAuthorField;
    @FXML
    ComboBox<BookGenre> bookGenreBox;
    @FXML
    ComboBox<String> bookPlaceBox;
    @FXML
    Button showSuitableBooksButton;
    @FXML
    CheckBox bookAvailableCheck;
    @FXML
    Button orderBookButton;
    private Integer userId;
    public void setUserId(Integer userId){
        this.userId=userId;
    }
    private final DBRequests dbRequests = DBRequests.getInstance();

    public void initController(){
        bookGenreBox.getItems().addAll(BookGenre.IT,BookGenre.АНГЛИЙСКИЙ_ЯЗЫК,BookGenre.БИОЛОГИЯ,
                BookGenre.ГЕОЛОГИЯ,BookGenre.МАТЕМАТИКА,BookGenre.ПРАВО,BookGenre.РУССКИЙ_ЯЗЫК,BookGenre.ЭКОНОМИКА);

        List<Place> placeList=dbRequests.getPlaces();
        for(Place place:placeList)
            bookPlaceBox.getItems().add(place.getPlaceName());

        List<BookUIEntity> books;
        Book filter=new Book(null,null,null,BookStatus.ДОСТУПНО);
        books=BookUIEntity.getBooksUI(dbRequests.getBooksWithFilter(filter));

        suitableBookIdColumn.setCellValueFactory(new PropertyValueFactory<BookUIEntity, Integer>("id"));
        suitableBookNameColumn.setCellValueFactory(new PropertyValueFactory<BookUIEntity, String>("name"));
        suitableBookAuthorColumn.setCellValueFactory(new PropertyValueFactory<BookUIEntity, String>("author"));
        suitableBookGenreColumn.setCellValueFactory(new PropertyValueFactory<BookUIEntity, BookGenre>("genre"));
        suitableBookPlaceColumn.setCellValueFactory(new PropertyValueFactory<BookUIEntity, String>("place"));
        suitableBookStatusColumn.setCellValueFactory(new PropertyValueFactory<BookUIEntity, BookStatus>("status"));

        ObservableList<BookUIEntity> books0 = FXCollections.observableArrayList(books);

        bookedBooksTable.setItems(books0);


    }

    @FXML
    public void orderBook() {
        Parent root;
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/user/orderBookWindow.fxml"));
            root = loader.load();
            OrderBookController c = loader.getController();
            c.setUserId(userId);
            Stage stage = new Stage();
            stage.setTitle("Заказ книги");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void showSuitableBooks() {
        List<BookUIEntity> books;
            BookType type=new BookType(null,bookNameField.getText(),bookAuthorField.getText(),
                    bookGenreBox.getValue());
            Place place=new Place();
            place.setPlaceName(bookPlaceBox.getValue());
            Book filter=new Book(type,null,place,BookStatus.ДОСТУПНО);
            books=BookUIEntity.getBooksUI(dbRequests.getBooksWithFilter(filter));

        suitableBookIdColumn.setCellValueFactory(new PropertyValueFactory<BookUIEntity, Integer>("id"));
        suitableBookNameColumn.setCellValueFactory(new PropertyValueFactory<BookUIEntity, String>("name"));
        suitableBookAuthorColumn.setCellValueFactory(new PropertyValueFactory<BookUIEntity, String>("author"));
        suitableBookGenreColumn.setCellValueFactory(new PropertyValueFactory<BookUIEntity, BookGenre>("genre"));
        suitableBookPlaceColumn.setCellValueFactory(new PropertyValueFactory<BookUIEntity, String>("place"));
        suitableBookStatusColumn.setCellValueFactory(new PropertyValueFactory<BookUIEntity, BookStatus>("status"));

        ObservableList<BookUIEntity> books0 = FXCollections.observableArrayList(books);

        bookedBooksTable.setItems(books0);

    }

}
