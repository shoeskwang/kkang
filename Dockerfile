FROM amazoncorretto:17-alpine

COPY ./build/libs/app.jar app.jar
EXPOSE 8080:8080
CMD ["java", "-Duser.timezone=UTC", "-jar", "app.jar"]
