FROM anapsix/alpine-java:jre8

WORKDIR /data

CMD ["bash"]

ADD target/s3srv-0.1-SNAPSHOT.jar /data/s3srv.jar
ADD s3srv.yaml /data/s3srv.yaml

CMD java -server -jar s3srv.jar server s3srv.yaml

EXPOSE 8080
