# syntax=docker/dockerfile:1

FROM maven:3.9.9-eclipse-temurin-21 AS builder
WORKDIR /app
ENV MAVEN_OPTS="-Djava.io.tmpdir=/app/.tmp"
RUN mkdir -p /app/.tmp

# Cache dependencies first
COPY pom.xml .
RUN mvn -q -DskipTests dependency:go-offline

# Build backend jar
COPY src ./src
RUN mvn -q -DskipTests clean package

FROM eclipse-temurin:21-jre-jammy AS runtime
WORKDIR /app

# App defaults (override at runtime with -e)
ENV SERVER_PORT=8081
ENV TZ=Asia/Kolkata
ENV JAVA_OPTS=""

RUN useradd -r -u 1001 spring

# pom.xml config writes jar under ${java.io.tmpdir}/clinic-booking-backend-build.
# java.io.tmpdir is overridden in builder via MAVEN_OPTS to /app/.tmp.
COPY --from=builder /app/.tmp/clinic-booking-backend-build/clinic-booking-backend-*.jar /app/app.jar

USER spring
EXPOSE 8081

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]
