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

### Database

Run an H2 database using
    
    java -cp h2.jar org.h2.tools.Server -tcp
    
## Develop

In order to accelerate development, backend and UI can be developed independently. The backend
can be deployed without a UI by omitting the ui profile shown before. The UI can be run in proxy 
serve mode as following:

    $ npm run proxy
    
The proxy configuration used is in `proxy.conf.json`.