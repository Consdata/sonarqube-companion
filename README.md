# sonarqube-with-scrum
SonarQube companion app for Scrum projects

[![Build Status](https://travis-ci.org/glipecki/sonarqube-companion.svg?branch=master)](https://travis-ci.org/glipecki/sonarqube-companion)

# TODO
- project view like group view
- move model->dto conversion to separate builder classes

# Keystore
keytool -keystore sonarqube-keystore.keystore -genkey -v -alias sonarqube-keystore
keytool -import -alias consdata-ca -keystore sonarqube-keystore.keystore -file consdata-ca.crt
