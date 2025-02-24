FROM gradle:jdk21 as build
COPY /wayd-backend /backend
WORKDIR /backend
RUN gradle clean build

FROM openjdk:21-slim
COPY --from=build /backend/build/libs/wayd-*SNAPSHOT.jar /app/app.jar
CMD [ "java", "-jar", "/app/app.jar" ]