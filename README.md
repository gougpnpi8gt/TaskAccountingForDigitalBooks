Задачи:
1) Переписать Проект №1 с использованием Hibernate и Spring Data JPA. В
проекте не должно быть ни одного SQL запроса. Должны быть реализованы
сущности (@Entity) Книга и Человек, репозитории и сервисы. PersonDAO и
BookDAO должны быть пустыми и не должны использоваться, вся работа с БД
через сервисы.
Новый функционал:
1) Добавить пагинацию для книг.
Книг может быть много и они могут не помещаться на одной странице в
браузере. Чтобы решить эту проблему, метод контроллера должен уметь
выдавать не только все книги разом, но и разбивать выдачу на страницы.
2) Добавить сортировку книг по году. Метод контроллера должен уметь выдавать книги в отсортированном порядке.
3) Создать страницу поиска книг. Вводим в поле на странице начальные буквы
названия книги, получаем полное название книги и имя автора. 
Также, если книга сейчас находится у кого-то, получаем имя этого человека.
4) Добавить автоматическую проверку на то, что человек просрочил возврат книги.