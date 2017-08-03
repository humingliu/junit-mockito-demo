package tf56.mingliu.service;

import tf56.mingliu.dao.BookDao;
import tf56.mingliu.dto.BookDto;

import java.util.List;

/**
 * Created by mingliu.hu on 2017/8/2.
 */
public class BookServiceImpl {
    private BookDao bookDao = BookDao.getInstance();

    public List<BookDto> getAllBooks(){
        return bookDao.getAllBooks();
    }

    public BookDto getBook(String isbn){
        return bookDao.getBook(isbn);
    }

    public String addBook(BookDto book){
        return bookDao.addBook(book);
    }

    public String updateBook(BookDto book){
        return bookDao.updateBook(book);
    }

}
