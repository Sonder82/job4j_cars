CREATE TABLE car
(
    id        serial   PRIMARY KEY,
    name      varchar  not null,
    engine_id int      not null    REFERENCES engine(id)
);