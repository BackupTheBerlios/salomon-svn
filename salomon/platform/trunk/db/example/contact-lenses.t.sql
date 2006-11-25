/******************************************************************************/
/***                                 Tables                                 ***/
/******************************************************************************/

CREATE GENERATOR GEN_CONTACT_LENSES_ID;
//

CREATE TABLE CONTACT_LENSES (
    ID                  INTEGER NOT NULL,
    AGE                 VARCHAR(20),
    SPECTACLE_PRESCRIP  VARCHAR(20),
    ASTIGMATISM         VARCHAR(20),
    TEAR_PROD_RATE      VARCHAR(20),
    CONTACT_LENSES      VARCHAR(20)
);
//

/******************************************************************************/
/***                              Primary Keys                              ***/
/******************************************************************************/

ALTER TABLE CONTACT_LENSES ADD PRIMARY KEY (ID);
//

/******************************************************************************/
/***                          Triggers for tables                           ***/
/******************************************************************************/

/* Trigger: CONTACT_LENSES_BI */
CREATE TRIGGER CONTACT_LENSES_BI FOR CONTACT_LENSES
ACTIVE BEFORE INSERT POSITION 0
AS
BEGIN
  IF (NEW.ID IS NULL) THEN
    NEW.ID = GEN_ID(GEN_CONTACT_LENSES_ID,1);
END
//

