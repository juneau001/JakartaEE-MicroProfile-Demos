FROM payara/micro:6.2023.2-jdk17

# Copy an application to be loaded
COPY ./target/employee-client.war /opt/payara/deployments

ENTRYPOINT ["java", "-jar", "/opt/payara/payara-micro.jar", "--deploy", "/opt/payara/deployments/employee-client.war"]
