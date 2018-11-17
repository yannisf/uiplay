# UIPlay

A trivial application to experiment with the Angular framework, based on a Spring backend.

## Build

In order to create a deployable application (war), the UI should be built and installed in the local maven repository. 
The backend application depends on the UI artifact, and includes it into its war as an overlay. 

### Building the Angular application

    $ cd ng
    $ ng build --base-href /app
    $ mvn
    
### Building the Spring application

    $ mvn clean package -Pui
    
**NOTE**: Adding `-Pui` enables the `ui` profile, which includes into the build the angular application from the previous step 
as an overlay. In user interface development mode, you may choose not to include it, to support a faster development cycle. 

### Database

The application by default uses an in memory H2 database. Data are not maintained between restarts. It is fairly easy to use
a different database mode by editing `jdbc.properties`. Typically one will use the H2 server mode. In that case start an H2
instance before starting the application as below:
    
    java -cp h2.jar org.h2.tools.Server -tcp
    
## Develop

In order to accelerate development, backend and UI can be developed independently. The backend
can be deployed without a UI by omitting the ui profile shown before. The UI can be run in proxy 
serve mode as following:

    $ npm run proxy
    
The proxy configuration used is in `proxy.conf.json`.

## Running UIPlay in production

UIPlay is suggested to be run within a container. The container should be based
on the `tomcat:9` docker image and created with the supplied `Dockerfile`.

### Create the image

From within the directory that has the Dockerfile create an image issuing the
following command:

    sudo docker build -t frlab:2.0 .

The purpose of the container creation is to customize the default `tomcat:9`
configuration with the frlab SSL certificates. Thus within the directory
the necessary keystores should be available as well.

**NOTE**: Update the image version accordingly

### Directory setup

UIPlay takes advantage of docker volumes to support seamless upgrade without
the loss of the database files.  Also for easier monitoring the logs are
externalized.

The following directory structure is expected on the host:

```
./uiplay
  /app/uiplay.war <1>
  /db/uiplay.mv.db <2>
  /logs <3>
```

1. Application to deploy. Hot deployment is supported. Just copy the updated war ontop.
2. Current database
3. Log directory

### Start the container

```
$ sudo docker run \
    --name frlab \
    --restart unless-stopped \
    -p 8080:8080 \
    -p 443:8443 \
    -v /home/yannis/uiplay/app/uiplay.war:/usr/local/tomcat/webapps/ROOT.war \
    -v /home/yannis/uiplay/db/uiplay.mv.db:/usr/local/tomcat/uiplay.mv.db \
    -v /home/yannis/uiplay/logs:/usr/local/tomcat/logs \
    -d frlab:2.0
```
