-- to enable usage many times
DROP TABLE "car_sales";
DROP TABLE "cars";
DROP TABLE "users";
DROP TABLE "dataset_items";
DROP TABLE "datasets";


CREATE TABLE "users" (
    "user_id" INTEGER NOT NULL,
    "name" VARCHAR (32) CHARACTER SET ASCII NOT NULL COLLATE ASCII,
    "surname" VARCHAR (32) CHARACTER SET ASCII COLLATE ASCII,
    "nick" VARCHAR (16) CHARACTER SET ASCII COLLATE ASCII,	
    "email" VARCHAR (64) CHARACTER SET ASCII COLLATE ASCII,
    "age" INTEGER,
    "city" VARCHAR (32) CHARACTER SET ASCII COLLATE ASCII
    );


-- Primary keys definition 

ALTER TABLE "users" ADD CONSTRAINT "PK_users" PRIMARY KEY ("user_id");

-- Indices definition 

-- CREATE UNIQUE INDEX "PK_users" ON "users" ("user_id");



-- Tablica: datasets 

CREATE TABLE "datasets" (
   "dataset_id" INTEGER NOT NULL,  
   "dataset_name" VARCHAR (32) CHARACTER SET ASCII NOT NULL COLLATE ASCII,
   "info" VARCHAR (256) CHARACTER SET ASCII COLLATE ASCII
   );


-- Primary keys definition 

ALTER TABLE "datasets" ADD CONSTRAINT "PK_datasets" PRIMARY KEY ("dataset_id");


-- Indices definition 

--CREATE UNIQUE INDEX "PK_datasets" ON "datasets" ("dataset_id");



-- Tablica: dataset_items 

CREATE TABLE "dataset_items" (
    "dataset_item_id" INTEGER NOT NULL,
    "dataset_id" INTEGER NOT NULL,
    "table_name" VARCHAR (32) CHARACTER SET ASCII NOT NULL COLLATE ASCII,
    "condition" VARCHAR (64) CHARACTER SET ASCII COLLATE ASCII
    );



-- Primary keys definition 

ALTER TABLE "dataset_items" ADD CONSTRAINT "PK_dataset_items" PRIMARY KEY ("dataset_item_id");


-- Foreign keys definition 

ALTER TABLE "dataset_items" ADD CONSTRAINT "FK_dataset_items" FOREIGN KEY ("dataset_id") REFERENCES "datasets" ("dataset_id");


-- Indices definition 

--CREATE INDEX "FK_dataset_items" ON "dataset_items" ("dataset_id");
--CREATE UNIQUE INDEX "PK_dataset_items" ON "dataset_items" ("dataset_item_id");


-- Tablica: cars 

CREATE TABLE "cars" (
    "car_id" INTEGER NOT NULL,
    "brand" VARCHAR (32) CHARACTER SET ASCII COLLATE ASCII,
    "name" VARCHAR (32) CHARACTER SET ASCII COLLATE ASCII,
    "engine_cc" INTEGER,
    "engine_power" INTEGER,
    "milage" INTEGER,
    "price" INTEGER,
    "info" VARCHAR (256) CHARACTER SET ASCII COLLATE ASCII
    );



-- Primary keys definition 
ALTER TABLE "cars" ADD CONSTRAINT "PK_cars" PRIMARY KEY ("car_id");


-- Tablica: car_sales 

CREATE TABLE "car_sales" (
    "car_id" INTEGER,
    "user_id" INTEGER,
    "payment_type" VARCHAR (32) CHARACTER SET ASCII COLLATE ASCII
    );


-- Unique keys definition 

ALTER TABLE "car_sales" ADD CONSTRAINT "PK_car_sales" UNIQUE ("car_id", "user_id");


-- Foreign keys definition 

ALTER TABLE "car_sales" ADD CONSTRAINT "FK_car_sales" FOREIGN KEY ("car_id") REFERENCES "cars" ("car_id");
ALTER TABLE "car_sales" ADD CONSTRAINT "FK_car_sales1" FOREIGN KEY ("user_id") REFERENCES "users" ("user_id");


-- Indices definition 

--CREATE INDEX "FK_car_sales" ON "car_sales" ("car_id");
--CREATE INDEX "FK_car_sales1" ON "car_sales" ("user_id");
--CREATE UNIQUE INDEX "PK_car_sales" ON "car_sales" ("car_id", "user_id");


-- Indices definition 

--CREATE UNIQUE INDEX "PK_cars" ON "cars" ("car_id");

-- inserty
insert into "users"
values (1, 'Nikodem', 'Jura', 'nico', 'nico@icslab.agh.edu.pl', '21', 'Wieprz');

insert into "users"
values (2, 'Krzysztof', 'Rajda', 'krzycho', 'krzycho@student.uci.agh.edu.pl', '22', 'Anrychow');

insert into "users"
values (3, 'Kuba', 'Galkowski', 'pan galka', 'avi@student.uci.agh.edu.pl', '22', 'Sosnowiec');

insert into "users"
values (4, 'Tomek', 'Jaworski', 'jawora', 'tjawor@student.uci.agh.edu.pl', '22', 'Tarnow');

-- cars 
insert into "cars"
values (1, 'Porsche', '911 Turbo', 3600, 450, 30000, 100000, '');
insert into "cars"
values (2, 'Ferrari', 'Maranello', 3600, 400, 50000, 150000, '');
insert into "cars"
values (3, 'Mercedes', 'SL 500', 5000, 400, 60000, 250000, '');
insert into "cars"
values (4, 'Mercedes', 'C 220', 220, 193, 25000, 150000, '');
insert into "cars"
values (5, 'Renault', 'Megane', 1999, 115, 10000, 40000, '');
insert into "cars"
values (6, 'Renault', 'Megane Scenic', 1999, 115, 15000, 45000, '');


-- car sales

insert into "car_sales"
values (1, 1 , 'card');

insert into "car_sales"
values (2, 3 , 'card');

insert into "car_sales"
values (3, 2 , 'cash');

insert into "car_sales"
values (4, 3 , 'card');

insert into "car_sales"
values (5, 1 , 'card');

-- datasets
insert into "datasets"
values (1, 'test1', 'testowy data set');

insert into "datasets"
values (2, 'test2', 'drugi testowy data set');

-- dataset_items 
insert into  "dataset_items"
values (1, 1, 'car_sales', 'price >= 50000');

insert into  "dataset_items"
values (2, 1, 'car_sales', 'payment_type = ''card''');








