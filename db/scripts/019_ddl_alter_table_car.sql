ALTER TABLE car ADD COLUMN body_id int not null REFERENCES body(id);
ALTER TABLE car ADD COLUMN modelCar_id int not null REFERENCES modelCar(id);
ALTER TABLE car ADD COLUMN transmission_id int not null REFERENCES transmission(id);