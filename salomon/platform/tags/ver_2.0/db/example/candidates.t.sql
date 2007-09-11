/******************************************************************************/
/***                                 Tables                                 ***/
/******************************************************************************/

CREATE GENERATOR GEN_CANDIDATES_ID;
//

CREATE TABLE CANDIDATES (
    ID            INTEGER NOT NULL,
    AGE           INTEGER,
    SEX           CHAR(1),
    EDUCATION     INTEGER,
    LANGUAGES     INTEGER,
    EXPERIENCE    INTEGER,
    PRESENTATION  INTEGER,
    ASSIGNED      VARCHAR(1)
);
//



/******************************************************************************/
/***                              Primary Keys                              ***/
/******************************************************************************/

ALTER TABLE CANDIDATES ADD CONSTRAINT PK_CANDIDATES PRIMARY KEY (ID);
//

/******************************************************************************/
/***                          Triggers for tables                           ***/
/******************************************************************************/

/* Trigger: CANDIDATES_BI */
CREATE TRIGGER CANDIDATES_BI FOR CANDIDATES
ACTIVE BEFORE INSERT POSITION 0
AS
begin
   IF (NEW.ID IS NULL) THEN
    NEW.ID = GEN_ID(gen_candidates_id,1);
end

