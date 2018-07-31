FROM tomcat:9.0.1-jre8-alpine
COPY ./ShortData-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/
CMD ["catalina.sh", "run"]