# FROM node:12
# WORKDIR /app
# COPY package*.json ./
# RUN npm install
# COPY . .
# EXPOSE 3000
# RUN npm run build
# CMD [ "npm", "run", "start" ]

FROM node:12.4.0-alpine as build

WORKDIR /app
COPY package*.json ./
RUN npm install

COPY . ./

ARG REACT_APP_BACKEND_ORIGIN
ARG REACT_APP_ENV
ENV REACT_APP_BACKEND_ORIGIN ${REACT_APP_BACKEND_ORIGIN}
ENV REACT_APP_ENV ${REACT_APP_ENV}

RUN npm run build

CMD [ "npm", "run", "start" ]

# FROM nginx:1.17.0-alpine

# COPY --from=build /app/build /var/www

# COPY nginx.conf /etc/nginx/nginx.conf

# EXPOSE 80

# ENTRYPOINT ["nginx","-g","daemon off;"]