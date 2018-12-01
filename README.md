# spring-restful-service
A simple RESTful web service built using Spring Boot.

### 1. Pre-requisite
#### Database mysql installation
1.1. mysql needs to be installed on your local machine with port 3306 (default). With the username `root` and password `password`
```
jdbc:mysql://localhost:3306/repository
```
1.2. After mysql installation is complete, database with the name '*repository*' needs to be created.
```
CREATE DATABASE repository;
USE repository;
```
1.3. Create a table with the name 'user', which will be storing all the users' data
```
CREATE TABLE user (
  id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(500) NOT NULL,
  salary double 
);
```
### 2. Start the server
2.1. Access the following url using a browser
```
http://localhost:8080/
```
2.2. The landing page allows you to:
1) Query user database using salary filter
2) Uploading csv file to populate into the database (see the `./db/users.csv` as an example)
3) The option to refresh the database based on the csv files stored in `/db/*.csv` directory

##### Query users' salaries and name
```
localhost:8080/users?min=0&max=4000
```
Outputs:
```
{
  "results" : [ {
    "name" : "John",
    "salary" : 2500.05
  }, {
    "name" : "Mary Posa",
    "salary" : 4000.0
  } ]
}
```

##### Error handling
Query filter parameters only accept numeric, any other type of data will be ignored and a default value will be used
```
min = 0
max = 4000
```
##### Valid input parameters
```
localhost:8080/users?min=2500&max=9999
localhost:8080/users?min=0&max=3000
```
##### Invalid input parameters
```
localhost:8080/users?min=abcdef&max=^#@^@
localhost:8080/users?min=2245Dcze#!&max=G9765D~
```




