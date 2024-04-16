package ru.vadim.springlibrary.models;

import lombok.*;
import lombok.experimental.FieldDefaults;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Person {

    int id;

    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(min = 2, max = 100, message = "Имя должно быть от 2 до 100 символов длиной")
    String fullName;

    @Min(value = 1900, message = "Год рождения должен быть больше, чем 1900")
    int yearOfBirth;
}
