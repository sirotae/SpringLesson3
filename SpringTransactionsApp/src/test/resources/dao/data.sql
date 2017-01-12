CREATE TABLE "UsersTable"
(
  id INTEGER primary key,
  age INTEGER ,
  name VARCHAR(256) NOT NULL,
  surname VARCHAR (256) NOT NULL
);


insert into "UsersTable" (id, age, name, surname) values (1, 35, 'EmbeddedUserName', 'EmbeddedUserSurname');