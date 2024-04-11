package ru.vadim.springlibrary.controllers;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.vadim.springlibrary.DAO.PersonBook;
import ru.vadim.springlibrary.models.Person;
import ru.vadim.springlibrary.util.PersonValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ControllerPerson {
    final PersonValidator personValidator;
    final PersonBook personBook;

    @Autowired
    public ControllerPerson(PersonValidator personValidator,
                            PersonBook personBook) {
        this.personValidator = personValidator;
        this.personBook = personBook;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("people", personBook.index());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(Model model, @PathVariable("id") int id){
        model.addAttribute("person", personBook.show(id));
        model.addAttribute("books", personBook.bookList(id));
        return "people/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person){
        return "people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person")
                         @Valid Person person,
                         BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            return "people/new";
        }
        personBook.save(person);
        return "redirect:/people";
    }
    @GetMapping("/{id}/edit")
    public String edit(Model model,
                       @PathVariable("id") int id){
        model.addAttribute("person", personBook.show(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "people/edit";
        personBook.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id") int id){
        personBook.delete(id);
        return "redirect:/people";
    }

}
