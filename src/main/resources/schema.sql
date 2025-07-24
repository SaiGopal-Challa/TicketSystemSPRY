-- src/main/resources/schema.sql

CREATE TABLE IF NOT EXISTS event (
id UUID PRIMARY KEY,
name VARCHAR(255)  NOT NULL,
 event_date  TIMESTAMP  NOT NULL,
location VARCHAR(255) NOT NULL,
total_seats INT NOT NULL CHECK (total_seats >= 0),
is_deleted  BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS seat_hold (
	hold_id      UUID           PRIMARY KEY,
  event_id     UUID           NOT NULL,
  user_id      UUID           NOT NULL,
  seat_count   INT            NOT NULL CHECK (seat_count > 0),
  created_at   TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
  status       VARCHAR(20)    NOT NULL,              
  CONSTRAINT fk_seathold_event
  FOREIGN KEY (event_id) REFERENCES event(id)
);

CREATE TABLE IF NOT EXISTS booking (
booking_id    UUID           PRIMARY KEY,
event_id      UUID           NOT NULL,
user_id       UUID           NOT NULL,
seat_count    INT            NOT NULL CHECK (seat_count > 0),
booked_at     TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
status        VARCHAR(20)    NOT NULL,              -- CONFIRMED, CANCELED
hold_id       UUID           NULL,
cancel_reason VARCHAR(255)  NULL,
CONSTRAINT fk_booking_event 
FOREIGN KEY (event_id) REFERENCES event(id),
CONSTRAINT fk_booking_hold
FOREIGN KEY (hold_id)  REFERENCES seat_hold(hold_id)
);
