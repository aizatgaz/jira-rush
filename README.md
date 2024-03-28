## [REST API](http://localhost:8080/doc)

## Концепция: 

- Spring Modulith
    - [Spring Modulith: достигли ли мы зрелости модульности](https://habr.com/ru/post/701984/)
    - [Introducing Spring Modulith](https://spring.io/blog/2022/10/21/introducing-spring-modulith)
    - [Spring Modulith - Reference documentation](https://docs.spring.io/spring-modulith/docs/current-SNAPSHOT/reference/html/)

```
  url: jdbc:postgresql://localhost:5432/jira
  username: jira
  password: JiraRush
```

- Есть 2 общие таблицы, на которых не fk
    - _Reference_ - справочник. Связь делаем по _code_ (по id нельзя, тк id привязано к окружению-конкретной базе)
    - _UserBelong_ - привязка юзеров с типом (owner, lead, ...) к объекту (таска, проект, спринт, ...). FK вручную будем
      проверять

## Аналоги

- https://java-source.net/open-source/issue-trackers

## Тестирование
[common](src%2Fmain%2Fjava%2Fcom%2Fjavarush%2Fjira%2Fcommon)
- https://habr.com/ru/articles/259055/

Список выполненных задач:
1. Удалить социальные сети: vk, yandex.

2. Вынести чувствительную информацию в отдельный проперти файл (application-authorization.yaml):
   • логин
   • пароль БД
   • идентификаторы для OAuth регистрации/авторизации
   • настройки почты

3. Переделать тесты так, чтоб во время тестов использовалась in memory БД (H2), а не PostgreSQL. Для этого нужно определить 
2 бина, и выборка какой из них использовать должно определяться активным профилем Spring. H2 не поддерживает все фичи, 
которые есть у PostgreSQL, поэтому тебе прийдется немного упростить скрипты с тестовыми данными.
Контейнер: docker run -d -p 9092:9092 --name my-h2-database -v /path/to/data:/data oscarfonts/h2:latest

4. Написать тесты для всех публичных методов контроллера ProfileRestController. Хоть методов только 2, но тестовых методов 
должно быть больше, т.к. нужно проверить success and unsuccess path.

5. Сделать рефакторинг метода com.javarush.jira.bugtracking.attachment.FileUtil#upload чтоб он использовал современный 
подход для работы с файловой системмой.

6. Добавить новый функционал: добавления тегов к задаче (REST API + реализация на сервисе).

7. Пытался настроить docker-compose.yaml, но что-то не получается пофиксить ошибку org.thymeleaf.TemplateEngine.process