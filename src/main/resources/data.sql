-- src/main/resources/data.sql

INSERT INTO event (id, name, event_date, location, total_seats, is_deleted)
VALUES
    ('11111111-1111-1111-1111-111111111111','Concert A','2025-08-15 19:00:00','Stadium X',100,false),
    ('22222222-2222-2222-2222-222222222222','Play B','2025-09-05 18:30:00','Theatre Y',50,false);

INSERT INTO seat_hold (hold_id, event_id, user_id, seat_count, created_at, status)
VALUES
    ('aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaa1','11111111-1111-1111-1111-111111111111', '99999999-9999-9999-9999-999999999999',2,CURRENT_TIMESTAMP,'HELD'),
    ('aaaaaaa2-aaaa-aaaa-aaaa-aaaaaaaaaaa2','11111111-1111-1111-1111-111111111111', '88888888-8888-8888-8888-888888888888',23,CURRENT_TIMESTAMP,'HELD');

INSERT INTO booking (booking_id, event_id, user_id, seat_count, booked_at, status, hold_id, cancel_reason)
VALUES
    ('bbbbbbb1-bbbb-bbbb-bbbb-bbbbbbbbbbb1','22222222-2222-2222-2222-222222222222',
     '88888888-8888-8888-8888-888888888888',3,CURRENT_TIMESTAMP,'CONFIRMED',NULL,NULL);
