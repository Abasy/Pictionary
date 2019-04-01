# Pictionary
Dans le cadre du cours de middleware, nous devons réaliser une application qui met en place différents systèmes de middleware : RMI, Web service, JMS ou Tuple Space.

## Application architecture

The application is composed of:

## Build and execution
The application is distributed as a Maven project, composed of a
`src/` directory that contains the java sources and a `pom.xml` file
that contains the Maven project description

### Project build

To build the project:

    mvn install
	
### Application execution

To launch the client application:

	java -jar target/Pictionary-1.0.jar <server_host> <server_port> <username> <directory>
	