# Radix_Assessment
A Loan Payment System within a single Spring Boot application

To start the project

Open the terminal to the location of the project and input the command : ./mvnw spring-boot:run
This will build and initialise the project and expose the 8080 endpoint

1. Make Sure Java Is Installed

Check:

java -version

You should see something like:

openjdk version "21"

If not, install a JDK.

2. Run the Application

Look for the main class:

@SpringBootApplication
public class AssessmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(AssessmentApplication.class, args);
	}

}

Using IntelliJ

Right-click the class → Run AssessmentApplication

Using Maven

From the project root:

./mvnw spring-boot:run

Windows:

mvnw.cmd spring-boot:run

3. Verify It Started

You should see something like:

Started AssessmentApplication in 2.5 seconds

and

Tomcat started on port(s): 8080

Applications.properties
Property            Purpose
jdbc:h2:mem:testdb  In-memory database
sa                  Default H2 user
h2-console.enabled  Enables browser console
ddl-auto=update     Creates/updates tables automatically
