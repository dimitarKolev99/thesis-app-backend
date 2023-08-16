FROM eclipse-temurin:17-jre

VOLUME /tmp

COPY app.jar /application/app.jar
COPY config/logback-ext.xml /application/logback-ext.xml

ENV LOGBACK_CONFIG_EXT=/application/logback-ext.xml
ENV USER=ui20
ENV UID=10011
ENV GID=10011

RUN apt-get update \
 && apt-get -y install vim \
 && addgroup "$USER" --gid "$GID" \
 && adduser \
    --disabled-password \
    --gecos "" \
    --home "$(pwd)" \
    --ingroup "$USER" \
    --no-create-home \
    --uid "$UID" \
    "$USER" \
 && mkdir /var/log/tomcat \
 && chmod +x /application/app.jar \
 && chmod +rw /application/logback-ext.xml \
 && chown -R "$USER":"$USER" /application \
 && chown -R "$USER":"$USER" /var/log/tomcat

USER $USER
WORKDIR /

CMD java -jar /application/app.jar;
