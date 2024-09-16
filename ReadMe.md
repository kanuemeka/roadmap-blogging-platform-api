# Roadmap Blogging Platform API
This is a practice project for the RoadMap Blogging Platform exercise frorm Roadmap.sh
See https://roadmap.sh/projects/blogging-platform-api for more details.

# Architecture
## Requirements
The application is a microservice based on Java (Version 21 but Version 17 can be used).
I use Maven to manage the applications dependencies (min version 3.5.x).
The application integrates with MongoDB, so you should have an installation for this. I recommend MongoDB Atlas online.

# Usage
## MongoDB
Firstly, create a mongodb instance and set up a user.
Add the `spring.data.mongodb.uri` property in the `src/main/resources/application.properties` file.

For example
```properties
spring.data.mongodb.uri=mongodb+srv://dbuser:{PASSWORD_HERE}@{MONGO_HOST}/{BLOG_DB}?authMechanism=SCRAM-SHA-1
```
## Run Application
Build the application code via Maven
```
mvn clean install
```
Run the application with the command below
```
mvn spring-boot:run
```