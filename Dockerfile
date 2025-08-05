FROM tomcat:9.0.107-jdk17-temurin

COPY server.xml /usr/local/tomcat/conf/server.xml

COPY build/libs/*SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080 8443
