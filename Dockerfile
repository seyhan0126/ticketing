# Use official WildFly image
FROM quay.io/wildfly/wildfly:latest

# Copy your WAR (or EAR) to WildFly deployments folder
COPY target/ticketing.war /opt/jboss/wildfly/standalone/deployments/
