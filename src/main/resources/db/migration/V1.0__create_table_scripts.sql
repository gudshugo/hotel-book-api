CREATE TABLE room
 (
	id bigserial NOT NULL,
	CONSTRAINT room_pkey PRIMARY KEY (id)
);

CREATE TABLE reservation (
	id bigserial NOT NULL,
	check_in date NOT NULL,
	check_out date NOT NULL,
	is_reserved bool NOT NULL,
	room_id int8 NOT NULL,
	CONSTRAINT reservation_pkey PRIMARY KEY (id)
);

