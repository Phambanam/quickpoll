FROM postgres:latest
# Create database and user
ENV POSTGRES_USER postgres
ENV POSTGRES_PASSWORD postgres
ENV POSTGRES_DB quick_poll


COPY ./src/main/resources/sql/insertdata.sql ./docker-entrypoint-initdb.d/
EXPOSE 5432
CMD ["postgres"]