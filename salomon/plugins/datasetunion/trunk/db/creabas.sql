CREATE TABLE PERSONS (
    ID INTEGER NOT NULL,
    FIRST_NAME VARCHAR(20) NOT NULL,
    LAST_NAME VARCHAR(20) NOT NULL,
    EMAIL VARCHAR(20));

alter table PERSONS
add constraint PK_PERSONS
primary key (ID);

CREATE GENERATOR GEN_PERSONS_ID;

-- trigger before insert

SET TERM ^ ;


CREATE TRIGGER PERSONS_BI FOR PERSONS
ACTIVE BEFORE INSERT POSITION 0
AS
BEGIN
  IF (NEW.ID IS NULL) THEN
    NEW.ID = GEN_ID(gen_persons_id,1);
END
^

SET TERM ; ^
