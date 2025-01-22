FROM openjdk:17-slim-buster as base
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:resolve
COPY src ./src

# FROM base as development
# CMD ["./mvnw", "spring-boot:run", "-Dspring-boot.run.profiles=mysql", "-Dspring-boot.run.jvmArguments='-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8000'"]

FROM base as build
RUN ./mvnw clean package -DskipTests 

FROM openjdk:17-slim-buster as production
EXPOSE 8080
COPY --from=build /app/target/spring-petclinic-*.jar /spring-petclinic.jar
CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/spring-petclinic.jar"]

# FROM gcr.io/distroless/java17 as production
# EXPOSE 8080
# COPY --from=build /app/target/spring-petclinic-*.jar /spring-petclinic.jar
# CMD ["/spring-petclinic.jar"]

