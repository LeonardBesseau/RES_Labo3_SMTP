FROM openjdk:8-jre

RUN apt-get update && \
    apt-get clean 
  
#
# When we build the image, we copy the executable jar in the image file system. 
#
COPY MockMock-1.4.0.one-jar.jar /opt/app/mocksmtp.jar

# Mock server working with ports 25 and 8282 by default
EXPOSE 25
EXPOSE 8282


#
# This is the command that is executed when the Docker container starts
#
CMD ["java", "-jar", "/opt/app/mocksmtp.jar"]