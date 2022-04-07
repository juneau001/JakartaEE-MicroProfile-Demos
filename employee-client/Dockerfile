FROM payara/micro:5.2022.1-jdk11

# Copy an application to be loaded
COPY ./target/employee-client.war /opt/payara/deployments

ENTRYPOINT ["java", "-jar", "/opt/payara/payara-micro.jar", "--deploy", "/opt/payara/deployments/employee-client.war"]
