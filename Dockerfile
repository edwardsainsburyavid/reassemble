FROM openjdk:latest

RUN apt-get update
RUN apt-get install -y maven

COPY pom.xml /usr/local/test/pom.xml
COPY src /usr/local/test/src
COPY tests.txt /usr/local/test/
COPY mainUnitTests.txt /usr/local/test/

WORKDIR /usr/local/test/

RUN mvn package -q
CMD ["java", "-cp", "target/Reassembler-3.0.jar", "org.edwardsainsbury.reassembler.Reassembler", "tests.txt"]
