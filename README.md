This application is built using Spring Boot which reads FlatFile hydrates the Business Logic and writes into the Database. 
It features querying the database and hydrates the business logic and provide restful response as JSON objects and also write to csv based on user requests.

Spring Boot Application/Dependencies
Spring Data JPA
Spring web
Spring security 
MySQL Driver 
Thymeleaf
Log4j2
openCSV 

Recommended Prerequisites:
The following items should be installed in your system:
Java 8 or newer.
Your preferred IDE
Spring Tools Suite (STS) 
MySQL Database

Database configuration:
Create a MySQL database with the schema name as "mpidb" and add the credentials to src/main/resources/application.properties.
The default ones are :
#Database
spring.jpa.hibernate.ddl-auto=update
spring.datasource.url=jdbc:mysql://${MYSQL_HOST:localhost}:3306/mpidb
spring.datasource.username=root
spring.datasource.password=root
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL55Dialect

#Spring - security 
spring.security.user.name=admin
spring.security.user.password=admin

Assumptions:
Every patient has an unique enterpriseId and there might be number of PatientMemberRecord records.
Each PatientMemberRecord has unique medicalRecordNumber.
Patient goes to the particular hospital only once.
Sample dataset is at src/main/resources/mpi_dataset_10.csv

#Packages:
configuration - security configuration
entities - Database entity classes
repository -  JPA/CRUD repository interfaces
Services - Business logic implementation
utils - ExportCsv util interface to access list of query results
pom.xml - contains all the project dependencies
resources/ - contains the static(index.html), template (uploadStatus.html), Log4j2.xml configuration files
resources/application.properties - It contains  spring-boot application properties

#Import the Application to Spring Tools Suite:
Go to File use Import. 
Import as existing Maven projects
Navigate to the folder where you clone/download the repository.
Select the Project

#How to Build and Run Spring Boot Application:
There are several ways you can run the Spring Boot Application. One way is you can execute on main method from the IDE.
Right click on the file Run As Spring Boot App

Alternatively you can use the Spring Boot Maven plugin like so:
mvn spring-boot:run





