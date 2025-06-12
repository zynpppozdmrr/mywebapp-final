FROM maven:3.9.6-amazoncorretto-8-debian-bookworm AS build
WORKDIR /build
COPY pom.xml .
COPY src ./src
RUN mvn clean package


FROM tomcat:10-jre8-openjdk-buster
COPY --from=build build/target/ROOT.war /usr/local/tomcat/webapps/ROOT.war
COPY --from=build build/target/ROOT /usr/local/tomcat/webapps/ROOT
EXPOSE 8080
CMD ["catalina.sh", "run"]