FROM centos:centos8.4.2105
RUN yum -y update && yum clean all && yum -y install java-11-openjdk.x86_64
WORKDIR episodate-app
ARG EPISODATE_APP_VERSION
COPY build/libs/episodate-series-listener-$EPISODATE_APP_VERSION.jar episodate-series-listener.jar
EXPOSE 8080
ENTRYPOINT java -jar episodate-series-listener.jar