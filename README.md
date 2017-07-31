# SonarQube Companion

SonarQube companion application for Scrum teams.

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

By default application will create file based H2 database in _./sonarqube-companion_.

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

## Contribution guide

### Code formatting

Default Intellij Formatter with modifications
- Editor > Code Style > TypeScript > Punctuation
  - Use 'single' quotes
