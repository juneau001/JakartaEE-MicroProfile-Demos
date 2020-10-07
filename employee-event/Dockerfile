FROM payara/micro:5.2020.4-jdk11

# Adds an application to be loaded
ADD ./target/employee-event.war $PAYARA_PATH

ENTRYPOINT ["java", "-jar", "$PAYARA_PATH/payara-micro.jar", "--deploy", "$PAYARA_PATH/employee-event.war"]
