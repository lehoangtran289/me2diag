FROM maven:3.8.4-jdk-11

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src/ src/
COPY .env .
RUN mvn -v
RUN mvn clean install -DskipTests

CMD mvn spring-boot:run