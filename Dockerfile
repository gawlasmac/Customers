FROM adoptopenjdk/openjdk8
COPY target/customers-0.0.1-SNAPSHOT.jar .
EXPOSE 55560
CMD java -jar customers-0.0.1-SNAPSHOT.jar