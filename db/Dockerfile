FROM postgres:alpine
ADD scripts/projectsapi-db.sql /docker-entrypoint-initdb.d
ADD scripts/authorization-db-sql.sql /docker-entrypoint-initdb.d

RUN chmod a+r /docker-entrypoint-initdb.d/*
EXPOSE 6666