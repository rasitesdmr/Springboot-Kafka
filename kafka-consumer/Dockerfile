FROM openjdk:17
EXPOSE 2002
ADD target/kafka-consumer-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]