package ru.vadim.springlibrary.controllers;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.vadim.springlibrary.entity.Book;
import ru.vadim.springlibrary.entity.Person;
import ru.vadim.springlibrary.service.BooksService;
import ru.vadim.springlibrary.service.PeopleService;

import javax.validation.Valid;

@Controller
@RequestMapping("/books")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ControllerBook {
    final BooksService booksService;
    final PeopleService peopleService;

    @Autowired
    public ControllerBook(BooksService booksService,
                          PeopleService peopleService) {
        this.booksService = booksService;
        this.peopleService = peopleService;
    }
    @GetMapping()
    public String index(Model model,
                        @RequestParam(value = "page", required = false) Integer page,
                        @RequestParam(value = "books_per_page", required = false) Integer booksPerPage,
                        @RequestParam(value = "sort_by_year", required = false) boolean year) {
        // Не ставить Boolean, возникнет ошибка сервера
        if (page == null || booksPerPage == null) {
            model.addAttribute("books", booksService.findAll(year));
        } else {
            model.addAttribute("books", booksService.findAll(page, booksPerPage, year));
        }
        return "allBooks/index";
    }
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id,
                       Model model,
                       @ModelAttribute("person") Person person){
        model.addAttribute("book", booksService.findOneBook(id));
        Person owner = booksService.findOwner(id);
        if (owner != null) {
            model.addAttribute("owner", owner);
        } else {
            model.addAttribute("people", peopleService.findAll());
        }
        return "allBooks/show";
    }
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("book", booksService.findOneBook(id));
        return "allBooks/edit";
    }
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") Book updatedBook, @PathVariable("id") int id){
        booksService.update(id, updatedBook);
        return "redirect:/books";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book){
        return "allBooks/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book){
        booksService.save(book);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/choose")
    public String choosePersonByBook(@ModelAttribute("person") Person person,
                                     @PathVariable("id") int id){
        booksService.appointPerson(id, person);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id){
        booksService.release(id);
        return "redirect:/books/" + id;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        booksService.delete(id);
        return "redirect:/books";
    }

    @GetMapping("/search")
    public String search(){
        return "allBooks/search";
    }

    @PostMapping("/search")
    public String searchBook(@RequestParam("query") String query, Model model){
        model.addAttribute("books", booksService.searchBookBeginWithName(query));
        return "allBooks/search";
    }
}
