-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema studentpairdb
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `studentpairdb` ;

-- -----------------------------------------------------
-- Schema studentpairdb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `studentpairdb` DEFAULT CHARACTER SET utf8 ;
USE `studentpairdb` ;

-- -----------------------------------------------------
-- Table `studentpairdb`.`student`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `studentpairdb`.`student` ;

CREATE TABLE IF NOT EXISTS `studentpairdb`.`student` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(200) NOT NULL,
  `last_name` VARCHAR(200) NOT NULL,
  `other_information` VARCHAR(200) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `studentpairdb`.`group`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `studentpairdb`.`group` ;

CREATE TABLE IF NOT EXISTS `studentpairdb`.`group` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `size` INT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `studentpairdb`.`student_group`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `studentpairdb`.`student_group` ;

CREATE TABLE IF NOT EXISTS `studentpairdb`.`student_group` (
  `student_id` INT NULL,
  `group_id` INT NULL,
  INDEX `fk_group_to_student_group_idx` (`group_id` ASC),
  INDEX `fk_student_to_student_group_idx` (`student_id` ASC),
  CONSTRAINT `fk_group_to_student_group`
    FOREIGN KEY (`group_id`)
    REFERENCES `studentpairdb`.`group` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_student_to_student_group`
    FOREIGN KEY (`student_id`)
    REFERENCES `studentpairdb`.`student` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SET SQL_MODE = '';
GRANT USAGE ON *.* TO studentuser;
 DROP USER studentuser;
SET SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';
CREATE USER 'studentuser' IDENTIFIED BY 'studentuser';

GRANT SELECT, INSERT, TRIGGER, UPDATE, DELETE ON TABLE `studentpairdb`.* TO 'studentuser';

-- -----------------------------------------------------
-- Data for table `studentpairdb`.`student`
-- -----------------------------------------------------
START TRANSACTION;
USE `studentpairdb`;
INSERT INTO `studentpairdb`.`student` (`id`, `first_name`, `last_name`, `other_information`) VALUES (1, 'Gibbon', 'TheMonkey', 'Song Dynasty in China');

COMMIT;


-- -----------------------------------------------------
-- Data for table `studentpairdb`.`group`
-- -----------------------------------------------------
START TRANSACTION;
USE `studentpairdb`;
INSERT INTO `studentpairdb`.`group` (`id`, `size`) VALUES (1, 1);

COMMIT;


-- -----------------------------------------------------
-- Data for table `studentpairdb`.`student_group`
-- -----------------------------------------------------
START TRANSACTION;
USE `studentpairdb`;
INSERT INTO `studentpairdb`.`student_group` (`student_id`, `group_id`) VALUES (1, 1);

COMMIT;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
