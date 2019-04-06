# JTA

## Requirements

You will need Java 11 ($JAVA_HOME path must point to a Java 11 installation). Also, the latest
version of Docker and Node.js +10 is needed. An Integrated Development Environment (IDE) is
irreplaceable in enhancing coding productivity, the most popular choice is IntelliJ IDEA.
Gradle is used as the build system and with the use of the Gradle Wrapper there is no need to install
the Gradle standalone version.


## Battlesnakes

### Setup
 
#### Use gradle

Windows
```
gradlew.bat <command>
```
Linux
```
./gradlew <command>
```
#### Annotation processor
IntelliJ IDEA:  
* Install the Lombok plugin.
* Enable the annotation processor:  
    * File->Other Settings->Default Settings  
    * Expand Build, Execution, Deployment  
    * Expand Compiler  
    * In Annotation Processors check `Enable annotation processing`
    
#### Run the app 
```
<gradle command> bootRun
```

#### Run the docker
On linux
```
docker run --net="host" -it --rm siimveskilt/battlesnakes
```
On Windows and Mac with:
```
docker run -it --rm -p 3010:3010 -p 3005:3005 -p 3009:3009 siimveskilt/battlesnakes 
```

#### The endpoints
When the application is running, three Snake controllers will be available for Linux at the following
endpoints:

http://localhost:8090/random

http://localhost:8090/smart

http://localhost:8090/genius

And for Windows and Mac at:

http://host.docker.internal:8090/random

http://host.docker.internal:8090/smart

http://host.docker.internal:8090/genius

#### Starting new game
On the left side of the game engine screen you can see the interface for creating new games. The
required fields are the snakes’ name and the URL where the snake can be reached (the controller’s
endpoint). Clicking “Start Game” will run a game. You can also add snakes to see how good they are
at battling against each other. 

#### Validate connection
Before starting with the assignment, validate the connection between the Application and the Engine
by using the snake validator found in the bottom of the game engine screen. All good if perfect response for validation.

### Assignment

The general rules of the game:
* The world is a grid.
* The snake can only travel orthogonally along this grid.
* This world has a border that kills the snake on contact.
* The snake cannot stop moving.
* If the snake runs into itself or other snakes, it dies.
* Every time the snake eats, it grows longer.
* The goal is to grow as long as possible. 

#### Tasks
1.  ##### Run "gradlew test”. Tests are failing. 

    Among the fails you will find:
    
    ```
    com.battle.snakes.util.SnakeUtilTest > getRandomMove() FAILED
    ```
    
    The person writing the tests failed to even write this test.
    
    Write this test and finish the getRandomMove method located in the SnakeUtil class.
    
    Remember, a passing test does not equal to a good test.
    
2.  ##### Run the RandomSnakeController’s snake through the game engine.
   
    It does not look very random. 
    
    Finish the getAllowedMoves method in the SnakeUtil class to fix this Snake. 
    
    Make sure it does not collide with other snakes or itself (unless it is trapped).

3.  ##### Run the snake controlled with the SmartSnakeController through the game engine. 
    
    Finish the rest of the methods in the SnakeUtil class to make the “smart” snake smart.
    
    It is a lot better than the “random” snake.
    
    But not really that good for planning more than a step.
    
4.  #### Run the snake controlled with the GeniusSnakeController through the game engine.
    Make this snake as good as you can at playing the game.
    
    It should be better than the “smart” snake.
    
    Bonus points will be awarded for writing at least 3 tests for your “genius” snake,
    if they are meaningful. 
    
    Be warned though, copying other people’s solutions will get you no points.
    
    Reading about what other people have done and incorporating ideas is encouraged.
    
    Remember, the goal here is to create a slightly improved “smart” snake, 
    not solve the many-body problem.

## Pizza store

### Setup

#### Use gradle

Windows
```
gradlew.bat <command>
```
Linux
```
./gradlew <command>
```
#### Annotation processor
IntelliJ IDEA:  
* Install the Lombok plugin.
* Enable the annotation processor:  
    * File->Other Settings->Default Settings  
    * Expand Build, Execution, Deployment  
    * Expand Compiler  
    * In Annotation Processors check `Enable annotation processing`
    
#### Run back-end app 
```
<gradle command> bootRun
```
The Application uses a H2 in-memory database by default. To browse the database, use
http://localhost:8080/h2-console (JDBC URL: jdbc:h2:mem:testdb / user: sa)

#### Run front-end app
To start the front-end application, in the pizzastore-ui directory, first run “npm install” and then
“npm start” to start the application. 

### Assignment

The PizzaStore application manages two different roles: customers and admins. 
These two roles  have different permissions within the application.
Customers are able to create a new order and  view their own orders 
whereas admins can see all the orders and also have permission to cancel  them.  

Existing users:  

Admin - username: admin password: admin  

Customer - username: customer password: customer

#### Tasks 
At first familiarize yourself with the PizzaStore application and its code.
There are some things  missing from the application that have to be implemented: 
look for TODOs in the code. 
Start by  reading the CHANGELOG.md file located in this assignment folder.

1.  ##### Bacon topping is missing from the pizza toppings

    Add a new bacon topping to the database and display the option in the UI.
    
    Existing image for the topping can be found in /src/assets/toppings
    
2.  ##### Customer’s details are not validated when an order is placed
    
    Add following validations for customer details form with appropriate error messages:
    
    * Name: required
    
    * Address: required
    
    * Phone: required
    
    * Email: required and matches a valid email pattern
    
    NB! The validations must be done both on the client and server side.
    Example: <path to test assignment>/pizzastore/README.pdf

3.  ##### Users cannot see the total price of each order in ORDERS/MY ORDERS view
   
    Calculate the total price of the customer’s order on the server side.
    
    Total price of the order should then appear like this: <path to test assignment>/pizzastore/README.pdf
    
4.  ##### Order cancelling in admin’s ORDERS view is not working properly on the server side
   
    Cancelling an order currently deletes it from the database, but we would actually like to keep
    the order for audit purposes. 
   
    Implement logical deletion by finding a way to deactivate orders
    instead of physically deleting them. 
   
    Behaviour in the UI must remain the same: cancelled
    orders must not be shown.
   
5.  ##### Currently customers do not have the option to register

    Add a registration form for customers with the following requirements:

    * Form has username and password input fields, both are required.
    * Form has validation for required fields with appropriate error messages.
    * Application checks whether username already exists or not.
    * The customer is redirected to “NEW ORDER” page after successful registration.
    * When registration fails, error message is displayed to the customer.
    * Passwords are currently stored in plain text. Implement password encoding with a
        commonly known secure algorithm of your choice. NB! Encoding must be done on the server side.
        
    Existing registration API endpoint is /auth/register.
    Example: <path to test assignment>/pizzastore/README.pdf
    
6.  ##### Production-grade database

    H2 is good enough for development but to get production ready, we definitely need a proper
    persistent database. 
    
    Let’s set up a PostgreSQL database in Docker:
    * Create a docker-compose file for a PostgreSQL database container setup. 
    * An empty file is already provided in the project root directory.
    * A separate Spring configuration has been provided at
        pizza-main\src\main\resources\application-pg.yaml. 
    * Use the same database settings in your docker-compose if possible.
    * If not, then change the Spring configuration accordingly.
    * When the docker-compose file is ready, run “docker-compose up” in the project root.
    * When the database has started, run the application with PostgreSQL profile: 
    ```gradlew bootRun -Pprofiles=pg```

    Now we have a proper persistent production-grade database.