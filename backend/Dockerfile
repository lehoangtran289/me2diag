# FROM maven:3.8.4-jdk-11

# WORKDIR /app

# COPY pom.xml .
# RUN mvn dependency:go-offline

# COPY src/ src/
# COPY .env .
# RUN mvn -v
# RUN mvn clean install -DskipTests

# CMD mvn spring-boot:run

FROM openjdk:11-jdk as build

WORKDIR /app

COPY mvnw .
COPY .mvn .mvn

COPY pom.xml .

RUN ./mvnw dependency:go-offline -B

COPY src src

RUN ./mvnw package -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

FROM openjdk:11-jre

ARG DEPENDENCY=/app/target/dependency

COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

ENTRYPOINT ["java","-cp","app:app/lib/*","com.hust.backend.BackendApplication"]