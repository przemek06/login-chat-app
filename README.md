# Login Chat Application
Application for chatting in existing groups.

## Setup
First, you need to create .env file in resources directory. In this file, you need to give information required to connect to MySql database.
To build and run this applcation, please use Maven in project's root directory:
```
mvn clean package
```
And then execute .jar file created in target directory.

## Technologies
Main technologies used in this project:
* Spring Web
* Spring WebSocket
* Spring Security
* Spring Data JPA
* Spring Batch

## Features
* Users can register and then login with username and password
* Users can join existing chats and read all the past messages
* Users can send and receive new messages via web sockets
* Application stores messages in temporary storage and then uses batch processing to save them to database
