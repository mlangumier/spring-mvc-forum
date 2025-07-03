# Forum posts - Spring Boot MVC demo app

## Objectives

Implement this class diagram and its functionalities, including authentication and role-specific features.  
![Class diagram](/assets/uml_class_diagram.png)

### Set up

- [x] Dependencies:
    - Spring DevTools
    - Spring Web
    - Thymeleaf
    - Spring Validation
    - Spring Security
    - Spring Data JPA
    - MySQL Driver for our main database
    - H2 Database for a temporary testing database
- [x] Configure the `application.properties` file

### Entities

- [x] Create the entities with JPA annotations, including their relations. Specifications:
    - Ex relation: Post.author (User)
    - Use UUIDs (string) as entity identifier
- [x] Set up repositories for all three entities

### Authentication

- [ ] Implement `UserDetails` on our `User` entity
- [ ] Set up some "custom" methods:
    - `findByUsername` (UserRepository)
    - `findByAuthor` (PostRepository)
- [ ] Create a `UserService` class that implements `UserDetailsService`
- [ ] Set up a `SecurityConfig` file to allows access to "/" route for now