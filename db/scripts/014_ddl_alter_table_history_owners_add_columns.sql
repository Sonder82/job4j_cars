ALTER TABLE history_owners
ADD COLUMN startAt  TimeStamp WITHOUT TIME ZONE DEFAULT now(),
ADD COLUMN endAt    TimeStamp WITHOUT TIME ZONE DEFAULT now();