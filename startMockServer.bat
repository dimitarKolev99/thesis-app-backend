@echo off
docker run --rm -d -p 8080:8080 -p 8443:8443 --name wiremock-demo rodolpheche/wiremock:2.25.1