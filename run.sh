#!/usr/bin/env bash

java -server -Xmx2g -XX:+UseG1GC -jar target/s3srv-0.1-SNAPSHOT.jar server s3srv.yaml
