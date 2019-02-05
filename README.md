# Notes_REST_API
Spring Boot RESTful API project for managing and storing simple notes in database

# Technology stack:
JDK 1.8
Spring Boot 
Spring Data JPA 
Spring MVC 
MySQL
Maven
Eclipse IDE
Tomcat server

# Database setup
To get started, you need to have MySQL installed.

You will need to create a database named notes_app in MySQL, and change the spring.datasource.username & spring.datasource.password properties as per your MySQL installation.

Open MySQL terminal / workbench terminal and execute following MySQL script :
CREATE DATABASE notes_app;

Tables will be created automatically after running the application.

# Project setup
Just go to the root directory of the application and type the following command to run it:

mvn spring-boot:run

# REST Endpoints
GET /notes - Retrieves all undeleted notes
GET /notes/deleted - Retrieves all deleted notes
GET /notes/{id} - Retrieves a single undeleted note
GET /notes/deleted/{id} - Retrieves a single deleted note
GET /notes/{id}/revision - Retrieves history of changes of particular note
POST /notes - Creates a new note
PUT /notes{id} - Updates an existing undeleted note
DELETE /notes/{id} - Removes an existing note

# TESTS and usages
To test the API I've used Postman GUI as a popular utility.
Here is a list of possible usages for testing purpose:
- Creates a new note:
curl -X POST http://localhost:8080/notes/ -H 'Content-Type: application/json' -H 'cache-control: no-cache' -d '{ "title": "new title 1", "content": "new content 1"}'

- Retrieve created note
curl -X GET http://localhost:8080/notes/1

- Retrieve all notes
curl -X GET http://localhost:8080/notes/

- Update an existing note
curl -X PUT http://localhost:8080/notes/1 -H 'Content-Type: application/json' -H 'cache-control: no-cache' -d '{ "title": "new title 1 updated", "content": "new content 1 updated"}'

- Delete an existing note
curl -X DELETE http://localhost:8080/notes/1

- Retrieve a deleted note
curl -X GET http://localhost:8080/notes/deleted/1

- Retrieve all deleted notes
curl -X GET http://localhost:8080/notes/deleted/

- Retrieve history of changes of particular note
curl -X GET http://localhost:8080/notes/1/revision
