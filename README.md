# Master Patient Index - Spring Boot Project

This application is built using Spring Boot which reads FlatFile hydrates the Business Logic and writes into the Database. 
It features querying the database and hydrates the business logic and provide restful response as JSON objects and also write to csv based on user requests.

## Dependencies (Spring Boot)
Spring Boot Application/Dependencies
Spring Data JPA
Spring web
Spring security 
MySQL Driver 
Thymeleaf
Log4j2
openCSV 

## Installation

The following items should be installed in your system:
Java 8 or newer
Spring Tools Suite (STS)
MySQL Database
Maven 3.3.9 or newer

## Database configuration

Create a MySQL database with the schema name as "mpidb" and add the credentials to src/main/resources/application.properties
The default ones are :
#Database
spring.jpa.hibernate.ddl-auto=update
spring.datasource.url=jdbc:mysql://${MYSQL_HOST:localhost}:3306/mpidb
spring.datasource.username=root
spring.datasource.password=root
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL55Dialect

spring.security.user.name=admin
spring.security.user.password=admin

## Assumptions

Every patient has an unique enterpriseId and there might be number of PatientMemberRecord records.
Each PatientMemberRecord has unique medicalRecordNumber.
Patient goes to the particular hospital only once.
Sample dataset is at src/main/resources/mpi_dataset_10.csv

# Java Packages

- controllers(com.navya.mpipoc): AppController.java, MpipocApplication.java 
- configuration: Security configuration for Spring App
- entities: Database entity classes
- repository:  JPA/CRUD repository interfaces
- Services: Business logic implementation
- utils: ExportCsv util interface to access list of query results
- pom.xml: Contains all the project dependencies
- resources/: Contains the static(index.html), template (uploadStatus.html), Log4j2.xml configuration files
- resources/application.properties: Contains  spring-boot application properties

## Import the Application to Spring Tools Suite

Go to File use Import. 
Import as existing Maven projects
Navigate to the folder where you clone/download the repository.
Select the Project

## How to Build and Run Spring Boot Application

There are several ways you can run the Spring Boot Application. One way is you can execute on main method from the IDE.
Right click on the file Run As Spring Boot App

Alternatively you can use the Spring Boot Maven plugin like so:
mvn spring-boot:run





