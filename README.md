#

# Notatki

## Api SonarQube

- lista projektów
  - GET api/projects/provisioned
  - GET api/projects/search (internal api)
- listy błędów na podstawie historii metryk
  - GET api/measures/search_history (since SQ 6.3)
  - https://docs.sonarqube.org/display/SONAR/Metric+Definitions
  - new_violations
  - new_xxxxx_violations (xxxxx in blocker, critical, major, minor, info)
  - violations
  - xxxxx_violations (xxxxx in blocker, critical, major, minor, info)
- lista błędów
  - GET api/issues/search (since SQ 3.6)
- przypisanie błędu
  - POST api/issues/assign (since SQ 3.6)
- historia analiz projektu
  - GET api/project_analyses/search (since SQ 6.3)
- quality gates projektu
  - GET api/qualitygates/project_status (since SQ 5.3)

## SonarQube

- https://hub.docker.com/_/sonarqube/
- https://docs.sonarqube.org/display/DEV/Web+API

# Keystore
keytool -keystore sonarqube-keystore.keystore -genkey -v -alias sonarqube-keystore
keytool -import -alias consdata-ca -keystore sonarqube-keystore.keystore -file consdata-ca.crt
