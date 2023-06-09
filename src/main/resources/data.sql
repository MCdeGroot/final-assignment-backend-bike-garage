-- 1 user
INSERT INTO users (username, password, email, first_name, last_name, enabled)
VALUES ('mathijs', '$2a$12$qKPVx3n3mAVuH2QZUMkrMeuyG/EsuTUlHEH0JXpZmFMNm.IkohUNK', 'mathijsdegroot@bikegarage.com',
        'Mathijs', 'de Groot', true),
       ('piet', '$2a$12$qKPVx3n3mAVuH2QZUMkrMeuyG/EsuTUlHEH0JXpZmFMNm.IkohUNK', 'pietpost@bikegarage.com', 'Piet',
        'Post', true),
       ('kees', '$2a$12$qKPVx3n3mAVuH2QZUMkrMeuyG/EsuTUlHEH0JXpZmFMNm.IkohUNK', 'keeskarel@bikegarage.com', 'Kees',
        'Karel', true);
-- authorities
INSERT INTO authorities (username, authority)
VALUES ('mathijs', 'ROLE_USER'),
       ('mathijs', 'ROLE_TRAINER'),
       ('piet', 'ROLE_USER');

-- 3 reviews
INSERT INTO reviews (id, comment_description, rating)
VALUES (100, 'Lekkerrrrrr', 8.0),
       (101, 'Lekkerrrrrr', 9.0),
       (102, 'mwah', 6.0);


-- 4 bikes
INSERT INTO bikes (id, frame_number, brand, model, name, bike_type, user_username)
VALUES (100, 111111111, 'Giant', 'TCR Advanced PRO', 'Purple rain', 'ROAD', 'mathijs'),
       (101, 123456789, 'Canyon', 'Speedmax CFR', 'Time wonder', 'TIMETRIAL', 'mathijs'),
       (102, 987654321, 'Canyon', 'Lux CFR', 'Mounty', 'MOUNTAIN', 'mathijs'),
       (103, 987654355, 'Canyon', 'Lux CFR', 'Mounty', 'MOUNTAIN', 'piet');

-- 10 rides met Bike 100
INSERT INTO rides (id, title_ride, sub_title_ride, distance, date, average_power, normalized_power, time_ride, bike_id,
                   user_username, review_id)
VALUES (100, 'Namiddagrit', 'Fietsrit', 71.82, '2023-06-21', 250, 254, '7911300000000', 100, 'mathijs', 100),
       (101, 'Namiddagrit', 'Fietsrit', 81.45, '2023-06-19', 273, 298, '8907000000000', 100, 'mathijs', 101),
       (102, 'Pilsjes eruit zweten', 'Fietsrit', 28.36, '2023-06-17', 320, 324, '1067900000000', 100, 'mathijs', 102);

INSERT INTO rides (id, title_ride, sub_title_ride, distance, date, average_power, normalized_power, time_ride, bike_id,
                   user_username)
VALUES (103, 'Namiddagrit', 'Fietsrit', 30.11, '2023-06-16', 242, 278, '1816900000000', 100, 'mathijs'),
       (104, 'Namiddagrit', 'Fietsrit', 33.46, '2023-06-15', 228, 228, '2004600000000', 100, 'mathijs'),
       (105, 'Nachtrit', 'Fietsrit', 33.05, '2023-06-14', 286, 294, '3298000000000', 100, 'mathijs'),
       (106, 'Namiddagrit', 'Fietsrit', 38.54, '2023-06-14', 228, 255, '4270000000000', 100, 'mathijs'),
       (107, 'Avondrit', 'Fietsrit', 76.22, '2023-06-12', 270, 277, '7644000000000', 100, 'mathijs'),
       (108, 'Klein rondje met papsie', 'Fietsrit', 33.29, '2023-06-11', 207, 239, '3856000000000', 100, 'mathijs'),
       (109, 'Ochtendrit', 'Fietsrit', 120.08, '2023-06-10', 223, 272, '4320000000000', 100, 'mathijs'),
       (110, 'Ochtendrit', 'Fietsrit', 120.08, '2023-06-10', 223, 272, '4320000000000', 103, 'piet');



-- 5 bikeparts Bike 100
INSERT INTO bike_parts (id, part_type, current_distance_driven, max_distance, bike_id, installation_date)
VALUES (100, 'CHAIN', 0.0, 4000.0, 100, '2023-06-01'),
       (101, 'CASSETTE', 0.0, 8000.0, 100, '2023-06-01'),
       (102, 'FRONTTIRE', 0.0, 5000.0, 100, '2023-06-01'),
       (103, 'REARTIRE', 0.0, 5000.0, 100, '2023-06-01'),
       (104, 'FRONTBRAKEPAD', 0.0, 10000.0, 100, '2023-06-01');

-- 5 bikeparts Bike 101
INSERT INTO bike_parts (id, part_type, current_distance_driven, max_distance, bike_id, installation_date)
VALUES (105, 'CHAIN', 0.0, 2000.0, 101, '2023-06-01'),
       (106, 'CASSETTE', 0.0, 5000.0, 101, '2023-06-01'),
       (107, 'FRONTTIRE', 0.0, 5000.0, 101, '2023-06-01'),
       (108, 'REARTIRE', 0.0, 5000.0, 101, '2023-06-01'),
       (109, 'FRONTBRAKEPAD', 0.0, 10000.0, 101, '2023-06-01');

-- 3 bikeparts Bike 102
INSERT INTO bike_parts (id, part_type, current_distance_driven, max_distance, bike_id, installation_date)
VALUES (110, 'CHAIN', 0.0, 2000.0, 102, '2023-06-01'),
       (111, 'CASSETTE', 0.0, 5000.0, 102, '2023-06-01'),
       (112, 'FRONTTIRE', 0.0, 5000.0, 102, '2023-06-01');