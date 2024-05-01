SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE IF EXISTS expense_item;
DROP TABLE IF EXISTS daily_expense;
DROP TABLE IF EXISTS path_bus;
DROP TABLE IF EXISTS expense;
DROP TABLE IF EXISTS route;
DROP TABLE IF EXISTS bus;
DROP TABLE IF EXISTS destination;
DROP TABLE IF EXISTS loan_history;
DROP TABLE IF EXISTS driver;
DROP TABLE IF EXISTS path_expense;
DROP TABLE IF EXISTS expense_type;
DROP TABLE IF EXISTS stock;
DROP TABLE IF EXISTS inventory;
DROP TABLE IF EXISTS path;
DROP TABLE IF EXISTS user;




/* Create Tables */

CREATE TABLE bus
(
	id int NOT NULL AUTO_INCREMENT,
	license_plate varchar(7) NOT NULL,
	primary_driver_id int,
	secondary_driver_id int,
	-- 0: OK,
	-- 1: SERVICING
	status smallint NOT NULL COMMENT '0: OK,
1: SERVICING',
	updated_datetime datetime NOT NULL,
	updated_id int NOT NULL,
	PRIMARY KEY (id),
	UNIQUE (id)
);


CREATE TABLE daily_expense
(
	id int NOT NULL AUTO_INCREMENT,
	bus_id int NOT NULL,
	from_date datetime NOT NULL,
	to_date datetime NOT NULL,
	path varchar(10),
	on_paper_income_leave numeric(18,2),
	on_paper_income_return numeric(18,2),
	in_hand_cash numeric(18,2),
	extra_income numeric(18,2),
	rest_day boolean DEFAULT '0' NOT NULL,
	updated_datetime datetime NOT NULL,
	updated_id int NOT NULL,
	PRIMARY KEY (id),
	UNIQUE (id)
);


CREATE TABLE destination
(
	id int NOT NULL AUTO_INCREMENT,
	name varchar(200) NOT NULL,
	is_order boolean NOT NULL,
	status boolean NOT NULL,
	updated_datetime datetime NOT NULL,
	updated_id int NOT NULL,
	PRIMARY KEY (id),
	UNIQUE (id)
);


CREATE TABLE driver
(
	id int NOT NULL AUTO_INCREMENT,
	name varchar(200) NOT NULL,
	phone varchar(100),
	status boolean NOT NULL,
	updated_datetime datetime NOT NULL,
	updated_id int NOT NULL,
	PRIMARY KEY (id),
	UNIQUE (id)
);


CREATE TABLE expense
(
	id int NOT NULL AUTO_INCREMENT,
	route_id int NOT NULL,
	-- 0: EXPENSE_TYPE,
	-- 1: INVENTORY,
	-- 2: AD_HOC
	type smallint NOT NULL COMMENT '0: EXPENSE_TYPE,
1: INVENTORY,
2: AD_HOC',
	value int NOT NULL,
	updated_datetime datetime NOT NULL,
	updated_id int NOT NULL,
	PRIMARY KEY (id),
	UNIQUE (id)
);


CREATE TABLE expense_item
(
	id int NOT NULL AUTO_INCREMENT,
	daily_expense_id int NOT NULL,
	expense_type_id int,
	inventory_id int,
	amount numeric(18,2) NOT NULL,
	quantity int,
	PRIMARY KEY (id),
	UNIQUE (id)
);


CREATE TABLE expense_type
(
	id int NOT NULL AUTO_INCREMENT,
	name varchar(200) NOT NULL,
	updated_datetime datetime NOT NULL,
	updated_id int NOT NULL,
	PRIMARY KEY (id),
	UNIQUE (id)
);


CREATE TABLE inventory
(
	id int NOT NULL AUTO_INCREMENT,
	item varchar(100) NOT NULL,
	quantity int NOT NULL,
	price numeric(18,2) NOT NULL,
	received_date datetime,
	updated_datetime datetime NOT NULL,
	updated_id int NOT NULL,
	PRIMARY KEY (id),
	UNIQUE (id)
);


CREATE TABLE loan_history
(
	id int NOT NULL AUTO_INCREMENT,
	driver_id int NOT NULL,
	loan_date datetime NOT NULL,
	amount numeric(18,2) NOT NULL,
	-- 0: LOAN,
	-- 1: RETURN
	type smallint NOT NULL COMMENT '0: LOAN,
1: RETURN',
	remark varchar(500),
	updated_datetime datetime NOT NULL,
	updated_id int NOT NULL,
	PRIMARY KEY (id),
	UNIQUE (id)
);


CREATE TABLE path
(
	id int NOT NULL AUTO_INCREMENT,
	path varchar(20) NOT NULL,
	updated_datetime datetime NOT NULL,
	updated_id int NOT NULL,
	PRIMARY KEY (id),
	UNIQUE (id)
);


CREATE TABLE path_bus
(
	path_id int NOT NULL,
	bus_id int NOT NULL
);


CREATE TABLE path_expense
(
	id int NOT NULL AUTO_INCREMENT,
	path_id int NOT NULL,
	expense_id int NOT NULL,
	amount numeric(18,2) NOT NULL,
	updated_datetime datetime NOT NULL,
	updated_id int NOT NULL,
	PRIMARY KEY (id),
	UNIQUE (id)
);


CREATE TABLE route
(
	id int NOT NULL AUTO_INCREMENT,
	bus_id int NOT NULL,
	route_date datetime NOT NULL,
	income numeric(18,2) NOT NULL,
	expense numeric(18,2) NOT NULL,
	updated_datetime datetime NOT NULL,
	updated_id int NOT NULL,
	PRIMARY KEY (id),
	UNIQUE (id)
);


CREATE TABLE stock
(
	id int NOT NULL AUTO_INCREMENT,
	inventory_id int NOT NULL,
	quantity int NOT NULL,
	stock_in boolean NOT NULL,
	transaction_date datetime NOT NULL,
	updated_datetime datetime NOT NULL,
	updated_id int NOT NULL,
	PRIMARY KEY (id),
	UNIQUE (id)
);


CREATE TABLE user
(
	id int NOT NULL AUTO_INCREMENT,
	username varchar(100) NOT NULL,
	password varchar(200) NOT NULL,
	name varchar(200) NOT NULL,
	-- 0 : ACTIVE,
	-- 1 : IN-ACTIVE
	status smallint DEFAULT 0 NOT NULL COMMENT '0 : ACTIVE,
1 : IN-ACTIVE',
	role varchar(15) NOT NULL,
	last_logged_in datetime,
	PRIMARY KEY (id),
	UNIQUE (id),
	UNIQUE (username)
);



/* Create Foreign Keys */

ALTER TABLE daily_expense
	ADD CONSTRAINT frk_bus_daily_expense FOREIGN KEY (bus_id)
	REFERENCES bus (id)
	ON UPDATE NO ACTION
	ON DELETE NO ACTION
;


ALTER TABLE path_bus
	ADD CONSTRAINT frk_bus_bus_path FOREIGN KEY (bus_id)
	REFERENCES bus (id)
	ON UPDATE NO ACTION
	ON DELETE NO ACTION
;


ALTER TABLE route
	ADD CONSTRAINT frk_bus_route FOREIGN KEY (bus_id)
	REFERENCES bus (id)
	ON UPDATE NO ACTION
	ON DELETE NO ACTION
;


ALTER TABLE expense_item
	ADD FOREIGN KEY (daily_expense_id)
	REFERENCES daily_expense (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE bus
	ADD CONSTRAINT frk_driver_primary_driver FOREIGN KEY (primary_driver_id)
	REFERENCES driver (id)
	ON UPDATE NO ACTION
	ON DELETE NO ACTION
;


ALTER TABLE bus
	ADD CONSTRAINT frk_secondary_driver_bus FOREIGN KEY (secondary_driver_id)
	REFERENCES driver (id)
	ON UPDATE NO ACTION
	ON DELETE NO ACTION
;


ALTER TABLE loan_history
	ADD CONSTRAINT frk_driver_loan_history FOREIGN KEY (driver_id)
	REFERENCES driver (id)
	ON UPDATE NO ACTION
	ON DELETE NO ACTION
;


ALTER TABLE expense_item
	ADD CONSTRAINT frk_expense_type_expense_item FOREIGN KEY (expense_type_id)
	REFERENCES expense_type (id)
	ON UPDATE NO ACTION
	ON DELETE NO ACTION
;


ALTER TABLE path_expense
	ADD CONSTRAINT frk_expense_type_path_expense FOREIGN KEY (expense_id)
	REFERENCES expense_type (id)
	ON UPDATE NO ACTION
	ON DELETE NO ACTION
;


ALTER TABLE expense_item
	ADD CONSTRAINT frk_inventory_expense_item FOREIGN KEY (inventory_id)
	REFERENCES inventory (id)
	ON UPDATE NO ACTION
	ON DELETE NO ACTION
;


ALTER TABLE stock
	ADD CONSTRAINT frk_inventory_stock FOREIGN KEY (inventory_id)
	REFERENCES inventory (id)
	ON UPDATE NO ACTION
	ON DELETE NO ACTION
;


ALTER TABLE path_bus
	ADD CONSTRAINT frk_path_path_bus FOREIGN KEY (path_id)
	REFERENCES path (id)
	ON UPDATE NO ACTION
	ON DELETE NO ACTION
;


ALTER TABLE path_expense
	ADD CONSTRAINT frk_path_path_expense FOREIGN KEY (path_id)
	REFERENCES path (id)
	ON UPDATE NO ACTION
	ON DELETE NO ACTION
;


ALTER TABLE expense
	ADD CONSTRAINT frk_route_expense FOREIGN KEY (route_id)
	REFERENCES route (id)
	ON UPDATE NO ACTION
	ON DELETE NO ACTION
;



