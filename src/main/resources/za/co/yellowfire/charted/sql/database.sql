/*
DROP TABLE budget_category CASCADE;

CREATE TABLE budget_category
(
  name character varying(64) NOT NULL,
  budget_amount numeric(38,0),
  color character varying(32),
  direction character varying(255),
  matches character varying(2048),
  budget_section character varying(64),
  CONSTRAINT budget_category_pkey PRIMARY KEY (name ),
  CONSTRAINT fk_budget_category_budget_section FOREIGN KEY (budget_section)
      REFERENCES budget_section (name) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);

ALTER TABLE budget_category
  OWNER TO uncharted;
*/

update budget_category set matches = 'ZA_GP_CH' where name = 'Food (In)';
update budget_category set matches = 'Carlton Hair' where name = 'Haircut';
update budget_category set matches = 'Exclusive Bo' where name = 'Books';
update budget_category set matches = 'SHANGRI-LA' where name = 'School Fees';
update budget_category set matches = 'MIMMOS|THE BREAD|PRIMI' where name = 'Food (Out)';


update budget_category set matches = 'GAUTRAIN' where name = 'Train';
update budget_category set matches = 'Salary' where name = 'Salary';
update budget_category set matches = 'Crystal Maintenance' where name = 'Maintenance';
update budget_category set matches = 'MTN' where name = 'MTN';
update budget_category set matches = 'MWeb' where name = 'MWeb';
update budget_category set matches = 'CellC' where name = 'CellC';
update budget_category set matches = 'Webonline' where name = 'Webonline';
update budget_category set matches = 'Outsurance' where name = 'Outsurance';
