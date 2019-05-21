FROM alpine:3.8
FROM maven:3.5.2-jdk-8-alpine AS MAVEN_TOOL_CHAIN

ENV workdir /tmp/
WORKDIR $workdir

COPY . /tmp/

RUN mvn clean package -DskipTests

FROM java:8-jdk-alpine
ENV workdir2 /usr/src/app
WORKDIR $workdir2
COPY --from=MAVEN_TOOL_CHAIN /tmp/target/lifechain-0.0.1-SNAPSHOT.jar /usr/src/app/
COPY start.sh /usr/src/app/

EXPOSE 8080
RUN chmod +x start.sh
ENTRYPOINT ["./start.sh"]