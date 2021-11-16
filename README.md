# spring-boot
## Technical:

1. Framework: Spring Boot v2.0.4
2. Java 8
3. Thymeleaf
4. Bootstrap v.4.3.1


## Setup with Intellij IDE
1. Create project from Initializr: File > New > project > Spring Initializr
2. Add lib repository into pom.xml
3. Add folders
    - Source root: src/main/java
    - View: src/main/resources
    - Static: src/main/resource/static
4. Create database with name "demo" as configuration in application.properties
5. Run sql script to create table doc/data.sql

## Implement a Feature
1. Create mapping domain class and place in package com.nnk.springboot.domain
2. Create repository class and place in package com.nnk.springboot.repositories
3. Create controller class and place in package com.nnk.springboot.controllers
4. Create view files and place in src/main/resource/templates

## Write Unit Test
1. Create unit test and place in package com.nnk.springboot in folder test > java

## Security
1. Create user service to load user from  database and place in package com.nnk.springboot.services
2. Add configuration class and place in package com.nnk.springboot.config
3. Add Authorization: redirect based on Role

To start the project, add in `application.properties`:
```properties
logging.level.com.nnk.springboot=Debug
logging.level.web=debug
logging.level.org.hibernate=error
logging.file=log/poseidon-app.log

logging.pattern.console=%d{yyyy-MM-dd} [%thread] %-5level %logger{50} - %msg%n
logging.pattern.file=%d{yyyy-MM-dd HH:mm:ss.SSS}  === [%thread] === %-5level === %logger{50} ===  %msg%n

################### DataSource Configuration ##########################
spring.jpa.database=mysql
spring.jpa.database-platform=mysql
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/poseidon?createDatabaseIfNotExist=true&allowPublicKeyRetrieval=true&characterEncoding=utf8&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC
spring.datasource.username=ur_username
spring.datasource.password=ur_password

################### Hibernate Configuration ##########################

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
server.error.whitelabel.enabled=false
```

## Test Coverage
<img width="1069" alt="Jacoco_cover_P7" src="https://user-images.githubusercontent.com/65612959/141878273-a607d816-867c-42a5-a357-967eb30449ed.png">
