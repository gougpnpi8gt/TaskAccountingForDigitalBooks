package ru.vadim.springlibrary.service;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vadim.springlibrary.entity.Book;
import ru.vadim.springlibrary.entity.Person;
import ru.vadim.springlibrary.repository.BooksRepository;
import ru.vadim.springlibrary.repository.PeopleRepository;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@Transactional(readOnly = true)
public class PeopleService {
    final BooksRepository booksRepository;

    final PeopleRepository peopleRepository;
    @Autowired
    public PeopleService(BooksRepository booksRepository,
                         PeopleRepository peopleRepository) {
        this.booksRepository = booksRepository;
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findAll(){
        return peopleRepository.findAll();
    }

    public Person findOnePerson(int id){
        return peopleRepository.findById(id).get();
    }

    public List<Book> findAllByBooksForPerson(int id) {
        Optional<Person> person = peopleRepository.findById(id);
        if (person.isPresent()) {
            person.get().getBooks().forEach(book -> {
                long diffInMillies = Math.abs(book.getDateWhenTook().getTime() - new Date().getTime());
                if (diffInMillies > 864000000) // 864000000 милисекунд = 10 суток
                    book.setDelay(true); // книга просрочена
            });
            return person.get().getBooks();
        } else {
            return Collections.emptyList();
        }
    }

    public Optional<Person> findByFullName(String fullName) {
        return peopleRepository.findByFullName(fullName);
    }

    @Transactional
    public void save(Person person){
        peopleRepository.save(person);
    }

    @Transactional
    public void update(int id, Person updatePerson){
        updatePerson.setId(id);
        peopleRepository.save(updatePerson);
    }
    @Transactional
    public void delete(int id){
        peopleRepository.deleteById(id);
    }

}
