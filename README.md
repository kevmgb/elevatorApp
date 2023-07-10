# elevatorApp
The elevator app provides simple functionality to call elevator from one floor to another, while logging all events

## Table schema
This service uses h2 database.

Tables
<img width="614" alt="Screenshot 2023-07-10 at 09 42 09" src="https://github.com/kevmgb/elevatorApp/assets/46866870/15069ad8-9d98-4db2-aaa1-c38eb753be20">

Schema
TBL_ELEVATOR
<img width="676" alt="Screenshot 2023-07-10 at 09 46 29" src="https://github.com/kevmgb/elevatorApp/assets/46866870/9ed7d5b0-0f81-478b-a4f3-c545ebe88f68">
TBL_LOG
![image](https://github.com/kevmgb/elevatorApp/assets/46866870/92a82cdc-5548-4edf-a90a-f93158559f76)

TBL_QUERY_LOGS
![image](https://github.com/kevmgb/elevatorApp/assets/46866870/265677fa-1616-4c5e-8746-4ef9285fc978)


## Features
1. Call elevator from one floor to another
2. View all elevator events
3. Intercept generated sql queries

## Prerequisites
Before installing and running the service, ensure that you have the following prerequisites:

- Java 17 or higher
- Maven 3.x

## Installation
To install and run the Sample Service locally, follow these steps:

1. Clone the repository
2. Navigate to the project directory
3. Build the application using Maven mvn clean package
4. Start service: java -jar target/{name of jar file in target folder}.jar

## Swagger UI
Once service is running, use the link http://localhost:8080/swagger-ui/index.html to view documentation.

## Usage
Use collection below to access the api. https://api.postman.com/collections/8144754-7ee499dd-246d-45c2-94cc-d481a122ae7c?access_key=PMAT-01H4Z9B83WG1MSJBDE9KP8ZBTJ
