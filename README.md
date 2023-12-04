## JavaFX

this is my first time playing around with JavaFX, although it was a terrible idea to put my hand on stinky java because I hate
it's philosphy, I kinda enjoyed the experienc of building desktop applications :D

this is a library management system

## Features

- [ ] the user can login and signup
- [ ] the user can add add/remove books (joins)

## DB schema

![Database Schema Diagram](./assets/DB_SCHEMA.png)

```sql
CREATE TABLE `Users` (
  `id` bigint PRIMARY KEY,
  `first_name` VARCHAR(50),
  `last_name` VARCHAR(50),
  `email` VARCHAR(250),
  `password` VARCHAR(16),
  `role` VARCHAR,
  `created_At` timestamp
);

CREATE TABLE `Authors` (
  `id` bigint PRIMARY KEY,
  `first_name` VARCHAR(50),
  `last_name` VARCHAR(50)
);

CREATE TABLE `Books` (
  `id` bigint PRIMARY KEY,
  `title` VARCHAR(50),
  `release_date` date,
  `author_id` bigint,
  `user_id` bigint
);

ALTER TABLE `Books` ADD FOREIGN KEY (`user_id`) REFERENCES `Users` (`id`);

ALTER TABLE `Books` ADD FOREIGN KEY (`author_id`) REFERENCES `Authors` (`id`);

```

## Resources

the main resource was a [youtube](https://www.youtube.com/watch?v=ltX5AtW9v30) video that showed me the way to deal with javaFX, after that I've applied my backend knowledge into this application

## notes

stage is essentially the window of the GUI,
the scene is what is displayed in this window

a stage can have multiple scenes
