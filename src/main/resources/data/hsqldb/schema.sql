DROP TABLE Account IF EXISTS;
 
CREATE TABLE Account (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1, INCREMENT BY 1) NOT NULL,
  firstName VARCHAR(100) NOT NULL,
  secondName VARCHAR(100) NOT NULL,
  accountNumber VARCHAR(100) NOT NULL
  );
ALTER TABLE Account  ADD CONSTRAINT ACCOUNTCONSTRAINT UNIQUE(accountNumber)