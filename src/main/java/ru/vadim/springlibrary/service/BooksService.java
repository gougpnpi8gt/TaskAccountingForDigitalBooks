package ru.vadim.springlibrary.service;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vadim.springlibrary.entity.Book;
import ru.vadim.springlibrary.entity.Person;
import ru.vadim.springlibrary.repository.BooksRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@Transactional(readOnly = true)
public class BooksService {

    final BooksRepository booksRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Book> findAll(boolean year) {
        if (year) {
            return booksRepository.findAll(Sort.by("year"));
        } else {
            return booksRepository.findAll();
        }
    }

    public List<Book> findAll(Integer page, Integer booksPerPage, boolean year) {
        if (year) {
            return booksRepository.findAll(PageRequest.of(page, booksPerPage, Sort.by("year"))).getContent();
        } else {
            return booksRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();
        }
    }

    public Book findOneBook(int id) {
        Optional<Book> book = booksRepository.findById(id);
        return book.orElse(null);
    }

    @Transactional
    public void update(int id, Book updatedBook) {
        updatedBook.setId(id);
        updatedBook.setOwner(booksRepository.findById(id).get().getOwner());
        booksRepository.save(updatedBook);
    }

    @Transactional
    public void save(Book book) {

        booksRepository.save(book);
    }

    public Person findOwner(int id) {
        return booksRepository.findById(id).map(Book::getOwner).orElse(null);
        /* map - Метод map() используется, когда у вас есть объект Optional и нужно выполнить некоторую операцию
    над содержащимся значением, в результате чего получится другое значение. Он принимает функцию в качестве аргумента.
    Эта функция применяется к значению внутри Optional, если оно существует.
         */
    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }

    @Transactional
    public void appointPerson(int bookId, Person owner) {
        Book book = findOneBook(bookId);
        book.setOwner(owner);
        book.setDateWhenTook(new Date());
        booksRepository.save(book);
    }

    @Transactional
    public void release(int id) {
        Book book = booksRepository.findById(id).get();
        book.setOwner(null);
        book.setDateWhenTook(null);
        booksRepository.save(book);

    }

    public List<Book> searchBookBeginWithName(String name) {
        return booksRepository.findByTitleStartingWith(name);
    }

}