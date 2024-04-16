package ru.vadim.springlibrary.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import ru.vadim.springlibrary.DAO.PersonBook;
import ru.vadim.springlibrary.models.Person;
@Component
public class PersonValidator implements org.springframework.validation.Validator {
    private final PersonBook personBook;
    @Autowired
    public PersonValidator(PersonBook personBook) {
        this.personBook = personBook;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;
        if (personBook.show(person.getFullName()).isPresent()){
            errors.rejectValue("fullName", "", "This fullName is already taken");
        }
    }
}
