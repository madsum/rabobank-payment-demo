FROM openjdk:11
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline
COPY src ./src
RUN ./mvnw clean package spring-boot:repackage
CMD ["./mvnw", "spring-boot:run"]
