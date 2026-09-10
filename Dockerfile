# =========================
# STEP 1: BUILD
# =========================
FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline

COPY src ./src

RUN mvn clean package -DskipTests


# =========================
# STEP 2: RUN TOMCAT
# =========================
FROM tomcat:11.0-jdk21-temurin

RUN rm -rf /usr/local/tomcat/webapps/*

COPY --from=build /app/target/*.war \
    /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080

CMD ["catalina.sh", "run"]