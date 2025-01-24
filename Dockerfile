FROM gradle:8.11.1-jdk17 AS build
COPY . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle clean build

FROM eclipse-temurin:17-jre-alpine
VOLUME /tmp
COPY --from=build /home/gradle/src/applications/app-service/build/libs/*.jar app.jar
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
ENV JAVA_OPTS=" -Xshareclasses:name=cacheapp,cacheDir=/cache,nonfatal -XX:+UseContainerSupport -XX:MaxRAMPercentage=70 -Djava.security.egd=file:/dev/./urandom"
EXPOSE 8080
# Replace with a non-root user to avoid running the container with excessive privileges
USER appuser
ENTRYPOINT [ "java", "-jar", "app.jar" ]
