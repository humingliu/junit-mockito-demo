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
