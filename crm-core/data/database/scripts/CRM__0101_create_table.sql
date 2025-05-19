SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE IF EXISTS leads;
DROP TABLE IF EXISTS company;
DROP TABLE IF EXISTS user;




/* Create Tables */

CREATE TABLE company
(
	id int NOT NULL AUTO_INCREMENT,
	name varchar(300) NOT NULL,
	updated_datetime datetime NOT NULL,
	updated_id int NOT NULL,
	PRIMARY KEY (id),
	UNIQUE (id)
);


CREATE TABLE leads
(
	id int NOT NULL AUTO_INCREMENT,
	company_id int NOT NULL,
	contact_name varchar(200) NOT NULL,
	phone varchar(20),
	email varchar(30),
	facebook varchar(100),
	status smallint NOT NULL,
	remark text,
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

ALTER TABLE leads
	ADD CONSTRAINT frk_company_lead FOREIGN KEY (company_id)
	REFERENCES company (id)
	ON UPDATE NO ACTION
	ON DELETE NO ACTION
;



