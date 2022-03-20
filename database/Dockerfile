FROM postgres:14.2

COPY ./schema.sql /docker-entrypoint-initdb.d/1_schema.sql
COPY ./data.sql /docker-entrypoint-initdb.d/2_data.sql
