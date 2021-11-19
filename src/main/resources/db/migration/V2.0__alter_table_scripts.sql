ALTER TABLE reservation ADD CONSTRAINT fk_room FOREIGN KEY (room_id) REFERENCES room;
