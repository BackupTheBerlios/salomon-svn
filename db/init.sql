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

insert into "datasets"
values (3, 'all_data', 'wszystkie dane');

-- dataset_items 
insert into  "dataset_items"
values (1, 1, 'cars', 'price >= 50000');

insert into  "dataset_items"
values (2, 1, 'car_sales', 'payment_type = ''card''');

-- projects
insert into "projects"
values (1, 'Project1', 'test projet 1');

insert into "projects"
values (2, 'Project2', 'test projet 2');

insert into "projects"
values (3, 'Project3', 'test projet 3');

-- plugins
INSERT INTO "plugins"("plugin_id","name","info","location")
values (1, 'Average Price', 'Plugin computes average price of sold cars', 'plugins\AveragePrice.jar');

INSERT INTO "plugins"("plugin_id","name","info","location")
values (2, 'Simple SQL Console', 'Plugin is simple SQL Console', 'plugins\SimpleSQLConsole.jar');

-- tasks
INSERT INTO "tasks"("task_id","project_id","plugin_id","name","info", "plugin_settings","plugin_result", "status")
values (1, 1, 1, 'task1', 'test task1', '', '', 'AC');

INSERT INTO "tasks"("task_id","project_id","plugin_id","name","info", "plugin_settings","plugin_result", "status")
values (2, 2, 1, 'task1', 'test task1', '', '', 'AC');

INSERT INTO "tasks"("task_id","project_id","plugin_id","name","info", "plugin_settings","plugin_result", "status")
values (3, 1, 2, 'task2', 'test task2', '', '', 'AC');


