# 开始
https://github.com/humingliu/junit-mockito-demo.git

clone项目后，使用maven命令: mvn test进行测试。输出如下：

```
-------------------------------------------------------
 T E S T S
-------------------------------------------------------
Running junit.demo.BookServiceImplTest
Tests run: 4, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.639 sec

Results :

Tests run: 4, Failures: 0, Errors: 0, Skipped: 0

[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 5.262 s
[INFO] Finished at: 2017-08-03T10:41:03+08:00
[INFO] Final Memory: 16M/167M
[INFO] ------------------------------------------------------------------------
```

# 框架 junit4+mockito

知识点
1. junit4官方网站：http://junit.org/junit4/
2. mockito官方网站：http://site.mockito.org/

JUnit 是单元测试框架。Mockito 与 JUnit 不同，并不是单元测试框架（这方面 JUnit 已经足够好了），它是用于生成模拟对象或者直接点说，就是”假对象“的工具。两者定位不同，所以一般通常的做法就是联合 JUnit + Mockito 来进行测试。


## 开始

在pom文件中引入依赖
```
<!-- Dependency for JUnit -->
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
            <scope>test</scope>
        </dependency>
<!-- Dependency for Mockito -->
        <dependency>
            <groupId>org.mockito</groupId>
            <artifactId>mockito-core</artifactId>
            <version>2.8.47</version>
            <scope>test</scope>
        </dependency>
```

为了展现如何使用mock框架，我编写一个==数据访问层(DAO)==，该类对应用提供了一套API 接口来访问和修改数据库中的数据。之后在不需要连接数据库的情况下，将==DAO层的方法mock掉==。然后在service类中调用DAO类方法。然后对service层进行单元测试。将数据映射操作从应用代码中分离出来。

### 示例模型类BookDto：


```
package tf56.mingliu.dto;

import java.util.List;

/**
 * Created by mingliu.hu on 2017/8/2.
 */
public class BookDto {
    private String isbn;
    private String title;
    private List<String> authors;
    private String publication;
    private Integer yearOfPublication;
    private Integer numberOfPages;
    private String image;

    public BookDto(String isbn,
                String title,
                List<String> authors,
                String publication,
                Integer yearOfPublication,
                Integer numberOfPages,
                String image){

        this.isbn = isbn;
        this.title = title;
        this.authors = authors;
        this.publication = publication;
        this.yearOfPublication = yearOfPublication;
        this.numberOfPages = numberOfPages;
        this.image = image;

    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public String getPublication() {
        return publication;
    }

    public Integer getYearOfPublication() {
        return yearOfPublication;
    }

    public Integer getNumberOfPages() {
        return numberOfPages;
    }

    public String getImage() {
        return image;
    }
}

```

### 操作Book模型类的DAO类：


```
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


```

当前DAO层以上没有任何功能，我们将对这段代码进行mock打桩。DAO层可能与ORM映射API或者数据库API通讯，而我们不关心的这些API是如何设计的。

### 调用DAO类的Service类


```
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

```
现在进行Service类的单元测试。单元测试中，我们将注入mock数据到BookDAO类，因此我们可以不依赖数据源就可以完成ServiceAPI的测试。

### BookServiceImplTest测试类


```
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

```

1. 首先定义BookDao 与 BookServiceImpl 并在==Dao类中加上@Mock==注解（模拟）和==Service类中加上@InjectMocks==（注入模拟）

```
@Mock
private static BookDao bookDao;
@InjectMocks
private static BookServiceImpl bookServiceImpl;
```

2. 在单元测试@Before方法里面执行下面方法才能初始化@Mock与@InjectMocks对象
```
//初始化mock对象
MockitoAnnotations.initMocks(this);
```

3. 开始mock打桩

```
 //开始mock打桩
when(bookDao.getAllBooks()).thenReturn(Arrays.asList(book1, book2));
when(bookDao.getBook(book1.getIsbn())).thenReturn(book1);
when(bookDao.addBook(book1)).thenReturn(book1.getIsbn());
when(bookDao.updateBook(book1)).thenReturn(book1.getIsbn());
```

4.最后分别测试service类方法

# 注意

1.verify方法 可确保DAO层方法的调用次数


```
//确保dao层的getAllBooks在service层必须且只能执行一次
  verify(bookDao,times(1)).getAllBooks();
```

2.**==Service中引用DAO类必须是全局变量且不能在方法体类实例化。==**

# 联系

author：胡名流

qq：295352050

email：17986@etransfar.com







