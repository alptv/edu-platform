FROM node:15.0.1-alpine

WORKDIR /client
COPY ./public ./public
COPY ./src ./src
COPY ./package.json ./package.json
COPY ./tsconfig.json ./tsconfig.json
COPY ./yarn.lock ./yarn.lock

RUN yarn install

CMD export PORT=$PORT && export REACT_APP_SERVER_URL=$SERVER_URL && yarn start