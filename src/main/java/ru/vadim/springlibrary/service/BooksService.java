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

    public List<Book> findAll(Boolean trueOrFalse) {
        if (trueOrFalse) {
            return booksRepository.findAll(Sort.by("year"));
        } else {
            return booksRepository.findAll();
        }
    }
    public List<Book> findAll(Integer page, Integer booksPerPage, Boolean trueOrFalse) {
        if (trueOrFalse) {
            return booksRepository.findAll(PageRequest.of(page, booksPerPage, Sort.by("year"))).getContent();
        } else {
            return booksRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();
        }
    }

    public Book findById(int id) {
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

    public Optional<Person> findOwner(int id) {
        // Здесь Hibernate.initialize() не нужен,
        // так как владелец (сторона One) загружается не лениво
        return booksRepository.findById(id)
                .map(Book::getOwner);
    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }

    @Transactional
    public void appointPerson(int bookId, Person owner){
        Book book = findById(bookId);
        book.setOwner(owner);
        booksRepository.save(book);
        //        booksRepository.findById(id).ifPresent(
//                book -> {
//                    book.setOwner(selectedPerson);
//                    book.setTakenAt(new Date()); // текущее время
//                }
//        );
    }

    @Transactional
    public void release(int id){
        Book book = booksRepository.findById(id).get();
        book.setOwner(null);
        booksRepository.save(book);

    }

    public List<Book> searchBookBeginWithName(String name) {
        List<Book> books = booksRepository.findByTitleStartingWith(name);
        return books;
    }


}