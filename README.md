# GR

## How to contribute?
Please read our [CONTRIBUTING](https://github.com/lehoangtran289/gr/blob/develop/CONTRIBUTING.md) document.

### Setup backend:
```bash
# start backend server and required services
docker-compose up
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
Then open [http://localhost:3000](http://localhost:3000) to view it in the browser. You should first register an account in login page.
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
