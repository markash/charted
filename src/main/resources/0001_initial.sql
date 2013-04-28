CREATE SEQUENCE seq_identity
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1000
  CACHE 1;

create table credential
(
  user_name character varying(32) NOT NULL,
  user_password character varying(255),
  constraint pk_credential primary key (user_name)
) without oids ;

create table account
(
  account_nbr character varying(64) NOT NULL,
  account_key character varying(64),
  bank_id character varying(64),
  branch_id character varying(64),
  account_type character varying(16),
  create_ts timestamp NULL default now(),
  update_ts timestamp NULL,
  CONSTRAINT pk_account PRIMARY KEY (account_nbr)
) without oids;

create table objective
(
  objective_id bigint NOT NULL default nextval('seq_identity'),
  objective_name character varying(64) NOT NULL,
  objective_expression character varying (256) NOT NULL,
  create_ts timestamp NULL default now(),
  update_ts timestamp NULL,
  constraint pk_objective primary key (objective_id),
  constraint uq_objective_name unique (objective_name)
) with (oids=false);

create table budget
(
  budget_id bigint not null default nextval('seq_identity'),
  start_date date NOT NULL,
  end_date date NOT NULL,
  objective_id bigint null,
  create_ts timestamp NULL default now(),
  update_ts timestamp NULL,
  constraint pk_budget primary key (budget_id),
  constraint uq_budget_date unique (start_date, end_date),
  constraint fk_budget_objective foreign key (objective_id) references objective(objective_id) on update no action on delete no action
) without oids;

create table budget_section
(
  section_id bigint NOT NULL default nextval('seq_identity'),
  budget_id bigint NOT NULL,
  name character varying(64),
  color character varying(32),
  direction character varying(16),
  create_ts timestamp NULL default now(),
  update_ts timestamp NULL,
  CONSTRAINT pk_budget_section PRIMARY KEY (section_id),
  constraint uq_budget_section_name unique (name),
  CONSTRAINT fk_budget_section_budget FOREIGN KEY (budget_id) REFERENCES budget(budget_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION
) without oids;

create table budget_category
(
  category_id bigint NOT NULL default nextval('seq_identity'),
  section_id bigint NOT NULL,
  name character varying(64),
  color character varying(32),
  direction character varying(16),
  matches character varying(2048),
  budget_amount numeric(38,2),
  create_ts timestamp NULL default now(),
  update_ts timestamp NULL,
  constraint pk_budget_category PRIMARY KEY (category_id),
  constraint uq_budget_category_name unique (name),
  constraint fk_budget_category_section foreign key (section_id) references budget_section(section_id) match full ON UPDATE NO ACTION ON DELETE NO ACTION
) without oids;

create table transaction
(
  transaction_id character varying(255) NOT NULL,
  account_nbr character varying(255),
  amount numeric(38,2),
  date_available date,
  date_initiated date,
  date_posted date,
  memo character varying(255),
  create_ts timestamp NULL default now(),
  update_ts timestamp NULL,
  CONSTRAINT pk_transaction PRIMARY KEY (transaction_id),
  CONSTRAINT fk_transaction_account_nbr FOREIGN KEY (account_nbr) REFERENCES account (account_nbr) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION
) without oids;

create table budget_allocation
(
  allocation_id bigint not null default nextval('seq_identity'),
  transaction_id character varying(255) NOT NULL,
  category_id bigint not null,
  amount numeric(38, 2),
  create_ts timestamp NULL default now(),
  update_ts timestamp NULL,
  constraint pk_budget_allocation primary key (allocation_id),
  constraint fk_budget_allocation_transaction foreign key (transaction_id) references transaction(transaction_id) MATCH SIMPLE on update  no action on delete no action,
  constraint fk_budget_allocation_category foreign key (category_id) references budget_category(category_id) MATCH SIMPLE on update  no action on delete no action
) without oids;

alter table credential owner to uncharted;
alter table account owner to uncharted;
alter table seq_identity owner to uncharted;
alter table budget owner to uncharted;
alter table budget_section owner to uncharted;
alter table budget_category owner to uncharted;
alter table transaction owner to uncharted;
alter table budget_allocation owner to uncharted;
