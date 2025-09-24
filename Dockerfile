FROM tomcat:11.0-jdk17

# Xóa app mặc định của Tomcat (ROOT)
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy file WAR vào Tomcat
COPY target/NEWProject-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080

CMD ["catalina.sh", "run"]
