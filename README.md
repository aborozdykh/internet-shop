# Internet Shop

Project Internet Shop is simply e-shop template with basic operations 
for products, users, orders.   

## Summary

  - [Project purpose](#project-purpose)
  - [Project structure](#project-structure)
  - [Implementation details](#implementation-details)
  - [Run and deployment](#run-and-deployment)
  - [Authors](#authors)

## Project purpose

Project Internet Shop allows you create and modify e-shop with basic operations such as:

  - User registration, login and logout
  - User password encryption
  - User authentication and RBAC authorization
  - Product management: CRUD operations
  - Adding products to shopping cart and order completion
  - Order history and management
  - User management
 
Users have two roles: ADMIN and USER. 

Users with ADMIN role are authorized to:

    - create and delete products from the list of products available for order
    - view list of all users and their orders
    - delete user's order
    - delere users
    
Users with USER role are authorized to:

    - view all products
    - add to and remove products from their shopping cart
    - view shopping cart and complete order
    - view all their orders

### Project structure

The project uses MVC architectural pattern. Project structure is the following:

  - Models (entity classes)
  - DAO layer, containing basic CRUD-operations for communication with the persistence layer
  - Service layer, containing business-logic of the application
  - Servlets, implementing client-server communication logic
  - JavaServer Pages

### Implementation details

  - Dependency Injection design pattern is used in the project - DAO and Service dependencies are injected during runtime
  - DAO layer has two implementations:
    - inner-storage (List-based)
    - outer-storage (using JDBC connecting to MySQL RDBMS)
    To switch between the two implementations, you will need to place @Dao annotation before class declaration of the chosen implementation
  - User authentication and RBAC authorization are realized through filters
  - JSPs use JSTL, EL and Twitter Bootstrap
  - Logging is implemented via Log4j2 library
  - Maven Checkstyle Plugin, Travis CI and SonarCloud Continuous Code Quality Tool are configured

## Run and deployment

To run this project you will need to install:

  - JDK 11 or higher
  - Apache Maven
  - Apache Tomcat
  - MySQL RDBMS

Here are the steps for you to follow:

  - Add this project to your IDE as Maven project.
  - If necessary, configure Java SDK 11 in Project Structure settings.
  - Add new Tomcat Server configuration and select war-exploded artifact to deploy. Set application context parameter to "/".
  - Change path to your log file in src/main/resources/log4j2.properties on line 14. 
  - If you decide to use the default JDBC-based DAO implementation:
    - Execute queries listed in src/main/resources/init_db.sql in MySQL RDBMS in order to create the schema and all the tables required.
    - Enter your own username and password in src/main/java/com/internet/shop/util/ConnectionUtil.java class on lines 13 and 14.
  - Run the project via Tomcat configuration.
  
First, you will need to register as a new user. By default, the USER role is assigned to all registered users.

After a successful login you will be able to inject an ADMIN user by pushing the corresponding button from the main page.

The ADMIN user will have a default login - "admin" and password - "admin".

At this point, you may wish to login as the ADMIN user and add some products that will become available for order by USERs.

## Authors

  - [Andrii Borozdykh](https://github.com/aborozdykh/)
