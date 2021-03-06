/******************************************************************************/
/****              Generated by IBExpert 2004-09-29 22:07:00               ****/
/******************************************************************************/

SET SQL DIALECT 3;

SET NAMES ISO8859_2;


/******************************************************************************/
/****                                Tables                                ****/
/******************************************************************************/

CREATE TABLE SOLUTIONS (
    SOLUTION_ID    INTEGER NOT NULL,
    SOLUTION_NAME  VARCHAR(25) NOT NULL,
    SOLUTION_INFO  VARCHAR(250),
    HOSTNAME       VARCHAR(250),
    DB_PATH        VARCHAR(250) NOT NULL,
    USERNAME       VARCHAR(25) NOT NULL,
    PASSWD         VARCHAR(25) NOT NULL,
    LM_DATE        TIMESTAMP
);


CREATE TABLE PROJECTS (
    PROJECT_ID    INTEGER NOT NULL,
    SOLUTION_ID   INTEGER NOT NULL,
    PROJECT_NAME  VARCHAR(25),
    PROJECT_INFO  VARCHAR(250),
    ENV 	  VARCHAR(1000),
    LM_DATE       TIMESTAMP
);


CREATE TABLE PLUGINS (
    PLUGIN_ID    INTEGER NOT NULL,
    PLUGIN_NAME  VARCHAR(25) NOT NULL,
    LOCATION     VARCHAR(100) NOT NULL,
    PLUGIN_INFO  VARCHAR(250),
    LM_DATE      TIMESTAMP
);



CREATE TABLE TASKS (
    TASK_ID          INTEGER NOT NULL,
    PROJECT_ID       INTEGER NOT NULL,
    PLUGIN_ID        INTEGER NOT NULL,
    TASK_NAME        VARCHAR(25) NOT NULL,
    TASK_INFO        VARCHAR(250),
    PLUGIN_SETTINGS  VARCHAR(1000),
    PLUGIN_RESULT    VARCHAR(1000),
    START_TIME       TIME,
    STOP_TIME        TIME,
    STATUS           VARCHAR(2) NOT NULL,
    LM_DATE          TIMESTAMP
);


CREATE TABLE DATASETS (
    DATASET_ID    INTEGER NOT NULL,
    DATASET_NAME  VARCHAR(25) NOT NULL,
    INFO          VARCHAR(250),
    LM_DATE       TIMESTAMP
);

CREATE TABLE DATASET_ITEMS (
    DATASET_ITEM_ID  INTEGER NOT NULL,
    DATASET_ID       INTEGER NOT NULL,
    TABLE_NAME       VARCHAR(50),
    CONDITION        VARCHAR(100)
);


/******************************************************************************/
/****                             Primary Keys                             ****/
/******************************************************************************/

ALTER TABLE SOLUTIONS ADD CONSTRAINT PK_SOLUTIONS PRIMARY KEY (SOLUTION_ID);

ALTER TABLE PROJECTS ADD CONSTRAINT PK_PROJECTS PRIMARY KEY (PROJECT_ID);

ALTER TABLE PLUGINS ADD CONSTRAINT PK_PLUGINS PRIMARY KEY (PLUGIN_ID);

ALTER TABLE TASKS ADD CONSTRAINT PK_TASKS PRIMARY KEY (TASK_ID);

ALTER TABLE DATASETS ADD CONSTRAINT PK_DATASETS PRIMARY KEY (DATASET_ID);

ALTER TABLE DATASET_ITEMS ADD CONSTRAINT PK_DATASET_ITEMS PRIMARY KEY (DATASET_ITEM_ID);

/******************************************************************************/
/****                             Foreign Keys                             ****/
/******************************************************************************/

ALTER TABLE PROJECTS ADD CONSTRAINT FK_PROJECTS_SOLUTIONS FOREIGN KEY (SOLUTION_ID) REFERENCES SOLUTIONS (SOLUTION_ID);

ALTER TABLE TASKS ADD CONSTRAINT FK_TASKS_PLUGINS FOREIGN KEY (PLUGIN_ID) REFERENCES PLUGINS (PLUGIN_ID);

ALTER TABLE TASKS ADD CONSTRAINT FK_TASKS_PROJECTS FOREIGN KEY (PROJECT_ID) REFERENCES PROJECTS (PROJECT_ID);

ALTER TABLE DATASET_ITEMS ADD CONSTRAINT FK_DATASET_ITEMS FOREIGN KEY (DATASET_ID) REFERENCES DATASETS (DATASET_ID);

/******************************************************************************/
/****                             Generators                               ****/
/******************************************************************************/


CREATE GENERATOR GEN_SOLUTION_ID;
SET GENERATOR GEN_SOLUTION_ID TO 1;

CREATE GENERATOR GEN_PROJECT_ID;
SET GENERATOR GEN_PROJECT_ID TO 1;

CREATE GENERATOR GEN_PLUGIN_ID;
SET GENERATOR GEN_PLUGIN_ID TO 1;

CREATE GENERATOR GEN_TASK_ID;
SET GENERATOR GEN_TASK_ID TO 1;

CREATE GENERATOR GEN_DATASET_ID;
SET GENERATOR GEN_DATASET_ID TO 1;

CREATE GENERATOR GEN_DATASET_ITEM_ID;
SET GENERATOR GEN_DATASET_ITEM_ID TO 1;

