Pasos para ejecutar el programa:
Requisitos:
Tener el plugin de tomcat maven y poder ejecutar el comando:
    -mvn

Pasos:
1. Crear una base de datos postgres llamada projectsapi con el usuario y contraseña configurados correctamente.
2. Ejecutar el script del archivo projectsapi_db .sql e importar los datos de prueba.
3. En la carpeta raiz del proyecto ejecutar el comando:
    - mvn spring-boot:run

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