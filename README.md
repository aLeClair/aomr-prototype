<div id="top"></div>




<!-- PROJECT LOGO -->
<br />

<h3 align="center">AOMR Prototype</h3>

<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
    </li>
    <li><a href="#contact">Contact</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project

This repository contains the prototype tool, implemented following the Architecture for Ontology-supported Multi-context Reasoning System(s). It utilizes public data from the city of San Francisco, found <a href="https://datasf.org/opendata/">here</a>. This prototype tool is strictly for demonstrative purposes.

<p align="right">(<a href="#top">back to top</a>)</p>

### File Directory
```
.
├── data                          # Data used for populating the domain-specification components. CSV files.
├── lib                           # A file composed of the JAR files used in the tool
| └── Pellet                      # Contains files necessary for using the Pellet reasoner
├── pac                           # Contains files for all the coordinators
| ├── contextcoordinator          # Contains files related to the context coordinator
| ├── interpretationscoordinator  # Contains files related to the interpretations coordinator
| └── knowledgecoordinator        # Contains files related to the knowledge coordinator
├── Main.java                     # The Main file that can be used to run the program
└── README.md
```

### Built With

* [Apache Derby](https://db.apache.org/derby/)

<p align="right">(<a href="#top">back to top</a>)</p>



<!-- GETTING STARTED -->
## Getting Started
As a prototype tool, installation is meant to be minimal. The only configuration necessary is with the Derby database. Derby is an embedded database, and requires installation. Once installed, within the file DataComponent.java, line 75 can be changed to your settings. Specifically,
```
return DriverManager.getConnection("jdbc:derby:pacdb;create=true");
```
must be changed so that jdbc points to your instance.

Once connected, the datasets (CSV files in /data/) must be loaded into Derby. This is done through the several commented out commands of DataComponent.java. Specifically, lines 22-64. These will create tables in Derby and pull the CSV files from the /data/ directory. They will also automatically clean the data by populating empty cells that need data (e.g., setting empty speed limits to 0).

After the data has been uploaded, the lines 22-64 can be commented again as they are only needed once. If Derby is removed and reinstalled, you will need to run them again.

Main.java can then be run and the tool will then be presented. Since it is a proof of concept tool, we have restricted the queries that can be submitted to only the three examples provided in the paper. Other than this, you are able to create any archetype you want, as well as domain-specification components. Since only the San Francisco dataset is uploaded, you are restricted to that set of data. 


<!-- CONTACT -->
## Contact

Andrew LeClair - leclaial@mcmaster.ca

<p align="right">(<a href="#top">back to top</a>)</p>
