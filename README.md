# Yandex Market Review Checker API

## Prerequisits:
To build the application locally you will need:
- JDK 8
- maven version 3.3 or higher
- MySQL server or higher

## Building
Clone this repo and run the following command:
```
mvn clean install
```

## Database Configuration

Running this application requires a local MySQL 5.x.x  installation.

[Liquibase](http://www.liquibase.org/) is used for schema change control.
Run the following command to ensure you have the latest schema in your MySQL database:

```
mvn -e liquibase:update
```
 
## Running

The project uses Spring Boot to produce an executable uber JAR with an embedded web server.

