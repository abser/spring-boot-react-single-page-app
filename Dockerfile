FROM node:8.16-jessie-slim AS frontend-build
# ENV workdir /tmp/frontend/
WORKDIR /tmp/frontend/
COPY frontend .
RUN npm install && npm run build && ls .
# RUN npm run build

FROM maven:3.5.2-jdk-8-alpine AS spring-boot-build
WORKDIR /tmp/
COPY src /tmp/src/
COPY pom.xml .
COPY mvnw .
COPY --from=frontend-build /tmp/frontend/build /tmp/src/main/resources/static/
RUN mvn clean package -DskipTests && ls .

FROM java:8-jdk-alpine
WORKDIR /usr/src/app
COPY --from=spring-boot-build /tmp/target/fileupload-0.0.1-SNAPSHOT.jar /usr/src/app/
COPY start.sh /usr/src/app/

EXPOSE 8080
RUN chmod +x start.sh
ENTRYPOINT ["./start.sh"]