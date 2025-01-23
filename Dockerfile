FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY applications/app-service/build/libs/Challenge-Nequi-Guatemala.jar app.jar
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
ENV JAVA_OPTS=" -Xshareclasses:name=cacheapp,cacheDir=/cache,nonfatal -XX:+UseContainerSupport -XX:MaxRAMPercentage=70 -Djava.security.egd=file:/dev/./urandom"
# Replace with a non-root user to avoid running the container with excessive privileges
USER appuser
ENTRYPOINT [ "java", "-jar", "app.jar" ]
