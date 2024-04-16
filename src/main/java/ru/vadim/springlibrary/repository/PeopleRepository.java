package ru.vadim.springlibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vadim.springlibrary.entity.Book;
import ru.vadim.springlibrary.entity.Person;

import java.util.List;
import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
    List<Book> findAllByBooksOrderById(int id);
    Optional<Person> findByFullName(String fullName);
}
