# SonarQube Companion

SonarQube Companion application for Scrum teams.

[![Gitter](https://badges.gitter.im/Consdata/sonarqube-companion.svg)](https://gitter.im/Consdata/sonarqube-companion?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge)
[![Build Status](https://travis-ci.org/Consdata/sonarqube-companion.svg?branch=master)](https://travis-ci.org/Consdata/sonarqube-companion)

## Building

```bash
$ ./mvwn clean package
```

### Running builded artifact

```bash
$ java -jar sonarqube-companion-rest/target/sonarqube-companion-rest-*.jar
```

### Building docker image

```bash
$ cd sonarqube-companion-rest
$ docker build -t consdata/sonarqube-companion:latest .
```

### Pushing docker image to docker hub

```bash
$ docker push consdata/sonarqube-companion:latest
```

## Configuration

### Configuration files

- application.properties - Properties based application configuration file.
- sq-companion-config.json - Repository schema configuration file, contains SonarQube servers and groups layout configuration.

### Configuration properties

- spring.datasource.url
- app.configFile
- app.allowSelfSignedHttps

### Sample repository (servers and groups) configuration

By default application will look for _./sq-companion-config.json_ configuration file. 

```json
{
  "servers": [
    {
      "id": "sonarcloud.io",
      "url": "https://sonarcloud.io/"
    }
  ],
  "scheduler": {
    "interval": 30,
    "timeUnit": "MINUTES"
  },
  "rootGroup": {
    "uuid": "0df404f7-3bcb-433d-9f2c-95e0e000adb5",
    "name": "All groups",
    "groups": [
      {
        "uuid": "0df404f7-3bcb-433d-9f2c-95e0e000adb6",
        "name": "Java",
        "projectLinks": [
          {
            "serverId": "sonarcloud.io",
            "type": "REGEX",
            "config": {
              "include": [
                "net.java.openjdk.*",
                "openjdk.*"
              ]
            }
          }
        ]
      },
      {
        "uuid": "5357609b-7705-4d23-a021-a8289747c58f",
        "name": "Apache",
        "projectLinks": [
          {
            "serverId": "sonarcloud.io",
            "type": "REGEX",
            "config": {
              "include": [
                "org.apache.*"
              ]
            }
          }
        ]
      }
    ]
  }
}
```

### Database file configuration

By default application will create file based H2 database in _./sonarqube-companion.(mv.db)_.

## Working with project - local development

### System requirements

- Node >= v6.10.0
- Yarn >= v0.24.6

### Local develpoment

- Start _sonarqube-companion-rest_ module as Spring Boot application in IDE (pl.consdata.ico.sqcompanion.SonarqubeCompanionRestApplication.main)
- Start _sonarqube-companion-frontend_ module via _yarn start_

## Starting frontend for local development

```bash
$ cd sonarqube-companion-frontend

$ yarn
yarn install v0.24.6
[1/4] 🔍  Resolving packages...
success Already up-to-date.
✨  Done in 0.75s.

$ yarn start
yarn start v0.24.6
$ yarn start-local 
yarn start-local v0.24.6
$ yarn ng -- serve --proxy-config proxy.local.conf.json 
yarn ng v0.24.6
$ node node_modules/.bin/ng serve --proxy-config proxy.local.conf.json
** NG Live Development Server is listening on localhost:4000, open your browser on http://localhost:4000 **
Date: 2017-07-31T18:28:19.468Z                                                          
Hash: 8bccf1a5a43da6a59954
Time: 10357ms
chunk {inline} inline.bundle.js, inline.bundle.js.map (inline) 5.83 kB [entry] [rendered]
chunk {main} main.bundle.js, main.bundle.js.map (main) 86.5 kB {vendor} [initial] [rendered]
chunk {polyfills} polyfills.bundle.js, polyfills.bundle.js.map (polyfills) 202 kB {inline} [initial] [rendered]
chunk {scripts} scripts.bundle.js, scripts.bundle.js.map (scripts) 267 kB {inline} [initial] [rendered]
chunk {styles} styles.bundle.js, styles.bundle.js.map (styles) 68.4 kB {inline} [initial] [rendered]
chunk {vendor} vendor.bundle.js, vendor.bundle.js.map (vendor) 2.7 MB [initial] [rendered]

webpack: Compiled successfully.
```

## REST Api documentation

Whole REST api is exposed as runnable configuration via Swagger at _http://$APP/swagger/index.html_ endpoint.

## H2 Web Console

You can use H2 web console for local development (binded only to local net interface) at _http://$APP/h2-console/_ endpoint.

## Running for production

### Simpliest run

```bash
$ docker run -p 8000:8080 consdata/sonarqube-companion:latest
```

### Exposing local dir as repository config

```bash
$ docker run -p 8000:8080 -v `pwd`:/opt/sonarqube-companion/repository consdata/sonarqube-companion:latest
```

### Sample docker-compose service configuration

For local folder structure:

- repository/
  - sq-companion-config.json
- docker-compose.yml

You can use _docker-compose.yml_:

```yml
version: '3'
services:
  sonarqube-companion:
    image: consdata/sonarqube-companion:latest
    container_name: sonarqube-companion
    ports:
      - "8000:8080"
    restart: unless-stopped
    volumes:
      - data:/opt/sonarqube-companion/data
      - ./repository:/opt/sonarqube-companion/repository
    network_mode: "bridge"
volumes:
  data:
```

## Contribution guide

### Code formatting

Default Intellij Formatter with modifications
- Editor > Code Style > TypeScript > Punctuation
  - Use 'single' quotes


# notes

https://sonarcloud.io/api/issues/search?organizations=payara&ps=1&s=CREATION_DATE
https://sonarcloud.io/api/issues/search?projectKeys=Mongo&createdAfter=2018-04-16&createdBefore=2018-04-16&ps=1