FROM payara/micro:latest

# Downloads the Apache Derby Client library
RUN wget -O /opt/payara/deployments/database-connector.jar https://search.maven.org/remotecontent?filepath=org/apache/derby/derbyclient/10.14.1.0/derbyclient-10.14.1.0.jar

# Adds an application to be loaded
ADD ./target/employee-service.war /opt/payara/deployments

ENTRYPOINT ["java", "-jar", "/opt/payara/payara-micro.jar", "--addJars", "/opt/payara/deployments/database-connector.jar", "--deploy", "/opt/payara/deployments/employee-service.war"]