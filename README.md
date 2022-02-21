# Projects Management Module integrated with Oauth 2.0 Security Module

This is a project that manages differents software projects and their tasks, in order to track them. This project in integrated with Oauth 2.0 security module in order to make the service secure.

## Requirements
- Docker desktop installed

## How to run the program

#### 1. Open a cmd prompt and run the following commands
##### netsh int ip add addr 1 10.5.0.4/32 st=ac sk=tr
##### netsh int ip add addr 1 10.5.0.5/32 st=ac sk=tr
##### netsh int ip add addr 1 10.5.0.6/32 st=ac sk=tr
##### netsh int ip add addr 1 10.5.0.7/32 st=ac sk=tr

### 2. Open a command prompt in the main root
In the main root run the command "docker-compose up"

### 3. Log in 
http://10.5.0.6:9000/login

Main url for endpoints:
http://10.5.0.5:8080/

Example: 
http://10.5.0.5:8080/admin

Users:
#### Username: julio.vargas@theksquaregroup.com, Password: user, ROLE_USER
#### Username: guillermo.ceme@theksquaregroup.com, Password: manager, ROLE_MANAGER
#### Username: carlos.reyes@theksquaregroup.com, Password: admin, ROLE_ADMIN

## Swagger documentation 
http://10.5.0.5:8080/swagger-ui/index.html
