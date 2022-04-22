FROM payara/micro:5.2022.2-jdk11

# Copy an application to be loaded
COPY ./target/employee-event.war /opt/payara/deployments

ENTRYPOINT ["java", "-jar", "/opt/payara/payara-micro.jar", "--deploy", "/opt/payara/deployments/employee-event.war"]
