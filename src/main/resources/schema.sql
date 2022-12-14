DROP SCHEMA IF EXISTS ons;
CREATE SCHEMA IF NOT EXISTS ons;
USE ons;

CREATE TABLE IF NOT EXISTS user (
  id INT UNSIGNED NOT NULL AUTO_INCREMENT,
  first_name VARCHAR(255) NOT NULL,
  last_name VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  failed_attempt tinyint NOT NULL DEFAULT 0,
  account_non_locked tinyint NOT NULL default 1,
  lock_time datetime,
  enabled tinyint NOT NULL default 1,
  PRIMARY KEY (id),
  CONSTRAINT email_unique UNIQUE (email))
ENGINE = InnoDB;
-- ENCRYPTED = YES;

CREATE TABLE IF NOT EXISTS role(
    id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT role_unique UNIQUE (name))
ENGINE = InnoDB;
-- ENCRYPTED = YES;

CREATE TABLE IF NOT EXISTS user_role(
    id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    user_id INT UNSIGNED NOT NULL,
    role_id INT UNSIGNED NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (role_id) REFERENCES role(id))
ENGINE = InnoDB;
-- ENCRYPTED = YES;

CREATE TABLE IF NOT EXISTS checklist_template(
    id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    author_id INT UNSIGNED NOT NULL, -- this will contain the id of the author who create the list
    list_name VARCHAR(255) NOT NULL,
    description LONGTEXT NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY (author_id) REFERENCES user(id))
ENGINE = InnoDB;
-- ENCRYPTED = YES;

CREATE TABLE IF NOT EXISTS topic(
    id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    checklist_template_id INT UNSIGNED NOT NULL,
    topic_name VARCHAR(255) NOT NULL,
    description LONGTEXT NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY (checklist_template_id) REFERENCES checklist_template(id))
ENGINE = InnoDB;
-- ENCRYPTED = YES;

CREATE TABLE IF NOT EXISTS checklist_template_item(
    id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    topic_id INT UNSIGNED NOT NULL,
    description LONGTEXT NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY (topic_id) REFERENCES topic(id))
ENGINE = InnoDB;
-- ENCRYPTED = YES;

CREATE TABLE IF NOT EXISTS personal_checklist(
    id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    user_id INT UNSIGNED NOT NULL,
    checklist_template_id INT UNSIGNED NOT NULL,
    date_assigned DATE NOT NULL,
    date_complete DATE,
    PRIMARY KEY(id),
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (checklist_template_id) REFERENCES checklist_template(id))
ENGINE = InnoDB;
-- ENCRYPTED = YES;

CREATE TABLE IF NOT EXISTS checklist_item(
     id INT UNSIGNED NOT NULL AUTO_INCREMENT,
     personal_checklist_id INT UNSIGNED NOT NULL,
     checklist_template_item_id INT UNSIGNED NOT NULL,
     checked BOOLEAN NOT NULL, -- boolean will show as TINYINT(1)
     date_checked DATE,
     PRIMARY KEY(id),
     FOREIGN KEY (personal_checklist_id) REFERENCES personal_checklist(id),
     FOREIGN KEY (checklist_template_item_id) REFERENCES checklist_template_item(id))
ENGINE = InnoDB;
-- ENCRYPTED = YES;
     
     
-- validate email procedure
DROP PROCEDURE IF EXISTS validate_email;
DELIMITER $$
CREATE PROCEDURE validate_email(
		IN email VARCHAR(128)
)
DETERMINISTIC
NO SQL
BEGIN
		IF NOT (SELECT email REGEXP '^[^\@<>+*/=!"??$^&%()`??\\|;:?,#~]+@cardiff.ac.uk') THEN
				SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'WRONG email format';
		END IF;
END $$
DELIMITER ;

-- validate first and last name procedure
DROP PROCEDURE IF EXISTS validate_user_first_name;
DELIMITER $$
CREATE PROCEDURE validate_user_first_name(
		IN first_name VARCHAR(128)
)
DETERMINISTIC
NO SQL
BEGIN
		IF (SELECT first_name REGEXP '[0-9\@<>+*/=!"??$123^&()`??\\|;:?,#~]') THEN
				SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Names can only contains letters';
		END IF;
END $$
DELIMITER ;

DROP PROCEDURE IF EXISTS validate_user_last_name;
DELIMITER $$
CREATE PROCEDURE validate_user_last_name(
		IN last_name VARCHAR(128)
)
DETERMINISTIC
NO SQL
BEGIN
		IF (SELECT last_name REGEXP '[0-9\@<>+*/=!"??$123^&()`??\\|;:?,#~]') THEN
				SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Names can only contains letters';
		END IF;
END $$
DELIMITER ;


-- validate email trigger
DELIMITER $$
CREATE TRIGGER `user_validate_insert`
BEFORE INSERT ON `user` FOR EACH ROW
BEGIN
		CALL validate_email(NEW.email);
		CALL validate_user_first_name(NEW.first_name);
		CALL validate_user_last_name(NEW.last_name);
END$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER user_validate_update
BEFORE UPDATE ON `user` FOR EACH ROW
BEGIN
		CALL validate_email(NEW.email);
		CALL validate_user_first_name(NEW.first_name);
		CALL validate_user_last_name(NEW.last_name);
END $$
DELIMITER ;


-- Stored procedure to get a count of the checklist items that have been checked
DROP PROCEDURE IF EXISTS getCheckedItemsCountForPersonalChecklist;

DELIMITER //

USE ons //
CREATE DEFINER = `onsUser`@`localhost`
    PROCEDURE getCheckedItemsCountForPersonalChecklist(IN personalChecklistId int, OUT countOut int)
    SQL SECURITY INVOKER
BEGIN
    SELECT COUNT(checked) INTO countOut
    FROM checklist_item
    WHERE checklist_item.personal_checklist_id = personalChecklistId
      AND checked = true;
END//

DELIMITER ;


 -- CREATING DB USER 
CREATE USER IF NOT EXISTS 'onsUser'@'localhost' IDENTIFIED BY '2Nng2?9P6q47QJLAL=^3';

grant usage on ons.* to 'onsUser'@'localhost';

grant select, insert, update(id,first_name, last_name, password, email, failed_attempt, account_non_locked, lock_time, enabled) on ons.user to 'onsUser'@'localhost';
grant select, insert, update, alter on ons.role to 'onsUser'@'localhost';
grant select, insert, update, alter, delete on ons.user_role to 'onsUser'@'localhost';
grant select, insert, update, alter on ons.checklist_template to 'onsUser'@'localhost';
grant select, insert, update, alter on ons.topic to 'onsUser'@'localhost';
grant select, insert, update, alter on ons.checklist_template_item to 'onsUser'@'localhost';
grant select, insert, update, alter on ons.personal_checklist to 'onsUser'@'localhost';
grant select, insert, update, alter on ons.checklist_item to 'onsUser'@'localhost';
grant execute on procedure ons.getCheckedItemsCountForPersonalChecklist to 'onsUser'@'localhost';
show grants for 'onsUser'@'localhost';
flush privileges;
