FROM adoptopenjdk:11-jre-hotspot
RUN mkdir /opt/app
COPY target/vizsgaremek-1.0.jar /opt/app/vizsgaremek.jar
#ADD maven/${project.artifactId}-${project.version}.jar \
 # /opt/app/vizsgaremek.jar
CMD ["java", "-jar", "/opt/app/vizsgaremek.jar"]
