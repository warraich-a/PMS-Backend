FROM openjdk:latest
WORKDIR /
ADD build/libs/PMS-1.0-SNAPSHOT-all.jar pms.jar
EXPOSE 9000
ENTRYPOINT ["java", "-jar", "pms.jar"]