#!/bin/bash
mvn sonar:sonar -Dsonar.host.url=http://$(docker-machine ip default):9000 -Dsonar.jdbc.url="jdbc:h2:tcp://$(docker-machine ip default)/sonar"
