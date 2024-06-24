-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema QuizMaster
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema QuizMaster
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `QuizMaster` DEFAULT CHARACTER SET utf8 ;
USE `QuizMaster` ;

-- -----------------------------------------------------
-- Table `QuizMaster`.`Role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `QuizMaster`.`Role` (
  `roleId` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(125) NOT NULL,
  PRIMARY KEY (`roleId`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `QuizMaster`.`User`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `QuizMaster`.`User` (
  `userId` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `firstName` VARCHAR(45) NOT NULL,
  `infix` VARCHAR(45) NULL,
  `lastName` VARCHAR(125) NOT NULL,
  `roleId` INT NOT NULL,
  PRIMARY KEY (`userId`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE,
  INDEX `!VERZINZELF!_idx` (`roleId` ASC) VISIBLE,
  CONSTRAINT `!VERZINZELF!`
    FOREIGN KEY (`roleId`)
    REFERENCES `QuizMaster`.`Role` (`roleId`)
    ON DELETE RESTRICT
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `QuizMaster`.`Difficulty`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `QuizMaster`.`Difficulty` (
  `difficultyId` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`difficultyId`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `QuizMaster`.`Course`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `QuizMaster`.`Course` (
  `courseId` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(125) NOT NULL,
  `coordinatorId` INT NOT NULL,
  `difficultyId` INT NOT NULL,
  PRIMARY KEY (`courseId`),
  INDEX `!VERZINZELF!1_idx` (`coordinatorId` ASC) VISIBLE,
  INDEX `!VERZINZELF!4_idx` (`difficultyId` ASC) VISIBLE,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE,
  CONSTRAINT `!VERZINZELF!1`
    FOREIGN KEY (`coordinatorId`)
    REFERENCES `QuizMaster`.`User` (`userId`)
    ON DELETE RESTRICT
    ON UPDATE NO ACTION,
  CONSTRAINT `!VERZINZELF!4`
    FOREIGN KEY (`difficultyId`)
    REFERENCES `QuizMaster`.`Difficulty` (`difficultyId`)
    ON DELETE RESTRICT
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `QuizMaster`.`Quiz`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `QuizMaster`.`Quiz` (
  `quizId` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(125) NOT NULL,
  `passMark` INT NOT NULL,
  `quizPoints` INT NOT NULL,
  `difficultyId` INT NOT NULL,
  `courseId` INT NOT NULL,
  PRIMARY KEY (`quizId`),
  INDEX `!VERZINZELF!5_idx` (`difficultyId` ASC) VISIBLE,
  INDEX `!VERZINZELF!10_idx` (`courseId` ASC) VISIBLE,
  CONSTRAINT `!VERZINZELF!5`
    FOREIGN KEY (`difficultyId`)
    REFERENCES `QuizMaster`.`Difficulty` (`difficultyId`)
    ON DELETE RESTRICT
    ON UPDATE NO ACTION,
  CONSTRAINT `!VERZINZELF!10`
    FOREIGN KEY (`courseId`)
    REFERENCES `QuizMaster`.`Course` (`courseId`)
    ON DELETE RESTRICT
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `QuizMaster`.`Question`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `QuizMaster`.`Question` (
  `questionId` INT NOT NULL AUTO_INCREMENT,
  `questionDescription` VARCHAR(999) NOT NULL,
  `answerA` VARCHAR(999) NOT NULL,
  `answerB` VARCHAR(999) NOT NULL,
  `answerC` VARCHAR(999) NOT NULL,
  `answerD` VARCHAR(999) NOT NULL,
  `Quiz_quizId` INT NOT NULL,
  PRIMARY KEY (`questionId`),
  INDEX `verzinzelf1_idx` (`Quiz_quizId` ASC) VISIBLE,
  CONSTRAINT `verzinzelf1`
    FOREIGN KEY (`Quiz_quizId`)
    REFERENCES `QuizMaster`.`Quiz` (`quizId`)
    ON DELETE RESTRICT
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `QuizMaster`.`Result`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `QuizMaster`.`Result` (
  `resultId` INT NOT NULL AUTO_INCREMENT,
  `date` DATETIME(1) NOT NULL,
  `score` INT NOT NULL,
  `userId` INT NOT NULL,
  `quizId` INT NOT NULL,
  PRIMARY KEY (`resultId`),
  INDEX `!VERZINZELF!8_idx` (`userId` ASC) VISIBLE,
  INDEX `!VERZINZELF!9_idx` (`quizId` ASC) VISIBLE,
  CONSTRAINT `!VERZINZELF!8`
    FOREIGN KEY (`userId`)
    REFERENCES `QuizMaster`.`User` (`userId`)
    ON DELETE RESTRICT
    ON UPDATE NO ACTION,
  CONSTRAINT `!VERZINZELF!9`
    FOREIGN KEY (`quizId`)
    REFERENCES `QuizMaster`.`Quiz` (`quizId`)
    ON DELETE RESTRICT
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `QuizMaster`.`StudentCourse`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `QuizMaster`.`StudentCourse` (
  `studentCourseId` INT NOT NULL AUTO_INCREMENT,
  `studentId` INT NOT NULL,
  `courseId` INT NOT NULL,
  `enrollDate` DATETIME(1) NOT NULL,
  `dropoutDate` DATE NULL,
  PRIMARY KEY (`studentCourseId`),
  INDEX `!VERZINZELF!3_idx` (`courseId` ASC) VISIBLE,
  INDEX `!VERZINZELF!2_idx` (`studentId` ASC) VISIBLE,
  CONSTRAINT `!VERZINZELF!2`
    FOREIGN KEY (`studentId`)
    REFERENCES `QuizMaster`.`User` (`userId`)
    ON DELETE RESTRICT
    ON UPDATE NO ACTION,
  CONSTRAINT `!VERZINZELF!3`
    FOREIGN KEY (`courseId`)
    REFERENCES `QuizMaster`.`Course` (`courseId`)
    ON DELETE RESTRICT
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

insert into
    `QuizMaster`.`Role`
values (1, 'Student'),
    (2, 'Docent'),
    (3, 'Coordinator'),
    (4, 'Administrator'),
    (5, 'Functioneel beheerder');

insert into
    `QuizMaster`.`Difficulty`
values (1, 'Beginner'),
    (2, 'Medium'),
    (3, 'Gevorderd');

CREATE USER 'userQuizMaster' @'localhost' IDENTIFIED BY 'pwQuizMaster';

GRANT ALL privileges ON quizmaster.* TO 'userQuizMaster' @'localhost';

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;




