#docker run "image" --priveleged=true -v /var/lib/docker
FROM ubuntu:20.04 as buider

RUN apt-get update
RUN apt-get -y install openjdk-11-jdk
RUN apt-get -y install curl
RUN curl -fsSL https://get.docker.com | bash
RUN service docker start

WORKDIR /server
COPY ./gradle ./gradle
COPY ./src ./src
COPY ./build.gradle.kts ./build.gradle.kts
COPY ./settings.gradle.kts ./settings.gradle.kts
COPY ./gradlew ./gradlew/

RUN ./gradlew test

RUN cp ./server/build/libs/server.jar .

#ENV PATH $JAVA_HOME/bin:$PATH
#RUN apk add --update docker openrcd
#RUN addgroup root dockerbs
#RUN ./gradlew test
#RUN ./gradlew bootJar
#
#FROM bellsoft/liberica-openjre-alpine:11
#WORKDIR /server
#COPY --from=builder ./server/build/libs/server.jar .
#CMD java -jar server.jar
