FROM openjdk:8-alpine as jdk
WORKDIR /app
COPY ./ ./
RUN ./gradlew clean build test

FROM openjdk:8-alpine
WORKDIR /app
COPY --from=jdk /app/build/libs .
CMD ulimit -c unlimited && java -jar srm-asset-api.jar