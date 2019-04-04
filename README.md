# Pictionary
Dans le cadre du cours de middleware, nous devons réaliser une application
qui met en place différents systèmes de middleware : RMI, Web service, JMS ou Tuple Space.

## Application architecture
The application is composed of:
*Client package that contain Client applications for JMS communication and Javaspace to share data
	*`ClientJMS` class client application : 
	*`ClientRiver` class client application : This client application allow to create a javaspace and manage User and Word Objects.
	
*Model package that contain all data for the application
	*`User` class : A user object with a name, a color, a score, a boolean that tell if the user is the drawer
	and a boolean that tell if the user who draw is ready to draw. A user implement an Entry object.
	*`Word` class : A Word object to save a user's word. A Word object implement an Entry.
	
*View package that contain user interface
	*`Affichage` 
	*`FrameAffichage`
	*`Mouse_listener`
	*`TimerThread`

## Build and execution
The application is distributed as a Maven project, composed of a
`src/` directory that contains the java sources and a `pom.xml` file
that contains the Maven project description

### Project build

To build the project:

    mvn install
	
### Application execution (do it after "how it works)

To launch the client application:

	java -jar target/pictionary-1.0.jar <server_host> <server_port> <topicName>

### how it works
First you have to launch the server joram-server :

	java -jar target/joram-server-1.0.jar <server_port>
	
two you have to launch joram-admin :

	java -jar target/joram-admin-1.0.jar <server_host> <server_port>
	
After you have to create a topic with the name "project-pictionary" :

	topic project-pictionary
	
Finaly launch several clients application, and have fun. (refer to "application execution" to launch the client application)

