FROM openjdk:8u191-jdk-alpine3.9

MAINTAINER Nitesh Surendra Jha "contact@www.niteshjha.info"

WORKDIR /usr/local/bin/

EXPOSE 8080

ADD /target/springsecuritywithbootstrap-0.0.1-SNAPSHOT.jar springSecurity.jar

CMD["java" ,"-jar","springSecurity.jar"]