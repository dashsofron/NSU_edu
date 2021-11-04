package UI.books;

        import Database.DBRequests;
        import Database.books.models.Book;
        import Database.books.models.BookStatus;
        import Database.books.models.BookType;
        import Database.files.models.BookGenre;
        import Database.places.models.Place;
        import javafx.fxml.FXML;
        import javafx.scene.control.*;

        import java.util.LinkedList;
        import java.util.List;

public class UpdateBookController {
    @FXML
    CheckBox getOldInfoCheck;
    @FXML
    CheckBox categoryCheck;
    @FXML
    ComboBox<String> typeBox;
    @FXML
    ComboBox<String> placeBox;
    @FXML
    TextField nameField;
    @FXML
    TextField authorField;
    @FXML
    ComboBox<BookGenre> genreBox;
    @FXML
    Button updateButton;
    @FXML
    TextField idFIeld;
    @FXML
    TextArea infoArea;

    String infoStart = "Нужно ввести или выбрать:\n ";

    private final DBRequests dbRequests = DBRequests.getInstance();
    public void initController() {
        List<String> names=new LinkedList<>();
        for(Place place:dbRequests.getPlaces())
            names.add(place.getPlaceName());
        placeBox.getItems().addAll(names);
        names.clear();
        for(BookType type:dbRequests.getBookTypes())
            names.add(type.getBookName());
        typeBox.getItems().addAll(names);
        genreBox.getItems().addAll(BookGenre.IT,BookGenre.ПРАВО);
    }

    @FXML
    public void updateBook(){
        infoArea.clear();
        String info = "";
        if(categoryCheck.isSelected()){
            if (idFIeld.getText().equals("")||authorField.getText().equals("") || nameField.getText().equals("") || genreBox.getValue() == null ||
                    placeBox.getValue() == null) {

                if (idFIeld.getText().equals(""))
                    info += "Идентификатор места хранения\n";
                if (authorField.getText().equals(""))
                    info += "Автора\n";
                if (nameField.getText().equals(""))
                    info += "Название книги\n";
                if (genreBox.getValue() == null)
                    info += "Жанр\n";
                if (placeBox.getValue() == null)
                    info += "Место хранения\n";
                infoArea.setText(infoStart + info);
                return;
            }
            BookType bookType=new BookType();
            bookType.setBookAuthor(authorField.getText());
            bookType.setBookName(nameField.getText());
            bookType.setGenre(genreBox.getValue());
            dbRequests.addBookType(bookType);
            Place place = dbRequests.getPlaceByName(placeBox.getValue());
            Book book=new Book();
            book.setBookId(Integer.valueOf(idFIeld.getText()));
            book.setStatus(BookStatus.ДОСТУПНО);
            BookType type=new BookType();
            type.setBookTypeId(bookType.getBookTypeId());
            type.setBookAuthor(bookType.getBookAuthor());
            type.setBookName(bookType.getBookName());
            type.setGenre(bookType.getGenre());
            book.setType(type);
            book.setPlace(place);
//            try {
                dbRequests.updateBook(book);
                infoArea.setText("Выполнено");
                typeBox.getItems().addAll(bookType.getBookName());
//            }
//            catch (Exception ignore){
//            }
        }
        else {
            if(placeBox.getValue()==null||typeBox.getValue()==null){
                if (typeBox.getValue() == null)
                    info += "Тип книги\n";
                if (placeBox.getValue() == null)
                    info += "Место хранения\n";
                infoArea.setText(infoStart + info);
                return;
            }
            Place place = dbRequests.getPlaceByName(placeBox.getValue());
            BookType type = dbRequests.getBookTypeByName(typeBox.getValue());
            Book book = new Book();
            book.setBookId(Integer.valueOf(idFIeld.getText()));
            book.setType(type);
            book.setPlace(place);
            book.setStatus(BookStatus.ДОСТУПНО);
//            try {
                dbRequests.updateBook(book);
                infoArea.setText("Выполнено");
//            }
//            catch (Exception ignore){
//
//            }
        }
    }

    @FXML
    public void openType() {
        if (categoryCheck.isSelected()) {
            typeBox.setDisable(true);
            genreBox.setVisible(true);
            nameField.setVisible(true);
            authorField.setVisible(true);
        } else {
            typeBox.setDisable(false);
            genreBox.setVisible(false);
            nameField.setVisible(false);
            authorField.setVisible(false);
        }
    }

}
