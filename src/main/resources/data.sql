-- 3 users
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
       ('mathijs', 'ADMIN'),
       ('piet', 'ROLE_USER'),
       ('kees', 'ROLE_USER');

-- 3 reviews
INSERT INTO reviews (id, comment_description, rating)
VALUES (100, 'Lekkerrrrrr', 8.0),
       (101, 'Lekkerrrrrr', 9.0),
       (102, 'mwah', 6.0);


-- 2 bikes user mathijs and 2 bikes user piet and 2 bikes kees
INSERT INTO bikes (id, frame_number, brand, model, name, group_set, bike_type, user_username)
VALUES (100, 111111111, 'Giant', 'TCR Advanced PRO', 'Purple rain', 'Shimano Ultegra', 'ROAD', 'mathijs'),
       (101, 123456789, 'Canyon', 'Speedmax CFR', 'Time wonder', 'Shimano Ultegra DI2', 'TIMETRIAL', 'mathijs'),
       (102, 987654321, 'Cervélo', 'S3', 'Orange Rocket', 'Shimano Ultegra', 'ROAD', 'piet'),
       (103, 987654355, 'Canyon', 'Lux CFR', 'Mounty', 'SRAM XX1 EAGLE AXS', 'MOUNTAIN', 'piet'),
       (104, 987654321, 'Canyon', 'Aeroad CFR', 'Passion Fruit', 'SRAM Red', 'ROAD', 'kees'),
       (105, 987654355, 'Willier', 'Filante', 'Red Velvet', 'Shimano Dura Ace', 'ROAD', 'kees');

-- 3 rides Mathijs with Bike 100 with review
INSERT INTO rides (id, title_ride, sub_title_ride, distance, date, average_power, normalized_power, time_ride, bike_id,
                   user_username, review_id)
VALUES (100, 'Namiddagrit', 'Fietsrit', 71.82, '2023-06-21', 250, 254, '7911300000000', 100, 'mathijs', 100),
       (101, 'Namiddagrit', 'Fietsrit', 81.45, '2023-06-19', 273, 298, '8907000000000', 100, 'mathijs', 101),
       (102, 'Pilsjes eruit zweten', 'Fietsrit', 28.36, '2023-06-17', 320, 324, '1067900000000', 100, 'mathijs', 102);

-- 5 rides Mathijs with Bike 100 without review
INSERT INTO rides (id, title_ride, sub_title_ride, distance, date, average_power, normalized_power, time_ride, bike_id,
                   user_username)
VALUES (103, 'Namiddagrit', 'Fietsrit', 180.04, '2023-06-16', 242, 278, '6407400000000', 100, 'mathijs'),
       (104, 'Namiddagrit', 'Fietsrit', 33.46, '2023-06-15', 228, 228, '2004600000000', 100, 'mathijs'),
       (112, 'Ochtendrit', 'Fietsrit', 91.84, '2023-06-14', 260, 287, '10275200000000', 100, 'mathijs'),
       (113, 'Avondrit', 'Fietsrit', 172.68, '2023-06-13', 275, 300, '21038400000000', 100, 'mathijs'),
       (114, 'Middagrit', 'Fietsrit', 126.54, '2023-06-12', 233, 259, '13706400000000', 100, 'mathijs');

-- 3 rides Mathijs with Bike 101
INSERT INTO rides (id, title_ride, sub_title_ride, distance, date, average_power, normalized_power, time_ride, bike_id,
                   user_username)
VALUES (105, 'Nachtrit', 'Fietsrit', 33.05, '2023-06-14', 286, 294, '3298000000000', 101, 'mathijs'),
       (106, 'Namiddagrit', 'Fietsrit', 38.54, '2023-06-14', 228, 255, '4270000000000', 101, 'mathijs'),
       (107, 'Avondrit', 'Fietsrit', 76.22, '2023-06-12', 270, 277, '7644000000000', 101, 'mathijs');

-- 3 rides from Piet with bike 102
INSERT INTO rides (id, title_ride, sub_title_ride, distance, date, average_power, normalized_power, time_ride, bike_id,
                   user_username)
VALUES (108, 'Klein rondje met papsie', 'Fietsrit', 33.29, '2023-06-11', 207, 239, '3856000000000', 102, 'piet'),
       (109, 'Ochtendrit', 'Fietsrit', 120.08, '2023-06-10', 223, 272, '4320000000000', 102, 'piet'),
       (110, 'Nog een rondje maar', 'Fietsrit', 120.08, '2023-06-09', 223, 272, '4320000000000', 102, 'piet');

-- 1 rides from Piet with bike 103
INSERT INTO rides (id, title_ride, sub_title_ride, distance, date, average_power, normalized_power, time_ride, bike_id,
                   user_username)
VALUES (111, 'Karren maar', 'Fietsrit', 165.06, '2023-06-13', 207, 239, '3856000000000', 103, 'piet');

-- 5 bikeparts Bike 100
INSERT INTO bike_parts (id, part_type, current_distance_driven, max_distance, bike_id, installation_date)
VALUES (100, 'CHAIN', 0.0, 3000.0, 100, '2023-06-01'),
       (101, 'CASSETTE', 0.0, 5000.0, 100, '2023-06-01'),
       (102, 'FRONTTIRE', 0.0, 3000.0, 100, '2023-06-01'),
       (103, 'REARTIRE', 0.0, 2500.0, 100, '2023-06-01'),
       (104, 'FRONTBRAKEPAD', 0.0, 10000.0, 100, '2023-06-01');

-- 5 bikeparts Bike 101
INSERT INTO bike_parts (id, part_type, current_distance_driven, max_distance, bike_id, installation_date)
VALUES (105, 'CHAIN', 0.0, 3000.0, 101, '2023-06-01'),
       (106, 'CASSETTE', 0.0, 5000.0, 101, '2023-06-01'),
       (107, 'FRONTTIRE', 0.0, 3000.0, 101, '2023-06-01'),
       (108, 'REARTIRE', 0.0, 2500.0, 101, '2023-06-01'),
       (109, 'FRONTBRAKEPAD', 0.0, 10000.0, 101, '2023-06-01');

-- 3 bikeparts Bike 102
INSERT INTO bike_parts (id, part_type, current_distance_driven, max_distance, bike_id, installation_date)
VALUES (110, 'CHAIN', 0.0, 3000.0, 102, '2023-06-01'),
       (111, 'CASSETTE', 0.0, 5000.0, 102, '2023-06-01'),
       (112, 'FRONTTIRE', 0.0, 4000.0, 102, '2023-06-01');

-- Update current_distance_driven for bike_parts of Bike 100
UPDATE bike_parts
SET current_distance_driven = (
    SELECT COALESCE(SUM(distance), 0)
    FROM rides
    WHERE bike_id = 100
)
WHERE bike_id = 100;

-- Update current_distance_driven for bike_parts of Bike 101
UPDATE bike_parts
SET current_distance_driven = (
    SELECT COALESCE(SUM(distance), 0)
    FROM rides
    WHERE bike_id = 101
)
WHERE bike_id = 101;

-- Update current_distance_driven for bike_parts of Bike 102
UPDATE bike_parts
SET current_distance_driven = (
    SELECT COALESCE(SUM(distance), 0)
    FROM rides
    WHERE bike_id = 102
)
WHERE bike_id = 102;

-- Voeg een nieuwe kolom "current_distance_driven_decimal" toe aan de bike_parts tabel
ALTER TABLE bike_parts ADD current_distance_driven_decimal DECIMAL(10, 2);

-- Update de nieuwe kolom "current_distance_driven_decimal" met de waarde van de bestaande "current_distance_driven" kolom
UPDATE bike_parts SET current_distance_driven_decimal = current_distance_driven;

-- Verwijder de oude kolom "current_distance_driven"
ALTER TABLE bike_parts DROP COLUMN current_distance_driven;

-- Hernoem de nieuwe kolom "current_distance_driven_decimal" naar "current_distance_driven"
ALTER TABLE bike_parts RENAME COLUMN current_distance_driven_decimal TO current_distance_driven;