# =========================
# BUILD STAGE
# =========================

FROM gradle:8.14.3-jdk21 AS build

WORKDIR /app

# Copy Gradle config first
COPY build.gradle settings.gradle ./

# Download dependencies
RUN gradle dependencies --no-daemon

# Copy source code
COPY src ./src

# Build JAR
RUN gradle build -x test --no-daemon


# =========================
# RUNTIME STAGE
# =========================

FROM eclipse-temurin:21-jre

RUN apt-get update && apt-get install -y curl

# Create non-root user
RUN useradd -ms /bin/bash springuser

WORKDIR /app

# Copy ONLY built JAR from build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Change ownership
RUN chown -R springuser:springuser /app

# Switch user
USER springuser

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]