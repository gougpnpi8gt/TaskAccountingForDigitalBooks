package ru.vadim.springlibrary.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "Book")
public class Book {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @NotEmpty(message = "Название книги не должно быть пустым")
    @Size(min = 2, max = 100, message = "Название книги должно быть от 2 до 100 символов длиной")
    @Column(name = "title")
    String title;

    @NotEmpty(message = "Автор не должен быть пустым")
    @Size(min = 2, max = 100, message = "Имя автора должно быть от 2 до 100 символов длиной")
    @Column(name = "author")
    String author;

    @Min(value = 1790, message = "Год должен быть больше, чем 1790")
    @Column(name = "year")
    int year;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    Person owner;

    @Column(name = "when_took")
    @Temporal(TemporalType.TIMESTAMP)
    Date dateWhenTook;

    @Transient
    boolean isDelay;

    public Book() {

    }

    public Book(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }
}