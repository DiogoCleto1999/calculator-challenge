# Spring Boot

## Run calculator module
cd calculator-api
cd calculator
gradlew bootRun

## Run rest module
cd calculator-api
cd rest
gradlew bootRun

#RabbitMQ

'''docker
docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:management
