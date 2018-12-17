
FROM maven:3.5-jdk-8-alpine

EXPOSE 8080

ENV HOME /home/centos/anz-customer-interface/

WORKDIR $HOME

COPY . $HOME

RUN ["mvn", "clean"]
RUN ["mvn", "install"]

CMD ["java","-jar", "/home/centos/anz-customer-interface/target/anz-customer-interface-0.0.1-SNAPSHOT.jar"]



