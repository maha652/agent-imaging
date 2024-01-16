
## Name
Agent-imaging

## Description
It is a Java Spring Boot application designed for anonymizing patient attributes and extracting metadata from DICOM files.
## Installation 
Make sure you have installed : 
java : https://www.oracle.com/java/technologies/downloads/

## Setup your environment 
git clone https://github.com/gustaveroussy/agent-imaging.git
Move to the new directory : cd agent-imaging
find the path/to/imaging.jar

## Configuration :
-- The application requires configuration for the DICOM folder path so configure the properties file

path.to.input.folder = path/to/dicom_folder

## launch the exectable jar file 
java "-Dspring.config.location=path/to/application.properties" -jar path/to/imaging.jar





