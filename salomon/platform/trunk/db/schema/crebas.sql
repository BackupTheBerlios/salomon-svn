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
    C_DATE         TIMESTAMP NOT NULL,
    LM_DATE        TIMESTAMP NOT NULL
);


CREATE TABLE PROJECTS (
    PROJECT_ID    INTEGER NOT NULL,
    SOLUTION_ID   INTEGER NOT NULL,
    PROJECT_NAME  VARCHAR(25),
    PROJECT_INFO  VARCHAR(250),
    ENV           VARCHAR(1000),
    C_DATE        TIMESTAMP NOT NULL,
    LM_DATE       TIMESTAMP NOT NULL
);


CREATE TABLE PLUGINS (
    PLUGIN_ID    INTEGER NOT NULL,
    PLUGIN_NAME  VARCHAR(25) NOT NULL,
    LOCATION     VARCHAR(100) NOT NULL,
    PLUGIN_INFO  VARCHAR(250),
    PLUGIN_TYPE  VARCHAR(1),
    C_DATE       TIMESTAMP NOT NULL,
    LM_DATE      TIMESTAMP NOT NULL
);


CREATE TABLE TASKS (
    TASK_ID          INTEGER NOT NULL,
    PROJECT_ID       INTEGER NOT NULL,
    PLUGIN_ID        INTEGER NOT NULL,
    TASK_NR          INTEGER NOT NULL,
    TASK_NAME        VARCHAR(25) NOT NULL,
    TASK_INFO        VARCHAR(250),
    PLUGIN_SETTINGS  VARCHAR(1000),
    PLUGIN_RESULT    VARCHAR(1000),
    START_TIME       TIME,
    STOP_TIME        TIME,
    STATUS           VARCHAR(2) NOT NULL,
    C_DATE           TIMESTAMP NOT NULL,
    LM_DATE          TIMESTAMP NOT NULL
);


CREATE TABLE DATASETS (
    DATASET_ID    INTEGER NOT NULL,
    SOLUTION_ID   INTEGER NOT NULL,
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


CREATE TABLE ATTRIBUTESETS (
    ATTRIBUTESET_ID    INTEGER NOT NULL,
    SOLUTION_ID        INTEGER NOT NULL,
    ATTRIBUTESET_NAME  VARCHAR(25),
    ATTRIBUTESET_INFO  VARCHAR(250),
    C_DATE             TIMESTAMP NOT NULL,
    LM_DATE            TIMESTAMP NOT NULL
);

CREATE TABLE ATTRIBUTESET_ITEMS (
    ATTRIBUTESET_ITEM_ID  INTEGER NOT NULL,
    ATTRIBUTESET_ID       INTEGER NOT NULL,
    ATTRIBUTE_NAME        VARCHAR(25) NOT NULL,
    ATTRIBUTE_TYPE        VARCHAR(1) NOT NULL,
    TABLE_NAME            VARCHAR(25) NOT NULL,
    COLUMN_NAME           VARCHAR(25) NOT NULL,
    IS_OUTPUT             CHAR(1) NOT NULL,
    ATTRIBUTE_VALUE       VARCHAR(500)
);

CREATE TABLE TREES (
    TREE_ID          INTEGER NOT NULL,
    SOLUTION_ID      SMALLINT,
    ATTRIBUTESET_ID  SMALLINT NOT NULL,
    ROOT_NODE_ID     INTEGER,
    TREE_NAME        VARCHAR(50) NOT NULL,
    TREE_INFO        VARCHAR(200),
    C_DATE           TIMESTAMP NOT NULL,
    LM_DATE          TIMESTAMP NOT NULL
);


CREATE TABLE TREE_NODES (
    NODE_ID            INTEGER NOT NULL,
    PARENT_NODE_ID     INTEGER,
    ATTRIBUTE_ITEM_ID  SMALLINT,
    PARENT_EDGE_VALUE  VARCHAR(200),
    NODE_VALUE         VARCHAR(200)
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

ALTER TABLE ATTRIBUTESETS ADD CONSTRAINT PK_ATTRIBUTESETS PRIMARY KEY (ATTRIBUTESET_ID);

ALTER TABLE ATTRIBUTESET_ITEMS ADD CONSTRAINT PK_ATTRIBUTESET_ITEMS PRIMARY KEY (ATTRIBUTESET_ITEM_ID);

ALTER TABLE TREES ADD CONSTRAINT PK_TREES PRIMARY KEY (TREE_ID);

ALTER TABLE TREE_NODES ADD CONSTRAINT PK_TREE_NODES PRIMARY KEY (NODE_ID);

/******************************************************************************/
/****                             Foreign Keys                             ****/
/******************************************************************************/

ALTER TABLE PROJECTS ADD CONSTRAINT FK_PROJECTS_SOLUTIONS FOREIGN KEY (SOLUTION_ID) REFERENCES SOLUTIONS (SOLUTION_ID) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE TASKS ADD CONSTRAINT FK_TASKS_PLUGINS FOREIGN KEY (PLUGIN_ID) REFERENCES PLUGINS (PLUGIN_ID) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE TASKS ADD CONSTRAINT FK_TASKS_PROJECTS FOREIGN KEY (PROJECT_ID) REFERENCES PROJECTS (PROJECT_ID) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE DATASETS ADD CONSTRAINT FK_DATASETS_SOLUTIONS FOREIGN KEY (SOLUTION_ID) REFERENCES SOLUTIONS (SOLUTION_ID) ON DELETE CASCADE ON UPDATE CASCADE
  USING INDEX FK_DATASETS;

ALTER TABLE DATASET_ITEMS ADD CONSTRAINT FK_DATASET_ITEMS FOREIGN KEY (DATASET_ID) REFERENCES DATASETS (DATASET_ID) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE ATTRIBUTESETS ADD CONSTRAINT FK_ATTRIBUTESETS_SOLUTIONS FOREIGN KEY (SOLUTION_ID) REFERENCES SOLUTIONS (SOLUTION_ID) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE ATTRIBUTESET_ITEMS ADD CONSTRAINT FK_ATTRIBUTESET_ITEMS_ATTRIBUTE FOREIGN KEY (ATTRIBUTESET_ID) REFERENCES ATTRIBUTESETS (ATTRIBUTESET_ID) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE TREES ADD CONSTRAINT FK_TREES_SOLUTIONS FOREIGN KEY (SOLUTION_ID) REFERENCES SOLUTIONS (SOLUTION_ID) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE TREES ADD CONSTRAINT FK_TREES_ATTRIBUTESETS FOREIGN KEY (ATTRIBUTESET_ID) REFERENCES ATTRIBUTESETS (ATTRIBUTESET_ID) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE TREES ADD CONSTRAINT FK_TREES_TREE_NODES FOREIGN KEY (ROOT_NODE_ID) REFERENCES TREE_NODES (NODE_ID) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE TREE_NODES ADD CONSTRAINT FK_TREE_NODES FOREIGN KEY (PARENT_NODE_ID) REFERENCES TREE_NODES (NODE_ID) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE TREE_NODES ADD CONSTRAINT FK_TREE_NODES_ATTR_ITEMS FOREIGN KEY (ATTRIBUTE_ITEM_ID) REFERENCES ATTRIBUTESET_ITEMS (ATTRIBUTESET_ITEM_ID) ON DELETE CASCADE ON UPDATE CASCADE;


/******************************************************************************/
/****                          Unique Constraints                          ****/
/******************************************************************************/

ALTER TABLE SOLUTIONS ADD CONSTRAINT UNQ_SOLUTION_NAME UNIQUE (SOLUTION_NAME);

ALTER TABLE DATASETS ADD CONSTRAINT UNQ_DATASETS_NAME UNIQUE (DATASET_NAME);


/* Check constraints definition */

ALTER TABLE ATTRIBUTESET_ITEMS ADD CONSTRAINT ATTRIBUTE_TYPE check (ATTRIBUTE_TYPE in ('I', 'S', 'E', 'R', 'D'));

ALTER TABLE ATTRIBUTESET_ITEMS ADD CONSTRAINT ATTRIBUTE_IS_OUTPUT check (IS_OUTPUT = 'Y' OR IS_OUTPUT = 'N');


/******************************************************************************/
/****                             Generators                               ****/
/******************************************************************************/

CREATE GENERATOR GEN_SOLUTION_ID;
SET GENERATOR GEN_SOLUTION_ID TO 1;

CREATE GENERATOR GEN_PLUGIN_ID;
SET GENERATOR GEN_PLUGIN_ID TO 1;

CREATE GENERATOR GEN_PROJECT_ID;
SET GENERATOR GEN_PROJECT_ID TO 1;

CREATE GENERATOR GEN_TASK_ID;
SET GENERATOR GEN_TASK_ID TO 1;

CREATE GENERATOR GEN_DATASET_ID;
SET GENERATOR GEN_DATASET_ID TO 1;

CREATE GENERATOR GEN_DATASET_ITEM_ID;
SET GENERATOR GEN_DATASET_ITEM_ID TO 1;

CREATE GENERATOR GEN_ATTRIBUTESET_ID;
SET GENERATOR GEN_ATTRIBUTESET_ID TO 1;

CREATE GENERATOR GEN_ATTRIBUTESET_ITEM_ID;
SET GENERATOR GEN_ATTRIBUTESET_ITEM_ID TO 1;

CREATE GENERATOR GEN_TREES_ID;
SET GENERATOR GEN_TREES_ID TO 1;

CREATE GENERATOR GEN_TREE_NODES_ID;
SET GENERATOR GEN_TREE_NODES_ID TO 1;





