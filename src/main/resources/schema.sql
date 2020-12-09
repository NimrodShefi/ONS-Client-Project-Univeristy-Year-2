DROP SCHEMA IF EXISTS ons;
CREATE SCHEMA IF NOT EXISTS ons;
USE ons;

CREATE TABLE IF NOT EXISTS USER (
  id INT UNSIGNED NOT NULL AUTO_INCREMENT,
  first_name VARCHAR(255) NOT NULL,
  last_name VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT email_unique UNIQUE (email))
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS ROLE(
    id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT role_unique UNIQUE (name))
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS USER_ROLE(
    id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    user_id INT UNSIGNED NOT NULL,
    role_id INT UNSIGNED NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES USER(id),
    FOREIGN KEY (role_id) REFERENCES ROLE(id))
    ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS CHECKLIST_TEMPLATE(
    id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    author_id INT UNSIGNED NOT NULL, -- this will contain the id of the author who create the list
    list_name VARCHAR(255) NOT NULL,
    description LONGTEXT NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY (author_id) REFERENCES USER(id))
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS TOPIC(
    id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    checklist_template_id INT UNSIGNED NOT NULL,
    topic_name VARCHAR(255) NOT NULL,
    description LONGTEXT NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY (checklist_template_id) REFERENCES CHECKLIST_TEMPLATE(id))
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS CHECKLIST_TEMPLATE_ITEM(
    id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    topic_id INT UNSIGNED NOT NULL,
    description LONGTEXT NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY (topic_id) REFERENCES TOPIC(id))
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS PERSONAL_CHECKLIST(
    id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    user_id INT UNSIGNED NOT NULL,
    checklist_template_id INT UNSIGNED NOT NULL,
    date_assigned DATE NOT NULL,
    date_complete DATE,
    PRIMARY KEY(id),
    FOREIGN KEY (user_id) REFERENCES USER(id),
    FOREIGN KEY (checklist_template_id) REFERENCES CHECKLIST_TEMPLATE(id))
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS CHECKLIST_ITEM(
     id INT UNSIGNED NOT NULL AUTO_INCREMENT,
     personal_checklist_id INT UNSIGNED NOT NULL,
     checklist_template_item_id INT UNSIGNED NOT NULL,
     checked BOOLEAN NOT NULL, -- boolean will show as TINYINT(1)
     date_checked DATE,
     PRIMARY KEY(id),
     FOREIGN KEY (personal_checklist_id) REFERENCES PERSONAL_CHECKLIST(id),
     FOREIGN KEY (checklist_template_item_id) REFERENCES CHECKLIST_TEMPLATE_ITEM(id))
     ENGINE = InnoDB;

CREATE USER 'onsUser'@'localhost' IDENTIFIED BY '2Nng2?9P6q47QJLAL=^3';

grant usage on ons to 'superuser'@'localhost';

grant select, insert, update(id,first_name, last_name, email) on ons.USER to 'onsUser'@'localhost';
grant select, insert, update, alter on ons.ROLE to 'onsUser'@'localhost';
grant select, insert, update, alter, delete on ons.USER_ROLE to 'onsUser'@'localhost';
grant select, insert, update, alter on ons.CHECKLIST_TEMPLATE to 'onsUser'@'localhost';
grant select, insert, update, alter on ons.TOPIC to 'onsUser'@'localhost';
grant select, insert, update, alter on ons.CHECKLIST_TEMPLATE_ITEM to 'onsUser'@'localhost';
grant select, insert, update, alter on ons.PERSONAL_CHECKLIST to 'onsUser'@'localhost';
grant select, insert, update, alter on ons.CHECKLIST_ITEM to 'onsUser'@'localhost';
show grants for 'onsUser'@'localhost';
flush privileges;
