FROM maven:3.8.3 AS build
COPY . /usr/src/app
WORKDIR /usr/src/app
RUN mvn clean package
FROM openjdk:21
COPY --from=build /usr/src/app/target/carrefour-delivery-kata-0.0.1-SNAPSHOT.jar /app/carrefour-delivery-kata-0.0.1-SNAPSHOT.jar
CMD ["java", "-jar", "/app/carrefour-delivery-kata-0.0.1-SNAPSHOT.jar"]
