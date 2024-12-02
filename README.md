This project is a REST API implementation for a basic calculator, designed to provide arithmetic operations such as addition, subtraction, multiplication, and division. It supports two operands (a and b) with arbitrary precision for signed decimal numbers. The architecture is modular and leverages RabbitMQ for intermodule communication.

# Spring Boot

## Run calculator module
```
cd calculator-api
cd calculator
gradlew bootRun
```

## Run rest module
```
cd calculator-api
cd rest
gradlew bootRun
```
## Run both modules at the same time
```
cd calculator-api
gradlew :calculator:bootRun :rest:bootRun --parallel
```
# RabbitMQ
Run rabbitmq in a docker container:
```docker
docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:management
```

# React
To test this calculator project, I also created a simple web page using React. If you'd like to try it out, just follow these steps:
```
cd calculator-frontend
npm install
npm start
```
Runs the app in the development mode.\
Open [http://localhost:3000](http://localhost:3000) to view it in the browser.

## References
- https://www.baeldung.com/spring-boot-start
- https://spring.io/blog/2020/03/26/spring-boot-2-2-6-available-now
- https://www.baeldung.com/spring-controllers
- https://spring.io/guides/tutorials/rest
- https://www.rabbitmq.com/tutorials
- https://www.baeldung.com/spring-amqp
- https://www.baeldung.com/spring-boot-logging
- https://medium.com/@d.lopez.j/spring-boot-setting-a-unique-id-per-request-dd648efef2b
- https://www.baeldung.com/mdc-in-log4j-2-logback
- https://docs.docker.com/
