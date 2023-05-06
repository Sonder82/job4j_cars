CREATE TABLE photo
(
    id       serial   PRIMARY KEY,
    name     varchar  not null,
    path     varchar  not null unique
);