FROM maven:latest AS build

WORKDIR /usr/src/app

COPY pom.xml .

RUN mvn -B -f pom.xml -s /usr/share/maven/ref/settings-docker.xml dependency:resolve

COPY . .

RUN mvn -B -s /usr/share/maven/ref/settings-docker.xml package -DskipTests

FROM java:17-jdk-alpine

WORKDIR /app

ARG APP_VERSION

COPY --from=build /usr/src/app/target/shop-dev-app-${APP_VERSION}.jar .

ENTRYPOINT ["java", "-jar", "/app/shop-dev-app-${APP_VERSION}.jar"]
# CMD ["--spring.profiles.active=postgres"]

