#!/bin/bash
nohup java -jar sonarqube-companion-web.jar --server.port=8001 --app.syncDelay=3600000 --sonarqube.password=q1w2e3r4 --app.layoutConfiguration=filesystem:/root/sonarqube-companion/repository.json  0<&- &>> sonarqube-companion.log &
