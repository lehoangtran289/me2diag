# GR

## How to contribute?
Please read our [CONTRIBUTING](https://github.com/lehoangtran289/gr/blob/develop/CONTRIBUTING.md) document.

## Build
Use this directions if you want to build the source on your machine
If you just want to run the system, see "Run the code"

>Note: you should initiate .env file in order to build.

### Setup backend:
Prerequisite: `openjdk-11` and `maven`
```bash
# start MariaDB, Minio storage, Python Flask model.
docker-compose up -f docker-compose.local.yml

cd backend/
mvn spring-boot:run
```

### Setup frontend: 
Prerequisite: `nodejs` and `npm` installed.

**1. Set up local developing environment (VSCode, ...)**
```bash
cd frontend/

# install dependencies (run once)
# Note: should not try to resolve any vulnerabilities
npm i
```

In case you do not use docker, run this command to start frontend server (port 3000) <br>
Then open [http://localhost:3000](http://localhost:3000) to view it in the browser. You should first register an account in database :D
```bash
# start frontend (in case you do not use docker)
npm run start
```

**2. Set up docker enviroment for developing**
```bash
cd frontend/

# start frontend server (port 3000)
docker-compose up
```

## Run the code
```bash
# Init empty .env in backend classpath
touch backend/.env && touch backend/src/main/resources/.env

# Build with docker compose 
docker-compose -f docker-compose.prod.yml up --build
```

## FAQ
### How to run command inside Docker container
```bash
docker-compose -f docker-compose.[ENV].yml exec [COMMAND]
```

### Enable Docker Buildkit on your machine
Some old versions of Docker may require setting enviroment variable `DOCKER_BUILDKIT` to 1.

```bash
DOCKER_BUILDKIT=1 docker-compose build
```
