FROM openjdk:17
EXPOSE 2001
ADD target/kafka-producer-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]