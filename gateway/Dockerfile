FROM openjdk:14-jdk-alpine
COPY . .
RUN ./mvnw package
ENTRYPOINT ["java","-jar","./target/gateway-0.1-beta.jar"]
