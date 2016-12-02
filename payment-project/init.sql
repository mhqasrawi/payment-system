DROP TABLE IF EXISTS account;
CREATE TABLE IF NOT EXISTS account (
  `id`       long     NOT NULL,
  `account_number`   varchar(255)     NOT NULL,
  `account_name`     VARCHAR(255) NOT NULL
  COMMENT '		',
  `account_currency` VARCHAR(45)  NOT NULL,
  `account_status`   INT(11)      NOT NULL,
  `customer_name`    VARCHAR(255) NOT NULL,
  `iban_id`          long,
  PRIMARY KEY (`id`, `account_number`)
)
  ENGINE = InnoDB;
DROP TABLE IF EXISTS iban;
CREATE TABLE IF NOT EXISTS iban (
  `iban_id`      long   NOT NULL,
  `country_code` VARCHAR(45) NULL,
  `iban_value`   VARCHAR(45) NULL,
  PRIMARY KEY (`iban_id`)
)
  ENGINE = InnoDB;
DROP TABLE IF EXISTS id_table;
CREATE TABLE IF NOT EXISTS id_table (
  `table_name` VARCHAR(255) NOT NULL,
  `id`         LONG    NOT NULL,
  PRIMARY KEY (`table_name`)
)
  ENGINE = InnoDB;
DROP TABLE IF EXISTS payment_purpose;
CREATE TABLE IF NOT EXISTS payment_purpose (
  `short_code`  VARCHAR(45)  NOT NULL,
  `description` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`short_code`)
)
  ENGINE = InnoDB;
DROP TABLE IF EXISTS payment;
CREATE TABLE IF NOT EXISTS payment (
  `order_account_number`    LONG(100)     NOT NULL,
  `benficiary_account_iban` MEDIUMTEXT   NOT NULL,
  `benficiary_account_name` VARCHAR(255) NOT NULL,
  `payment_amount`          DECIMAL      NOT NULL,
  `transfer_currency`       VARCHAR(45)  NOT NULL,
  `payment_date`            DATE         NOT NULL,
  `payment_purpose`         VARCHAR(45)  NOT NULL,
  PRIMARY KEY (`order_account_number`),
  CONSTRAINT `fk_payment_1`
  FOREIGN KEY (`payment_purpose`)
  REFERENCES `payment_purpose` (`short_code`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
  ENGINE = InnoDB;
