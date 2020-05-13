FROM openjdk:14-jdk-alpine
COPY . .
RUN ./mvnw package -Dmaven.test.skip=true
ENTRYPOINT ["java","-jar","./target/users-0.1-beta.jar"]
