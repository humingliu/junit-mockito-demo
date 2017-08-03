package tf56.mingliu.dao;

import tf56.mingliu.dto.BookDto;

import java.util.Collections;
import java.util.List;

/**
 * Created by mingliu.hu on 2017/8/2.
 */
public class BookDao {
    private static BookDao BookDao = new BookDao();

    public List<BookDto> getAllBooks(){
        return Collections.EMPTY_LIST;
    }

    public BookDto getBook(String isbn){
        return null;
    }

    public String addBook(BookDto book){
        return "";
    }

    public String updateBook(BookDto book){
        return "";
    }

    public static BookDao getInstance(){
        return BookDao;
    }
}
