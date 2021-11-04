package UI.books;

import Database.DBRequests;
import Database.books.models.Book;
import Database.books.models.BookStatus;
import Database.books.models.BookType;
import Database.files.models.BookGenre;
import Database.places.models.Place;
import UI.BookUIEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.LinkedList;
import java.util.List;

public class GetBookController {
    private final DBRequests dbRequests = DBRequests.getInstance();
    @FXML
    TableView<BookUIEntity> booksTable;
    @FXML
    TableColumn<BookUIEntity, Integer> idColumn;
    @FXML
    TableColumn<BookUIEntity, String> nameColumn;
    @FXML
    TableColumn<BookUIEntity, String> authorColumn;
    @FXML
    TableColumn<BookUIEntity, BookGenre> genreColumn;
    @FXML
    TableColumn<BookUIEntity, String> placeColumn;
    @FXML
    TableColumn<BookUIEntity, BookStatus> statusColumn;
    @FXML
    ComboBox<String> placeBox;
    @FXML
    ComboBox<BookGenre> genreBox;
    @FXML
    ComboBox<BookStatus> statusBox;
    @FXML
    Button getBooksButton;
    @FXML
    TextField idField;
    @FXML
    TextField nameField;
    @FXML
    TextField authorField;

    public void initController() {
        List<String> plNames = new LinkedList<>();
        for (Place place : dbRequests.getPlaces())
            plNames.add(place.getPlaceName());
        placeBox.getItems().addAll(plNames);
        genreBox.getItems().setAll(BookGenre.IT, BookGenre.АНГЛИЙСКИЙ_ЯЗЫК, BookGenre.БИОЛОГИЯ, BookGenre.ГЕОЛОГИЯ,
                BookGenre.ЭКОНОМИКА, BookGenre.РУССКИЙ_ЯЗЫК, BookGenre.ПРАВО, BookGenre.МАТЕМАТИКА);
        statusBox.getItems().setAll(BookStatus.ПОТЕРЯНО, BookStatus.ЗАКАЗАНО, BookStatus.ДОСТУПНО, BookStatus.ВЗЯТО, BookStatus.ГОТОВИТСЯ);
        getBooks();
    }

    @FXML
    public void getBooks() {
        List<BookUIEntity> books;
        if (!idField.getText().equals("")) {
            books = new LinkedList<>();
            Book book = dbRequests.getBook(Integer.parseInt(idField.getText()));
            books.add(BookUIEntity.getBookUI(book));
        } else {
            BookType type = new BookType(null, nameField.getText(), authorField.getText(), genreBox.getValue());
            Place place = new Place();
            place.setPlaceName(placeBox.getValue());
            Book filter = new Book(type, null, place, statusBox.getValue());
            books = BookUIEntity.getBooksUI(dbRequests.getBooksWithFilter(filter));
        }
        idColumn.setCellValueFactory(new PropertyValueFactory<BookUIEntity, Integer>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<BookUIEntity, String>("name"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<BookUIEntity, String>("author"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<BookUIEntity, BookGenre>("genre"));
        placeColumn.setCellValueFactory(new PropertyValueFactory<BookUIEntity, String>("place"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<BookUIEntity, BookStatus>("status"));
        ObservableList<BookUIEntity> books0 = FXCollections.observableArrayList(books);
        booksTable.setItems(books0);
    }

    @FXML
    public void dropFilter() {
        placeBox.setValue(null);
        genreBox.setValue(null);
        genreBox.setValue(null);
        statusBox.setValue(null);
        idField.clear();
        nameField.clear();
        authorField.clear();
    }
}
