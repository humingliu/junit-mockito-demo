package junit.demo;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tf56.mingliu.dao.BookDao;
import tf56.mingliu.dto.BookDto;
import tf56.mingliu.service.BookServiceImpl;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

/**
 * Created by mingliu.hu on 2017/8/2.
 */
public class BookServiceImplTest {

    @Mock
    private static BookDao bookDao;
    @InjectMocks
    private static BookServiceImpl bookServiceImpl;
    private static BookDto book1;
    private static BookDto book2;

    static {
        book1 = new BookDto("8131721019","Compilers Principles",
                Arrays.asList("D. Jeffrey Ulman","Ravi Sethi", "Alfred V. Aho", "Monica S. Lam"),
                "Pearson Education Singapore Pte Ltd", 2008,1009,"BOOK_IMAGE");

        book2 = new BookDto("9788183331630","Let Us C 13th Edition",
                Arrays.asList("Yashavant Kanetkar"),"BPB PUBLICATIONS", 2012,675,"BOOK_IMAGE");
    }

    @Before
    public void setUp(){
        //初始化mock对象
        MockitoAnnotations.initMocks(this);

        //开始mock打桩
        when(bookDao.getAllBooks()).thenReturn(Arrays.asList(book1, book2));
        when(bookDao.getBook(book1.getIsbn())).thenReturn(book1);
        when(bookDao.addBook(book1)).thenReturn(book1.getIsbn());
        when(bookDao.updateBook(book1)).thenReturn(book1.getIsbn());
    }

    /**
    {@link BookServiceImpl#getAllBooks()}
    **/
    @Test
    public void testGetAllBooks() throws Exception {

        List<BookDto> allBooks = bookServiceImpl.getAllBooks();

        //确保dao层的getAllBooks在service层必须且只能执行一次
        verify(bookDao,times(1)).getAllBooks();

        assertEquals(2, allBooks.size());

        BookDto myBook = allBooks.get(0);

        assertEquals("8131721019", myBook.getIsbn());
        assertEquals("Compilers Principles", myBook.getTitle());
        assertEquals(4, myBook.getAuthors().size());
        assertEquals((Integer)2008, myBook.getYearOfPublication());
        assertEquals((Integer) 1009, myBook.getNumberOfPages());
        assertEquals("Pearson Education Singapore Pte Ltd", myBook.getPublication());
        assertEquals("BOOK_IMAGE", myBook.getImage());
    }

    /**
     {@link BookServiceImpl#getBook(String)}
     **/
    @Test
    public void testGetBook(){
        String isbn = "8131721019";
        BookDto myBook = bookServiceImpl.getBook(isbn);

        //确保dao层的getBook在service层必须且只能执行一次
        verify(bookDao,times(1)).getBook(isbn);

        assertNotNull(myBook);
        assertEquals(isbn, myBook.getIsbn());
        assertEquals("Compilers Principles", myBook.getTitle());
        assertEquals(4, myBook.getAuthors().size());
        assertEquals("Pearson Education Singapore Pte Ltd", myBook.getPublication());
        assertEquals((Integer)2008, myBook.getYearOfPublication());
        assertEquals((Integer)1009, myBook.getNumberOfPages());

    }

    /**
     {@link BookServiceImpl#addBook(BookDto)}
     **/
    @Test
    public void testAddBook(){
        String isbn = bookServiceImpl.addBook(book1);

        //确保dao层的addBook在service层必须且只能执行一次
        verify(bookDao,times(1)).addBook(book1);

        assertNotNull(isbn);
        assertEquals(book1.getIsbn(), isbn);
    }

    /**
     {@link BookServiceImpl#updateBook(BookDto)}
     **/
    @Test
    public void testUpdateBook(){
        String isbn = bookServiceImpl.updateBook(book1);

        //确保dao层的updateBook在service层必须且只能执行一次
        verify(bookDao,times(1)).updateBook(book1);

        assertNotNull(isbn);
        assertEquals(book1.getIsbn(), isbn);
    }
}
