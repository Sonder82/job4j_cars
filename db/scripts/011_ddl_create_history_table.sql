CREATE TABLE history (
id      int       PRIMARY KEY,
startAt TimeStamp WITHOUT TIME ZONE DEFAULT now(),
endAt   TimeStamp WITHOUT TIME ZONE DEFAULT now(),
owner_id int      not null REFERENCES owners(id)
);