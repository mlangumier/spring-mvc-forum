# Forum posts - Spring Boot MVC demo app

Demo project: [Spring Blog](https://gitlab.com/jeandemel-formations/hb-cda-2025/springblog)

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
    - Ex relation: `Post ` has `author` (User)
    - Use UUIDs (string) as entity identifier
- [x] Set up repositories for all three entities

### Authentication Config

- [x] Implement `UserDetails` on our `User` entity
- [x] Set up some "custom" methods:
    - `findByUsername` (UserRepository)
    - `findByAuthor` (PostRepository)
- [x] Set up a `SecurityConfig` file to allows access to "/" route for now
- [x] Create a `UserService` class that implements `UserDetailsService`

### Registering process

- [x] Set up DTOs and validation annotations for the user:
    - Register form: username, password, confirmPassword
    - Login form: username, password
- [x] Set up an `AuthController`:
    - Process register form: verify data, hash password, set default role & persist
    - (Optional) Create tests
- [x] Create a register template page
- [x] (Optional) Create a login template page

### Add posts

- [x] Set up DTOs for Posts:
    - Write a new post: title, content
- [x] Set up a `PostController`:
    - Process writing a new post: assign authenticated user, set date & persist
- [x] Update SecurityConfig to also manage post routes (only accessible if authenticated)

### Display posts

- [x] Add a displayPosts method in `PostController`
    - Set up pagination with `PageRequest.of(params)`: start with first page, display 5 posts per page, sort by date
- [x] Set up Thymeleaf template to display the paginated results
    - Display pagination links control