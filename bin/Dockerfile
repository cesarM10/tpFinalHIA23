# Stage 1
FROM maven:3.6.3-openjdk-16-slim AS stage1
ENV MAVEN_OPTS="-XX:+TieredCompilation -XX:TieredStopAtLevel=1"
WORKDIR /opt/app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY ./src ./src
COPY .env .
RUN mvn clean package -DskipTests=true

## Stage 2
FROM adoptopenjdk/openjdk16:jre-16.0.1_9-alpine
WORKDIR /opt/app
COPY --from=stage1 /opt/app/target/*.jar app.jar
RUN ls /opt/app
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]