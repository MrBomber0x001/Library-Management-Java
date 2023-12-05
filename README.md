## Reading Management System.


I've tried to keep the schema as simple as possible as the only goal of this task was to implement what we've learned in the OOP semester.
so my focus was on applying OOP examples as much as needed, and I've used my backend knowledge working with a database to implement a `GUI`
application that glues OOP + database + GUI programming together.

## Tool
- Database -> `mysql`
- GUI framework -> `JavaFX`
- IDE -> `Intellij IDEA community edition`

## Features

This is a simple `reading management` system which allows users to
- authenticate themselves (signing up, log in) to the system
- adding/removing and updating books based on their progress

## PoC (Proof of Concept)
This is a demo of the running application


https://github.com/MrBomber0x001/Library-Management-Java/assets/42917814/85307341-2b91-47ad-a048-53c78a370e28



## DB schema

![Database Schema Diagram](./assets/DB_SCHEMA.png)

```sql
CREATE TABLE `users` (
  `user_id` int AUTO_INCREMENT NOT NULL PRIMARY KEY,
  `username` VARCHAR(250),
  `email` VARCHAR(250),
  `password` VARCHAR(16),
  `gender` VARCHAR(250)
);

CREATE TABLE `books` (
  `id` int AUTO_INCREMENT NOT NULL PRIMARY KEY,
  `title` VARCHAR(50),
  `author_name` VARCHAR(50),
  `user_id` int
);


ALTER TABLE `books` ADD CONSTRAINT `fk_books_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);
```




## TODO and log
- [x] Implement user authentication (signup, login)
- [x] Implement Book adding feature, prefetching the books for registered or already logged in users
- [ ] Implement Book Updating/Deletion
- [ ] Package the application & Dockersize.
- [x] <https://stackoverflow.com/questions/27556536/javafx-scene-layout-pane-cannot-be-cast-to-javafx-fxml-fxmlloader>
- [x] Change the schema
- [x] Read about `ObservalList` (needed to update TableColumns in time);
- [x] Finish Adding Book -> click add (the user_id is passed automatically, make two constructors (book_id, no book_id)
