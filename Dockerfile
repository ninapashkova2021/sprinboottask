FROM centos:centos8.4.2105
RUN yum -y update && yum clean all && yum -y install java-11-openjdk.x86_64
COPY build/libs/episodate-series-listener-0.0.1-SNAPSHOT.jar episodate-series-listener-0.0.1-SNAPSHOT.jar
EXPOSE 80 443 8080
ENTRYPOINT java -jar episodate-series-listener-0.0.1-SNAPSHOT.jar
