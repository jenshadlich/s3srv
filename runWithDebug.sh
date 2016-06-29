#!/usr/bin/env bash

java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 -jar target/s3srv-0.1-SNAPSHOT.jar server s3srv.yaml