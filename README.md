# AOMR Prototype

Protoype tool which implements an ontology-supported multi-context reasoning system, which follows a PAC architecture. The java files are included under src, as well as the datafiles used for the examples under data.

This tool uses an embedded Apache Derby database (https://db.apache.org/derby/). The data component will need to be changed to conform to your local Derby db. If you are not using a Derby db, then any connection details will need to be detailed in the data component. 

Several JAR files are required to use the OWL and Pellet libraries, and are under the lib folder. If OWL or Pellet is not being used, then they are not necessary.
