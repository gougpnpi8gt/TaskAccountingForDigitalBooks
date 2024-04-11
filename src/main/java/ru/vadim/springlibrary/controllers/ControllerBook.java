package ru.vadim.springlibrary.controllers;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.vadim.springlibrary.DAO.BookDao;
import ru.vadim.springlibrary.DAO.PersonBook;
import ru.vadim.springlibrary.models.Book;
import ru.vadim.springlibrary.models.Person;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/books")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ControllerBook {
    final BookDao bookDao;
    final PersonBook personBook;
    @Autowired
    public ControllerBook(BookDao bookDao,
                          PersonBook personBook) {
        this.bookDao = bookDao;
        this.personBook = personBook;
    }
    @GetMapping()
    public String index(Model model){
        model.addAttribute("books", bookDao.index());
        return "allBooks/index";
    }
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id,
                       Model model,
                       @ModelAttribute("person") Person person){
        model.addAttribute("book", bookDao.show(id));
        Optional<Person> owner = bookDao.getBookOwner(id);
        if (owner.isPresent()) {
            model.addAttribute("owner", owner.get());
        } else {
            model.addAttribute("people", personBook.index());
        }
        return "allBooks/show";
    }
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("book", bookDao.show(id));
        return "allBooks/edit";
    }
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") Book book, @PathVariable("id") int id){
        bookDao.update(book, id);
        return "redirect:/books";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book){
        return "allBooks/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book){
        bookDao.save(book);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/choose")
    public String choosePersonByBook(@ModelAttribute("person") Person person,
                                     @PathVariable("id") int id){
        bookDao.designate(id, person);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id){
        bookDao.release(id);
        return "redirect:/books/" + id;
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        bookDao.delete(id);
        return "redirect:/books";
    }
}
