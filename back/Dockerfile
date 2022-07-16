# build spring boot jar
FROM adoptopenjdk:11-jdk-hotspot AS builder
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src
RUN chmod +x ./gradlew
RUN ./gradlew bootJar

# copy over jar into runtime docker image
FROM adoptopenjdk:11-jdk-hotspot
COPY --from=builder build/libs/*.jar app.jar
###############################################################################
# environment variable
###############################################################################
# spring profile
ENV SPRING_PROFILE "dev"
#ENV SPRING_DATASOURCE_BASE_URL='jdbc:h2:mem:testdb'
#ENV SPRING_DATASOURCE_BASE_USERNAME='base'
#ENV SPRING_DATASOURCE_BASE_PASSWORD='base'

# port
EXPOSE 5001

ENTRYPOINT ["java","-jar","-Dspring.profiles.active=${SPRING_PROFILE}", "-Dserver.port=${PORT}", "/app.jar"]
# docker image entry point
#ENTRYPOINT java -jar \
#-Dspring.profiles.active="${SPRING_PROFILE}" \
#/app.jar \
#SPRING_DATASOURCE_BASE_URL="${SPRING_DATASOURCE_BASE_URL}" \
#SPRING_DATASOURCE_BASE_USERNAME="${SPRING_DATASOURCE_BASE_USERNAME}" \
#SPRING_DATASOURCE_BASE_PASSWORD="${SPRING_DATASOURCE_BASE_PASSWORD}"
