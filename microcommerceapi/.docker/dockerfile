FROM maven:3.6.0-jdk-8

MAINTAINER Claude-Clement Yapo (yjeandavid@hotmail.com)

COPY . /tmp

RUN cd /tmp && mvn clean package -DskipTests && mkdir ../var/microcommerceapi && cd target \
	&& mv microcommerceapi-1.0.0-SNAPSHOT.jar ../../var/microcommerceapi/microcommerceapi.jar \
	&& mv ../wait-for.sh ../../var/microcommerceapi/wait-for.sh \
	&& apt-get update && apt-get install -y netcat
	
WORKDIR ../var/microcommerceapi

#executer une commande dans le container
#RUN bash -c 'ls /var/microcommerceapi'

EXPOSE 9090

CMD ["java", "-jar", "/var/microcommerceapi/microcommerceapi.jar"]