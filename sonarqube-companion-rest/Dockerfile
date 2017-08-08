FROM consdata/java:base
MAINTAINER Grzegorz Lipecki <grzegorz.lipecki@gmail.com>

ENV dir /opt/sonarqube-companion

RUN mkdir -p $dir
RUN mkdir -p $dir/config
RUN mkdir -p $dir/repository
RUN mkdir -p $dir/data

ADD target/sonarqube-companion-rest-*.jar $dir/sonarqube-companion.jar
ADD src/docker/dockerized-config.properties $dir/config/application.properties
ADD src/docker/sample-config.json $dir/repository/sq-companion-config.json

ENTRYPOINT [ "sh", "-c", "cd $dir && java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar ./sonarqube-companion.jar" ]

EXPOSE 8080