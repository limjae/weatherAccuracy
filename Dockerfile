FROM    openjdk:17-alpine
EXPOSE 8080

ARG JAR_FILE=./build/libs/weather-app.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar","-Duser.timezone=Asia/Seoul"]