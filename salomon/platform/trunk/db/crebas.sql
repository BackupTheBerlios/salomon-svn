-- to enable usage many times
DROP TABLE "car_sales";
DROP TABLE "cars";
DROP TABLE "users";
DROP TABLE "dataset_items";
DROP TABLE "datasets";
DROP TABLE "tasks";
DROP TABLE "plugins";
DROP TABLE "projects";

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


-- Tablica: projects 

CREATE TABLE "projects" (
    "project_id" INTEGER NOT NULL,
    "name" VARCHAR (32) CHARACTER SET ASCII NOT NULL COLLATE ASCII,
    "info" VARCHAR (256) CHARACTER SET ASCII COLLATE ASCII
    );


-- Primary keys definition 
ALTER TABLE "projects" ADD CONSTRAINT "PK_projects" PRIMARY KEY ("project_id");

-- Tablica: plugins

CREATE TABLE "plugins" (
    "plugin_id" INTEGER NOT NULL,
    "name" VARCHAR (32) CHARACTER SET ASCII NOT NULL COLLATE ASCII,    
    "info" VARCHAR (256) CHARACTER SET ASCII COLLATE ASCII,    
    "location" VARCHAR (128) CHARACTER SET ASCII COLLATE ASCII
    );

-- Primary keys definition 
ALTER TABLE "plugins" ADD CONSTRAINT "PK_plugins" PRIMARY KEY ("plugin_id");


-- Tablica: tasks

CREATE TABLE "tasks" (
    "task_id" INTEGER NOT NULL,
    "project_id" INTEGER NOT NULL, 
    "plugin_id" INTEGER NOT NULL,
    "name" VARCHAR (32) CHARACTER SET ASCII COLLATE ASCII,    
    "info" VARCHAR (256) CHARACTER SET ASCII COLLATE ASCII,    
    "plugin_settings" VARCHAR (1024) CHARACTER SET ASCII COLLATE ASCII,
    "plugin_result" VARCHAR (1024) CHARACTER SET ASCII COLLATE ASCII,
    "status" VARCHAR (2) CHARACTER SET ASCII COLLATE ASCII,
    "date_started" DATE,
    "date_finished" DATE
    );



-- Primary keys definition 
ALTER TABLE "tasks" ADD CONSTRAINT "PK_tasks" PRIMARY KEY ("task_id");


-- Foreign keys definition 

ALTER TABLE "tasks" ADD CONSTRAINT "FK_tasks_projects" FOREIGN KEY ("project_id") REFERENCES "projects" ("project_id");

ALTER TABLE "tasks" ADD CONSTRAINT "FK_tasks_plugins" FOREIGN KEY ("plugin_id") REFERENCES "plugins" ("plugin_id");









