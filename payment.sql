-- MySQL dump 10.13  Distrib 5.7.16, for Linux (x86_64)
--
-- Host: localhost    Database: payment_db
-- ------------------------------------------------------
-- Server version	5.7.16-0ubuntu0.16.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `payment_db`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `payment_db` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `payment_db`;

--
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `account` (
  `id` varchar(255) NOT NULL,
  `account_number` varchar(255) NOT NULL,
  `account_name` varchar(255) NOT NULL COMMENT '		',
  `account_currency` varchar(45) NOT NULL,
  `account_status` int(11) NOT NULL,
  `customer_name` varchar(255) NOT NULL,
  `iban_id` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`,`account_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `iban`
--

DROP TABLE IF EXISTS `iban`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `iban` (
  `iban_id` varchar(255) NOT NULL,
  `country_code` varchar(45) DEFAULT NULL,
  `iban_value` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`iban_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `iban`
--

LOCK TABLES `iban` WRITE;
/*!40000 ALTER TABLE `iban` DISABLE KEYS */;
/*!40000 ALTER TABLE `iban` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `id_table`
--

DROP TABLE IF EXISTS `id_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `id_table` (
  `table_name` varchar(255) NOT NULL,
  `id` int(11) NOT NULL,
  PRIMARY KEY (`table_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `id_table`
--

LOCK TABLES `id_table` WRITE;
/*!40000 ALTER TABLE `id_table` DISABLE KEYS */;
/*!40000 ALTER TABLE `id_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment`
--

DROP TABLE IF EXISTS `payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `payment` (
  `order_account_number` varchar(255) NOT NULL,
  `benficiary_account_iban` varchar(255) NOT NULL,
  `benficiary_account_name` varchar(255) NOT NULL,
  `payment_amount` decimal(10,0) NOT NULL,
  `transfer_currency` varchar(45) NOT NULL,
  `payment_date` date NOT NULL,
  `payment_purpose` varchar(45) NOT NULL,
  PRIMARY KEY (`order_account_number`),
  KEY `fk_payment_1_idx` (`payment_purpose`),
  CONSTRAINT `fk_payment_1` FOREIGN KEY (`payment_purpose`) REFERENCES `payment_purpose` (`short_code`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment`
--

LOCK TABLES `payment` WRITE;
/*!40000 ALTER TABLE `payment` DISABLE KEYS */;
/*!40000 ALTER TABLE `payment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment_purpose`
--

DROP TABLE IF EXISTS `payment_purpose`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `payment_purpose` (
  `short_code` varchar(45) NOT NULL,
  `description` varchar(255) NOT NULL,
  `id` mediumtext,
  PRIMARY KEY (`short_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment_purpose`
--

LOCK TABLES `payment_purpose` WRITE;
/*!40000 ALTER TABLE `payment_purpose` DISABLE KEYS */;
/*!40000 ALTER TABLE `payment_purpose` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-11-27 16:00:21
