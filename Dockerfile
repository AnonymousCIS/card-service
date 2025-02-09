FROM yonggyo00/ubuntu
ARG JAR_FILE=build/libs/cardservice-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

ENV SPRING_PROFILES_ACTIVE=default,ml
ENV DB_HOST=localhost:1521
ENV DDL_AUTO=update

ENTRYPOINT ["java", "-jar", "-Ddb.host=${DB_HOST}", "-Ddb.password=${DB_PASSWORD}", "-Ddb.username=${DB_USERNAME}", "-Dddl.auto=${DDL_AUTO}", "-Dconfig.server=${CONFIG_SERVER}", "-Deureka.server=${EUREKA_SERVER}", "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}", "-Dhostname=${HOSTNAME}", "-Dpython.run.path=${PYTHON_RUN_PATH}", "-Dpython.script.path=${PYTHON_SCRIPT_PATH}", "app.jar"]

EXPOSE 3008