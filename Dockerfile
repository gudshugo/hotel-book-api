FROM openjdk:11
EXPOSE 8080
ARG JAR_FILE=target/hotel-book-api-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} hotel-book-api-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-Dspring.profiles.active=prod","-jar","/hotel-book-api-0.0.1-SNAPSHOT.jar"]