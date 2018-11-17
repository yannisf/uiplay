FROM tomcat:9
WORKDIR /usr/local/tomcat
RUN rm -rf webapps/ROOT
COPY frlab.jks truststore.jks server.xml conf/
