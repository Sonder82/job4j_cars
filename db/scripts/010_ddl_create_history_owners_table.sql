CREATE TABLE history_owners (
   id       serial PRIMARY KEY,
   car_id   int    not null REFERENCES car(id),
   owner_id int    not null REFERENCES owners(id)
);