FROM java:8

MAINTAINER Claude-Clement Yapo (yjeandavid@hotmail.com)

COPY	./target/microcommerceapi-1.0.0-SNAPSHOT.jar /var/microcommerceapi/microcommerceapi.jar
WORKDIR /var/microcommerceapi

#executer une commande dans le container
#RUN bash -c 'ls /var/microcommerceapi'

EXPOSE 9090

ENTRYPOINT ["java", "-jar", "/var/microcommerceapi/microcommerceapi.jar"]