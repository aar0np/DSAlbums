FROM amazoncorretto:21

# get DSAlbums JAR file
COPY target/dsalbums-0.0.1-SNAPSHOT.jar dsalbums.jar

# open port for http endpoint
EXPOSE 8080

ENTRYPOINT java -jar dsalbums.jar
