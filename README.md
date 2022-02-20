Requisitos:
    Tener instalado docker
    
Pasos para ejecutar el programa:
Ejecutar el comando: docker-compose up

Para autenticación se tiene las siguientes credenciales:
1. usuario:
uancona
Contraseña:
admin

2. usuario:
sbojorquez
Contraseña:
admin

3. usuario:
user
Contraseña:
admin

4. usuario:
ulisesa
Contraseña:
admin

Para ver la documentacionen swagger:
http://localhost:8090/swagger-ui/index.html


# Resume module service with Oauth 2.0 Security

This is an implementation using Oauth 2.0 as a client server for security. The service has the 
same funcionality of view, create and edit of employee's resumes with scopes defined as roles
ROLE_USER, ROLE_MANAGER, ROLE_ADMIN

## Requirements
Docker desktop

## How to run the program

#### 1. Open a cmd prompt and run the following commands
##### netsh int ip add addr 1 10.5.0.4/32 st=ac sk=tr
##### netsh int ip add addr 1 10.5.0.5/32 st=ac sk=tr
##### netsh int ip add addr 1 10.5.0.6/32 st=ac sk=tr
##### netsh int ip add addr 1 10.5.0.7/32 st=ac sk=tr

### 2. Open a command prompt in the main root
In the main root run the command "docker-compose up"

### 3. Log in 
Now, login in the redirect login form found in http://localhost:8080. The following users and passwords are present:
#### Username: julio.vargas@theksquaregroup.com, Password: user, ROLE_USER
#### Username: guillermo.ceme@theksquaregroup.com, Password: manager, ROLE_MANAGER
#### Username: carlos.reyes@theksquaregroup.com, Password: admin, ROLE_ADMIN

## Swagger documentation 
http://localhost:8090/swagger-ui/index.html