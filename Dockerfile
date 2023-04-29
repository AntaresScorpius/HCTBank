# Build Stage
FROM maven:3.8.3-openjdk-17 AS build
COPY . /app
WORKDIR /app
RUN apt-get update && \
    apt-get install -y mysql-server && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/* /tmp/* /var/tmp/*

RUN service mysql start && \
    mysql -e "CREATE USER 'admin'@'%' IDENTIFIED BY 'password';" && \
    mysql -e "GRANT ALL PRIVILEGES ON *.* TO 'admin'@'%';" && \
    mysql -e "FLUSH PRIVILEGES;" && \
    mvn clean package

# Run stage
FROM openjdk:17-jdk-slim
COPY --from=build /app/target/HCTBank-0.0.1-SNAPSHOT.jar /app/HCTBank-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/HCTBank-0.0.1-SNAPSHOT.jar"]