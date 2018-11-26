FROM openjdk:8-alpine as jdk
WORKDIR /app
COPY ./ ./
RUN ./gradlew clean build test

FROM openjdk:8
WORKDIR /app
COPY --from=jdk /app/build/libs .
CMD java -jar srm-asset-api.jar