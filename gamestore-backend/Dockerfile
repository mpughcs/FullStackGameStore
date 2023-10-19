# Use an official OpenJDK runtime as a parent image
# so that we can run our application in a Java environment
# runtimes can be found here: https://hub.docker.com/_/openjdk
FROM openjdk:11-jre-slim
# FROM python:3.8-slim-buster

# Set the working directory to /gamestore-backend 
# so that all following commands are executed there
# if this is / 
WORKDIR /
# copy the packaged jar file into our docker image
# the jar file is created by the maven build 
# and is located in the target folder
# rename the jar file to gamestore.jar
# COPY /gamestore/target/test.py gamestore.py
COPY /gamestore/target/gamestore-0.0.1-SNAPSHOT.jar gamestore.jar

# Expose port 8008 so that the container can be reached from outside
# EXPOSE 8080

# Define the command that should be executed when the container is started

# CMD ["python3", "gamestore.py"]
CMD ["java", "-jar", "gamestore.jar"]


#q:  I'm getting error that unable to access jarfile gamestore.jar
# but I'm not getting an error that says unable to access jarfile gamestore-0.0.1-SNAPSHOT.jar when i build the image
# why is this happening?
    # A: The
# why is this z
    # zz
# why is this z