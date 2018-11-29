# spring-restful-service
A simple RESTful web service built using Spring Boot


### Query users' salaries and name
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

### Error handling
Query filter parameters only accept numeric, any other type of data will be ignored and a default value will be used
```
min = 0
max = 4000
```
#### Valid input parameters
```
localhost:8080/users?min=2500&max=9999
localhost:8080/users?min=0&max=3000
```
#### Invalid input parameters
```
localhost:8080/users?min=abcdef&max=^#@^@
localhost:8080/users?min=2245Dcze#!&max=G9765D~
```
