package ru.vadim.springlibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vadim.springlibrary.entity.Book;
import ru.vadim.springlibrary.entity.Person;

import java.util.Optional;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {

}
