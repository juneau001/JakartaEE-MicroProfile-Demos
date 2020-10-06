FROM payara/micro:5.2020.4-jdk11

# Downloads the database connector library
RUN wget -O $PAYARA_PATH/database-connector.jar https://repo1.maven.org/maven2/org/apache/derby/derbyclient/10.15.2.0/derbyclient-10.15.2.0.jar

# Adds an application to be loaded
ADD ./target/employee-event.war $PAYARA_PATH

ENTRYPOINT ["java", "-jar", "$PAYARA_PATH/payara-micro.jar", "--addJars", "$PAYARA_PATH/database-connector.jar", "--deploy", "$PAYARA_PATH/employee-event.war"]
