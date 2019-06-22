FROM java:8-jdk-alpine

RUN mkdir /var/ratpackapp

COPY ./build/libs/my.ratpackapp-1.0-SNAPSHOT-all.jar /var/ratpackapp

WORKDIR /var/ratpackapp

RUN sh -c 'touch my.ratpackapp-1.0-SNAPSHOT-all.jar'

ENTRYPOINT ["java","-jar","my.ratpackapp-1.0-SNAPSHOT-all.jar"]