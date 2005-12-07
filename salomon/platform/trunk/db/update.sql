/* 19.07.2005 nico adding tasks.task_nr field */

-- before adding not null column
DELETE FROM TASKS;

-- adding new column
ALTER TABLE TASKS
ADD TASK_NR INTEGER NOT NULL;

-- unique index 
CREATE UNIQUE INDEX UI_TASKS_ID_NR
ON TASKS (TASK_ID,TASK_NR);

-- setting appropriate order of columns
alter table TASKS
alter TASK_ID position 1;
alter table TASKS
alter PROJECT_ID position 2;
alter table TASKS
alter PLUGIN_ID position 3;
alter table TASKS
alter TASK_NR position 4;
alter table TASKS
alter TASK_NAME position 5;
alter table TASKS
alter TASK_INFO position 6;
alter table TASKS
alter PLUGIN_SETTINGS position 7;
alter table TASKS
alter PLUGIN_RESULT position 8;
alter table TASKS
alter START_TIME position 9;
alter table TASKS
alter STOP_TIME position 10;
alter table TASKS
alter STATUS position 11;
alter table TASKS
alter LM_DATE position 12;


/* 18.08.2005 nico adding projects.env field */

ALTER TABLE PROJECTS
ADD ENV VARCHAR(1000);

-- setting columns order
alter table PROJECTS
alter LM_DATE position 6;

/* 7.12.2005 nico adding datasets_solution_id field */

-- before adding not null column
DELETE FROM DATASETS;

-- adding new column
ALTER TABLE DATASETS
ADD SOLUTION_ID INTEGER NOT NULL;

alter table DATASETS
add constraint FK_DATASETS_SOLUTIONS
foreign key (SOLUTION_ID)
references SOLUTIONS(SOLUTION_ID)
using index FK_DATASETS;



