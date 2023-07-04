-- 3 bikes
INSERT INTO bikes (id, frame_number, brand, model, name, bike_type)
VALUES (100, 111111111, 'Giant', 'TCR Advanced PRO', 'Purple rain','ROAD'),
       (101, 123456789, 'Canyon', 'Speedmax CFR', 'Time wonder', 'TIMETRIAL'),
       (102, 987654321, 'Canyon', 'Lux CFR', 'Mounty', 'MOUNTAIN');

-- 10 rides met Bike 100
-- INSERT INTO rides (title_ride, sub_title_ride, distance, date, average_power, normalized_power, time_ride, bike_id)
-- VALUES ('Namiddagrit', 'Fietsrit', 71.82, '2023-06-21', 250, 254, 'PT2H11M53S', 100),
--        ('Namiddagrit', 'Fietsrit', 81.45, '2023-06-19', 273, 298, 'PT2H19M40S', 100),
--        ('Pilsjes eruit zweten', 'Fietsrit', 28.36, '2023-06-17', 320, 324, 'PT44M29S', 100),
--        ('Namiddagrit', 'Fietsrit', 30.11, '2023-06-16', 242, 278, 'PT54M49S', 100),
--        ('Namiddagrit', 'Fietsrit', 33.46, '2023-06-15', 228, 228, 'PT1H5M38S', 100),
--        ('Nachtrit', 'Fietsrit', 33.05, '2023-06-14', 286, 294, 'PT54M58S', 100),
--        ('Namiddagrit', 'Fietsrit', 38.54, '2023-06-14', 228, 255, 'PT1H11M10S', 100),
--        ('Avondrit', 'Fietsrit', 76.22, '2023-06-12', 270, 277, 'PT2H7M24S', 100),
--        ('Klein rondje met papsie', 'Fietsrit', 33.29, '2023-06-11', 207, 239, 'PT1H4M16S', 100),
--        ('Ochtendrit', 'Fietsrit', 120.08, '2023-06-10', 223, 272, 'PT1H4M16S', 100);

-- 5 bikeparts Bike 100
INSERT INTO bike_parts (id, name, part_type, current_distance_driven, max_distance, bike_id, installation_date)
VALUES
    (100, 'Chain', 'CHAIN', 0.0, 4000.0, 100, '2023-06-01'),
    (101, 'Cassette', 'CASSETTE', 0.0, 8000.0, 100, '2023-06-01'),
    (102, 'Front Tire', 'FRONTTIRE', 0.0, 5000.0, 100, '2023-06-01'),
    (103, 'Rear Tire', 'REARTIRE', 0.0, 5000.0, 100, '2023-06-01'),
    (104, 'Front Brake Pad', 'FRONTBRAKEPAD', 0.0, 10000.0, 100, '2023-06-01');

-- 5 bikeparts Bike 101
INSERT INTO bike_parts (id, name, part_type, current_distance_driven, max_distance, bike_id, installation_date)
VALUES
    (105, 'Chain', 'CHAIN', 0.0, 2000.0, 101, '2023-06-01'),
    (106, 'Cassette', 'CASSETTE', 0.0, 5000.0, 101, '2023-06-01'),
    (107, 'Front Tire', 'FRONTTIRE', 0.0, 5000.0, 101, '2023-06-01'),
    (108, 'Rear Tire', 'REARTIRE', 0.0, 5000.0, 101, '2023-06-01'),
    (109, 'Front Brake Pad', 'FRONTBRAKEPAD', 0.0, 10000.0, 101, '2023-06-01');

-- 3 bikeparts Bike 102
INSERT INTO bike_parts (id, name, part_type, current_distance_driven, max_distance, bike_id, installation_date)
VALUES
    (110, 'Chain', 'CHAIN', 0.0, 2000.0, 102, '2023-06-01'),
    (111, 'Cassette', 'CASSETTE', 0.0, 5000.0, 102, '2023-06-01'),
    (112, 'Front Tire', 'FRONTTIRE', 0.0, 5000.0, 102, '2023-06-01');