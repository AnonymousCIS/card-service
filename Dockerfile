FROM bpa1010/cis-ubuntu:card
ARG JAR_FILE=build/libs/cardservice-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

ENV SPRING_PROFILES_ACTIVE=default,ml
ENV DB_HOST=localhost:1521
ENV DDL_AUTO=update
ENV PYTHON_RUN_PATH=/usr/bin/python3.9
ENV PYTHON_RUN_SCRIPT=/card

ENTRYPOINT ["java", "-jar", "-Ddb.host=${DB_HOST}", "-Ddb.password=${DB_PASSWORD}", "-Ddb.username=${DB_USERNAME}", "-Dddl.auto=${DDL_AUTO}", "-Dconfig.server=${CONFIG_SERVER}", "-Deureka.server=${EUREKA_SERVER}", "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}", "-Dhostname=${HOSTNAME}", "-Dpython.run.path=${PYTHON_RUN_PATH}", "-Dpython.script.path=${PYTHON_SCRIPT_PATH}", "app.jar"]

EXPOSE 3008