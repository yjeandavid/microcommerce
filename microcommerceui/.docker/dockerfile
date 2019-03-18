FROM node:latest

MAINTAINER Claude-Clement Yapo (yjeandavid@hotmail.com)

COPY . /var/microcommerceui

WORKDIR /var/microcommerceui

#executer une commande dans le container
#RUN bash -c 'ls /var/microcommerceapi'

RUN bash -c 'npm rebuild node-sass'

RUN bash -c 'npm install -g @angular/cli'

EXPOSE 4200

ENTRYPOINT ["ng", "serve", "--host", "0.0.0.0"]