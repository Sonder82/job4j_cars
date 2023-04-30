CREATE TABLE owners
(
    id        serial   PRIMARY KEY,
    name      varchar  not null,
    user_id   int      not null REFERENCES auto_user(id)
);