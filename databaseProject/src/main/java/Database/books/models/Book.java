package Database.books.models;

import Database.places.models.Place;

public class Book {
    private BookType type;
    private Integer bookId;
    private Place place;
    private BookStatus status;

    public Book(){

    }
    public Book(BookType type,Integer bookId,Place place, BookStatus status){
        if(type!=null)
        setType(type);
        if(bookId!=null)
        setBookId(bookId);
        if(place!=null)
        setPlace(place);
        if(status!=null)
        setStatus(status);

    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public BookStatus getStatus() {
        return status;
    }

    public void setType(BookType type){
        this.type=type;
    }

    public BookType getType(){
        return type;
    }

    public void setStatus(BookStatus status) {
        this.status = status;
    }
}
