
ALTER TABLE auto_post
DROP COLUMN photo_id;
ALTER TABLE auto_post ADD COLUMN photo_id int REFERENCES photo(id);
