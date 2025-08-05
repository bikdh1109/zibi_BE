FROM tomcat:9.0.107-jdk17-temurin

COPY server.xml /usr/local/tomcat/conf/server.xml

COPY build/libs/*SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

COPY src/main/resources/keystore.p12 /usr/local/tomcat/conf/keystore.p12


EXPOSE 8080 8443
