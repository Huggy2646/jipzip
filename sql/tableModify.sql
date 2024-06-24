use jipzip;

-- houseinfo 테이블에 매물 상태 컬럼 추가
ALTER TABLE houseinfo
ADD COLUMN stat varchar(20) NOT NULL default "매매중";

-- 회원 정보 테이블 수정
SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

USE `jipzip` ;

DROP TABLE IF EXISTS `jipzip`.`Users` ;

CREATE TABLE IF NOT EXISTS `jipzip` .`Users` (
  `id` VARCHAR(45) NOT NULL,
  `pw` VARCHAR(30) NOT NULL,
  `pkey` VARCHAR(255) NOT NULL,
  `joindate` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `jipzip` .`Userinfo`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `jipzip`.`Userinfo` ;

CREATE TABLE IF NOT EXISTS `jipzip`.`Userinfo` (
  `id` VARCHAR(45) NOT NULL,
  `role` ENUM('일반회원', '관리자', '휴먼회원') NOT NULL DEFAULT '일반회원',
  `name` VARCHAR(45) NULL,
  `adress` TEXT NULL,
  `email` VARCHAR(45) NULL,
  `mobile_number` VARCHAR(45) NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `Userinfo_Users_id_fk`
    FOREIGN KEY (`id`)
    REFERENCES `jipzip`.`Users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
) ENGINE = InnoDB;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- 관심 지역 관리 테이블 추가
SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

CREATE SCHEMA IF NOT EXISTS `jipzip` DEFAULT CHARACTER SET utf8 ;
USE `jipzip` ;

-- -----------------------------------------------------
-- Table `jipzip`.`interestedarea`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `jipzip`.`interestedarea` ;

CREATE TABLE IF NOT EXISTS `jipzip`.`interestedarea` (
  `no` INT NOT NULL AUTO_INCREMENT,
  `id` VARCHAR(45) NOT NULL,
  `dongCode` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`no`),
  CONSTRAINT `interestedarea_Userinfo_id_fk`
    FOREIGN KEY (`id`)
    REFERENCES `jipzip`.`Users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `interestedarea_dongcode_dongCode_fk`
    FOREIGN KEY (`dongCode`)
    REFERENCES `jipzip`.`dongcode` (`dongCode`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
) ENGINE = InnoDB;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;



