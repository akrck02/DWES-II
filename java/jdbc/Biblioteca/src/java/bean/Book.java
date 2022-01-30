/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bean;

/**
 * Class representing a new book
 */
public class Book {

    private Integer id;
    private String title;
    private Integer pages;
    private String genre;
    private Integer author;

    private Book() {}

    public void setAuthor(Integer author) {
        this.author = author;
    }

    public Integer getAuthor() {
        return author;
    }
    
    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getGenre() {
        return genre;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
    
    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public Integer getPages() {
        return pages;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    /**
     * Class for book creation
     */
    public static class BookBuilder {

        private Integer id;
        private String title;
        private Integer pages;
        private String genre;
        private Integer author;

        public BookBuilder() {}

        public void setAuthor(Integer author) {
            this.author = author;
        }

        public void setGenre(String genre) {
            this.genre = genre;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public void setPages(Integer pages) {
            this.pages = pages;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Book build() {
            final Book book = new Book();
            
            book.author = this.author;
            book.genre = this.genre;
            book.id = this.id;
            book.pages = this.pages;
            book.title = this.title;
            
            return book;
        }

    }
}
