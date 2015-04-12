WebServiceProject
=================

What is it?
-----------

It's a sample, deployable Maven 3 project with Java EE 6, Spring on JBoss AS 7.1.1

The project has three child projects, with there respective poms 

1) WebServiceProject-ear
2) WebServiceProject-ejb
3) WebServiceProject-web

Web project has two web services, one REST based with Spring REST implementation and other SOAP based with JAX-WS implementation.

1) REST Service with following endpoints:
   POST: http://<hostname>:<port>/WebServiceProject/queueInterface/push?param1={param1}&param2={param2}
   GET: http://<hostname>:<port>/WebServiceProject/queueInterface/push?param1={param1}&param2={param2}
2) SOAP Service (http://<hostname>:<port>/WebServiceProject/GCDService?wsdl) with following exposed operations:
	 int gcd()
 	ArrayList<Integer> gcdList()
	 int gcdSum()

EJB Project has following local stateless session beans

1) GCDPersistenceBean: persist the GCD of the top elements of the JMS queue.
2) ParameterPersistenceBean: persist the parameters added to the JMS queue.

EAR project will package the web and ejb projects in an EAR file and deploy to the respective App Server.

In addition to the main project, WebServiceTest project is also included which have the client classes to test the SOAP web service.

Database and App Server requirements
----------------------------------------------

All you need to build this project is Java 6.0 (Java SDK 1.6) or better, Maven 3.0 or better.
The application this project produces is designed to be run on a JBoss AS 7.1.1 and on MY SQL relational database.
Following are the requirements of the project, in order to successfully run on the application server:

1) JMS queue with the JNDI name: java:/unico/parameterQueue needs to configured.
2) Datasource with JNDI name: java:/MySqlDS needs to be configured.

Following are the database scripts:

create schema WEBSERVICES;

create table parameter (
id BIGINT(10) NOT NULL PRIMARY KEY,
param_value BIGINT(10) NOT NULL
)
alter table parameter AUTO_INCREMENT=1;
alter table parameter add column entered_date DATETIME NOT NULL;

create table gcd (
id BIGINT(10) NOT NULL PRIMARY KEY,
gcd_value BIGINT(10) NOT NULL,
entered_date DATETIME NOT NULL
)
alter table gcd modify column id INT auto_increment;

 
NOTE:
This project some retrieves artifacts from the JBoss Community Maven repository.
With the prerequisites out of the way, you're ready to build and deploy.

Deploying the application
-------------------------
 
First you need to start JBoss AS 7. To do this, run
  
    $JBOSS_HOME/bin/standalone.sh
  
or if you are using windows
 
    $JBOSS_HOME/bin/standalone.bat

To deploy the application, you first need to produce the archive to deploy using
the following Maven goal:

    mvn clean compile install

You can now deploy the artifact to JBoss AS by executing the following command from EAR project directory:

    mvn jboss-as:deploy

This will deploy `target/WebServiceProject.ear`.
 
The application will be running at the following URL <http://localhost:8080/WebServiceProject/>.

To undeploy from JBoss AS, run this command:

    mvn jboss-as:undeploy

You can also start JBoss AS 7 and deploy the project using Eclipse. See the JBoss AS 7
Getting Started Guide for Developers for more information.
 
Importing the project into an IDE
=================================

If you created the project using the Maven archetype wizard in your IDE
(Eclipse, NetBeans or IntelliJ IDEA), then there is nothing to do. You should
already have an IDE project.

If you created the project from the commandline using archetype:generate, then
you need to import the project into your IDE. If you are using NetBeans 6.8 or
IntelliJ IDEA 9, then all you have to do is open the project as an existing
project. Both of these IDEs recognize Maven projects natively.
 
Detailed instructions for using Eclipse with JBoss AS 7 are provided in the 
JBoss AS 7 Getting Started Guide for Developers.

