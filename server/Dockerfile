FROM    bellsoft/liberica-openjdk-alpine:11 as builder

WORKDIR /server
COPY    ./gradle ./gradle
COPY    ./src ./src
COPY    ./build.gradle.kts ./build.gradle.kts
COPY    ./settings.gradle.kts ./settings.gradle.kts
COPY    ./gradlew ./gradlew
RUN     ./gradlew bootJar
RUN     cp ./build/libs/server.jar .

FROM    bellsoft/liberica-openjre-alpine:11
WORKDIR /server
COPY    --from=builder /server/server.jar .
CMD     java -jar server.jar
