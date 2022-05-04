-- MySQL dump 10.13  Distrib 8.0.29, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: backend
-- ------------------------------------------------------
-- Server version	5.5.5-10.6.5-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `application`
--

DROP TABLE IF EXISTS `application`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `application` (
  `id` varchar(100) NOT NULL,
  `description` text DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `application`
--

INSERT INTO `application` (`id`, `description`, `created_at`, `updated_at`) VALUES ('KDC','classify various kidney diseases','2022-04-27 15:56:15',NULL),('PFS','diagnose disease using picture fuzzy relations','2022-04-27 15:56:14',NULL);

--
-- Table structure for table `diagnose`
--

DROP TABLE IF EXISTS `diagnose`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `diagnose` (
  `name` varchar(50) NOT NULL,
  `description` text DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `diagnose`
--

INSERT INTO `diagnose` (`name`, `description`, `created_at`, `updated_at`) VALUES ('CHEST_PROBLEM',NULL,'2022-02-26 18:04:40','2022-02-26 18:04:46'),('FEVER',NULL,'2022-02-26 18:04:38','2022-02-26 18:04:44'),('MALARIA',NULL,'2022-02-26 18:04:39','2022-02-26 18:04:44'),('STOMACH',NULL,'2022-02-26 18:04:40','2022-02-26 18:04:45'),('TYPHOID',NULL,'2022-02-26 18:04:39','2022-02-26 18:04:45');

--
-- Table structure for table `examination`
--

DROP TABLE IF EXISTS `examination`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `examination` (
  `id` varchar(50) NOT NULL,
  `application_id` varchar(50) NOT NULL,
  `user_id` varchar(50) DEFAULT NULL,
  `patient_id` varchar(50) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `examination`
--

INSERT INTO `examination` (`id`, `application_id`, `user_id`, `patient_id`, `created_at`) VALUES ('01G1QH3P4FXEK2H8BZ87FKFME5','PFS','02FCZG2SXNZYGQXMPPBB8FV3ZV','0123','2022-04-28 14:28:02'),('01G1QJE2NNCBBAPHCRGR1TJ4F9','PFS','02FCZG2SXNZYGQXMPPBB8FV3ZV','0123','2022-04-28 14:51:11'),('01G1QJGT80MT93V8VNKD6QW6MA','PFS','02FCZG2SXNZYGQXMPPBB8FV3ZV','0123','2022-04-28 14:52:40');

--
-- Table structure for table `hedge_algebra_config`
--

DROP TABLE IF EXISTS `hedge_algebra_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hedge_algebra_config` (
  `application_id` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL,
  `fm` double DEFAULT NULL,
  `type` varchar(50) DEFAULT NULL,
  `linguistic_order` int(11) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`application_id`,`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hedge_algebra_config`
--

INSERT INTO `hedge_algebra_config` (`application_id`, `name`, `fm`, `type`, `linguistic_order`, `created_at`, `updated_at`) VALUES ('KDC','HIGH',0.6,'GENERATOR',1,'2022-04-28 16:28:08','2022-04-29 15:05:27'),('KDC','LITTLE',0.2,'HEDGE',-2,'2022-04-28 16:33:55',NULL),('KDC','LOW',0.4,'GENERATOR',-1,'2022-04-28 16:27:44','2022-04-29 15:05:27'),('KDC','MEDIUM',0.4,'GENERATOR',0,'2022-04-28 16:28:23','2022-04-29 15:05:27'),('KDC','MORE',0.25,'HEDGE',1,'2022-04-28 16:28:35',NULL),('KDC','POSSIBLE',0.2,'HEDGE',-1,'2022-04-28 16:33:56',NULL),('KDC','VERY',0.35,'HEDGE',2,'2022-04-28 16:33:54',NULL),('PFS','COMPLETELY',1,'GENERATOR',99,'2022-04-18 22:30:27',NULL),('PFS','HIGH',0.5,'GENERATOR',1,'2022-04-18 22:30:25','2022-04-27 17:05:09'),('PFS','LOW',0.5,'GENERATOR',-1,'2022-04-18 22:30:23','2022-04-27 17:05:09'),('PFS','MEDIUM',0.5,'GENERATOR',0,'2022-04-18 22:30:24','2022-04-27 17:05:09'),('PFS','NONE',0,'GENERATOR',-99,'2022-04-18 22:30:20',NULL),('PFS','SLIGHTLY',0.7,'HEDGE',-1,'2022-04-18 22:30:23','2022-04-29 14:02:20'),('PFS','VERY',0.3,'HEDGE',1,'2022-04-18 22:30:22','2022-04-29 14:02:20');

--
-- Table structure for table `kdc_examination_result`
--

DROP TABLE IF EXISTS `kdc_examination_result`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `kdc_examination_result` (
  `examination_id` varchar(50) NOT NULL,
  `WBC` double DEFAULT NULL,
  `LY` double DEFAULT NULL,
  `NE` double DEFAULT NULL,
  `RBC` double DEFAULT NULL,
  `HGB` double DEFAULT NULL,
  `HCT` double DEFAULT NULL,
  `PLT` double DEFAULT NULL,
  `NA` double DEFAULT NULL,
  `K` double DEFAULT NULL,
  `Total protein` double DEFAULT NULL,
  `Albumin` double DEFAULT NULL,
  `Ure` double DEFAULT NULL,
  `Creatinin` double DEFAULT NULL,
  `result` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`examination_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kdc_examination_result`
--


--
-- Table structure for table `linguistic_domain`
--

DROP TABLE IF EXISTS `linguistic_domain`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `linguistic_domain` (
  `application_id` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL,
  `fm` double DEFAULT NULL,
  `v` double DEFAULT NULL,
  `linguistic_order` int(11) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`application_id`,`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `linguistic_domain`
--

INSERT INTO `linguistic_domain` (`application_id`, `name`, `fm`, `v`, `linguistic_order`, `created_at`, `updated_at`) VALUES ('KDC','HIGH',0.6,0.64,3,'2022-04-29 15:07:16','2022-04-29 16:25:58'),('KDC','LITTLE_HIGH',0.12,0.496,1,'2022-04-28 16:37:39','2022-04-29 16:20:36'),('KDC','LITTLE_LOW',0.08,0.336,-1,'2022-04-28 16:37:37','2022-04-29 16:20:36'),('KDC','LOW',0.4,0.24,-3,'2022-04-29 15:07:15','2022-04-29 15:08:01'),('KDC','MEDIUM',0.4,0.4,0,'2022-04-28 16:37:38','2022-04-29 15:05:27'),('KDC','MORE_HIGH',0.15,0.7,4,'2022-04-28 16:37:40','2022-04-29 15:05:27'),('KDC','MORE_LOW',0.1,0.2,-4,'2022-04-28 16:37:36','2022-04-29 15:05:27'),('KDC','POSSIBLE_HIGH',0.12,0.568,2,'2022-04-28 16:37:39','2022-04-29 15:05:27'),('KDC','POSSIBLE_LOW',0.08,0.288,-2,'2022-04-28 16:37:37','2022-04-29 15:05:27'),('KDC','VERY_HIGH',0.21,0.784,5,'2022-04-28 16:37:41','2022-04-29 16:20:36'),('KDC','VERY_LOW',0.14,0.144,-5,'2022-04-28 16:37:35','2022-04-29 16:20:36'),('PFS','COMPLETELY',1,1,99,'2022-04-18 22:54:51',NULL),('PFS','HIGH',0.5,0.85,3,'2022-04-18 22:54:49','2022-04-29 14:02:20'),('PFS','LOW',0.5,0.15,-1,'2022-04-18 22:54:47','2022-04-29 14:02:20'),('PFS','MEDIUM',0.5,0.5,0,'2022-04-18 22:54:48','2022-04-27 17:05:09'),('PFS','NONE',0,0,-99,'2022-04-18 22:54:44',NULL),('PFS','SLIGHTLY_HIGH',0.35,0.745,1,'2022-04-18 22:54:49','2022-04-29 14:02:20'),('PFS','SLIGHTLY_LOW',0.35,0.255,-2,'2022-04-18 22:54:46','2022-04-29 14:02:20'),('PFS','VERY_HIGH',0.15,0.955,2,'2022-04-18 22:54:50','2022-04-29 14:02:20'),('PFS','VERY_LOW',0.15,0.045,-3,'2022-04-18 22:54:48','2022-04-29 14:02:20');

--
-- Table structure for table `patient`
--

DROP TABLE IF EXISTS `patient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `patient` (
  `id` varchar(50) NOT NULL,
  `name` varchar(100) NOT NULL,
  `birth_date` date DEFAULT NULL,
  `gender` varchar(50) DEFAULT NULL,
  `avatar` varchar(100) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient`
--

INSERT INTO `patient` (`id`, `name`, `birth_date`, `gender`, `avatar`, `created_at`, `updated_at`) VALUES ('0123','Pham Van Khoa','1987-05-21','MALE',NULL,'2022-04-27 17:32:29',NULL);

--
-- Table structure for table `patient_symptom`
--

DROP TABLE IF EXISTS `patient_symptom`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `patient_symptom` (
  `examination_id` varchar(50) NOT NULL,
  `symptom` varchar(50) NOT NULL,
  `positive` double DEFAULT NULL,
  `neutral` double DEFAULT NULL,
  `negative` double DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  PRIMARY KEY (`examination_id`,`symptom`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient_symptom`
--

INSERT INTO `patient_symptom` (`examination_id`, `symptom`, `positive`, `neutral`, `negative`, `created_at`) VALUES ('01G1QH3P4FXEK2H8BZ87FKFME5','CHEST_PAIN',0.2,0.3,0.5,'2022-04-28 14:28:02'),('01G1QH3P4FXEK2H8BZ87FKFME5','COUGH',0.7,0.15,0.1,'2022-04-28 14:28:02'),('01G1QH3P4FXEK2H8BZ87FKFME5','HEADACHE',0.7,0.05,0.2,'2022-04-28 14:28:02'),('01G1QH3P4FXEK2H8BZ87FKFME5','STOMACH_PAIN',0.1,0.2,0.6,'2022-04-28 14:28:02'),('01G1QH3P4FXEK2H8BZ87FKFME5','TEMPERATURE',0.7,0.125,0.1,'2022-04-28 14:28:02'),('01G1QJE2NNCBBAPHCRGR1TJ4F9','CHEST_PAIN',0.2,0.3,0.5,'2022-04-28 14:51:11'),('01G1QJE2NNCBBAPHCRGR1TJ4F9','COUGH',0.2,0.15,0.1,'2022-04-28 14:51:11'),('01G1QJE2NNCBBAPHCRGR1TJ4F9','HEADACHE',0.7,0.05,0.2,'2022-04-28 14:51:11'),('01G1QJE2NNCBBAPHCRGR1TJ4F9','STOMACH_PAIN',0.1,0.2,0.6,'2022-04-28 14:51:11'),('01G1QJE2NNCBBAPHCRGR1TJ4F9','TEMPERATURE',0.7,0.125,0.1,'2022-04-28 14:51:11'),('01G1QJGT80MT93V8VNKD6QW6MA','CHEST_PAIN',0.2,0.3,0.5,'2022-04-28 14:52:40'),('01G1QJGT80MT93V8VNKD6QW6MA','COUGH',0.7,0.15,0.1,'2022-04-28 14:52:40'),('01G1QJGT80MT93V8VNKD6QW6MA','HEADACHE',0.7,0.05,0.2,'2022-04-28 14:52:40'),('01G1QJGT80MT93V8VNKD6QW6MA','STOMACH_PAIN',0.1,0.2,0.6,'2022-04-28 14:52:40'),('01G1QJGT80MT93V8VNKD6QW6MA','TEMPERATURE',0.7,0.125,0.1,'2022-04-28 14:52:40');

--
-- Table structure for table `pfs_examination_result`
--

DROP TABLE IF EXISTS `pfs_examination_result`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pfs_examination_result` (
  `examination_id` varchar(50) NOT NULL,
  `diagnose` varchar(50) NOT NULL,
  `probability` double DEFAULT NULL,
  PRIMARY KEY (`examination_id`,`diagnose`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pfs_examination_result`
--

INSERT INTO `pfs_examination_result` (`examination_id`, `diagnose`, `probability`) VALUES ('01G1QH3P4FXEK2H8BZ87FKFME5','CHEST_PROBLEM',0.06),('01G1QH3P4FXEK2H8BZ87FKFME5','FEVER',0.41),('01G1QH3P4FXEK2H8BZ87FKFME5','MALARIA',0.683),('01G1QH3P4FXEK2H8BZ87FKFME5','STOMACH',0.21),('01G1QH3P4FXEK2H8BZ87FKFME5','TYPHOID',0.682),('01G1QJE2NNCBBAPHCRGR1TJ4F9','CHEST_PROBLEM',0.06),('01G1QJE2NNCBBAPHCRGR1TJ4F9','FEVER',0.355),('01G1QJE2NNCBBAPHCRGR1TJ4F9','MALARIA',0.683),('01G1QJE2NNCBBAPHCRGR1TJ4F9','STOMACH',0.21),('01G1QJE2NNCBBAPHCRGR1TJ4F9','TYPHOID',0.682),('01G1QJGT80MT93V8VNKD6QW6MA','CHEST_PROBLEM',0.06),('01G1QJGT80MT93V8VNKD6QW6MA','FEVER',0.41),('01G1QJGT80MT93V8VNKD6QW6MA','MALARIA',0.683),('01G1QJGT80MT93V8VNKD6QW6MA','STOMACH',0.21),('01G1QJGT80MT93V8VNKD6QW6MA','TYPHOID',0.682);

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` varchar(50) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

INSERT INTO `role` (`id`, `name`, `description`, `created_at`, `updated_at`) VALUES ('01FCZG2SXNZYGQXMPPBB8FV3ZV','ADMIN','role admin','2022-01-28 10:21:34','2022-01-28 10:21:36'),('01FCZG2SXNZYGQXMPPBB8FV4ZV','USER','role user','2022-01-28 10:21:37','2022-01-28 10:21:37');

--
-- Table structure for table `symptom`
--

DROP TABLE IF EXISTS `symptom`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `symptom` (
  `name` varchar(50) NOT NULL,
  `description` text DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `symptom`
--

INSERT INTO `symptom` (`name`, `description`, `created_at`, `updated_at`) VALUES ('CHEST_PAIN',NULL,'2022-02-26 18:03:48','2022-02-26 18:03:50'),('COUGH',NULL,'2022-02-26 18:03:43','2022-02-26 18:03:47'),('HEADACHE',NULL,'2022-02-26 18:02:12','2022-02-26 18:02:17'),('STOMACH_PAIN',NULL,'2022-02-26 18:02:50','2022-02-26 18:03:47'),('TEMPERATURE',NULL,'2022-02-26 18:01:55','2022-02-26 18:01:57');

--
-- Table structure for table `symptom_diagnose`
--

DROP TABLE IF EXISTS `symptom_diagnose`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `symptom_diagnose` (
  `symptom` varchar(50) NOT NULL,
  `diagnose` varchar(50) NOT NULL,
  `positive` double DEFAULT NULL,
  `neutral` double DEFAULT NULL,
  `negative` double DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  PRIMARY KEY (`symptom`,`diagnose`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `symptom_diagnose`
--

INSERT INTO `symptom_diagnose` (`symptom`, `diagnose`, `positive`, `neutral`, `negative`, `created_at`) VALUES ('CHEST_PAIN','CHEST_PROBLEM',0.9,0.02,0.05,'2022-02-26 18:07:59'),('CHEST_PAIN','FEVER',0.05,0.25,0.6,'2022-02-26 18:07:59'),('CHEST_PAIN','MALARIA',0.03,0.07,0.8,'2022-02-26 18:07:59'),('CHEST_PAIN','STOMACH',0.1,0.1,0.7,'2022-02-26 18:07:59'),('CHEST_PAIN','TYPHOID',0.01,0.01,0.85,'2022-02-26 18:07:59'),('COUGH','CHEST_PROBLEM',0.15,0.2,0.7,'2022-02-26 18:07:59'),('COUGH','FEVER',0.45,0.2,0.1,'2022-02-26 18:07:59'),('COUGH','MALARIA',0.65,0.5,0.05,'2022-02-26 18:07:59'),('COUGH','STOMACH',0.25,0.25,0.5,'2022-02-26 18:07:59'),('COUGH','TYPHOID',0.2,0.15,0.6,'2022-02-26 18:07:59'),('HEADACHE','CHEST_PROBLEM',0.01,0.1,0.8,'2022-02-26 18:07:59'),('HEADACHE','FEVER',0.4,0.25,0.3,'2022-02-26 18:07:59'),('HEADACHE','MALARIA',0.1,0.2,0.6,'2022-02-26 18:07:59'),('HEADACHE','STOMACH',0.3,0.05,0.05,'2022-02-26 18:07:59'),('HEADACHE','TYPHOID',0.75,0.05,0.03,'2022-02-26 18:07:59'),('STOMACH_PAIN','CHEST_PROBLEM',0.1,0.15,0.75,'2022-02-26 18:07:59'),('STOMACH_PAIN','FEVER',0.1,0.25,0.6,'2022-02-26 18:07:59'),('STOMACH_PAIN','MALARIA',0.01,0.03,0.9,'2022-02-26 18:07:59'),('STOMACH_PAIN','STOMACH',0.8,0.1,0.01,'2022-02-26 18:07:59'),('STOMACH_PAIN','TYPHOID',0.1,0.2,0.7,'2022-02-26 18:07:59'),('TEMPERATURE','CHEST_PROBLEM',0.05,0.15,0.7,'2022-02-26 18:07:59'),('TEMPERATURE','FEVER',0.4,0.4,0.05,'2022-02-26 18:07:59'),('TEMPERATURE','MALARIA',0.8,0.1,0.1,'2022-02-26 18:07:59'),('TEMPERATURE','STOMACH',0.15,0.05,0.6,'2022-02-26 18:07:59'),('TEMPERATURE','TYPHOID',0.3,0.3,0.3,'2022-02-26 18:07:59');

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` varchar(50) NOT NULL,
  `username` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `password_token` varchar(255) DEFAULT NULL,
  `token_creation_date` datetime DEFAULT NULL,
  `is_enable` tinyint(1) NOT NULL,
  `first_name` varchar(80) DEFAULT NULL,
  `last_name` varchar(80) DEFAULT NULL,
  `avatar` varchar(100) DEFAULT NULL,
  `birth_date` date DEFAULT NULL,
  `gender` varchar(50) DEFAULT NULL,
  `description` text DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `username`, `email`, `password`, `password_token`, `token_creation_date`, `is_enable`, `first_name`, `last_name`, `avatar`, `birth_date`, `gender`, `description`, `created_at`, `updated_at`) VALUES ('02FCZG2SXNZYGQXMPPBB8FV3ZV','hoangtl','hoangtl@gmail.com','$2a$10$qbCJB7znYS/KD0sFR8f.C.o97a2PYFSGC8KyiKD.nYG7ZT2gaGm2y',NULL,NULL,1,'tran','hoang','localhost:8080/asdasd','2022-04-20','MALE','abctest','2022-01-28 10:23:08','2022-01-28 10:23:08');

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_role` (
  `user_id` varchar(50) NOT NULL,
  `role_id` varchar(50) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

INSERT INTO `user_role` (`user_id`, `role_id`, `created_at`, `updated_at`) VALUES ('02FCZG2SXNZYGQXMPPBB8FV3ZV','01FCZG2SXNZYGQXMPPBB8FV3ZV','2022-01-28 10:25:44','2022-01-28 10:25:45'),('02FCZG2SXNZYGQXMPPBB8FV3ZV','01FCZG2SXNZYGQXMPPBB8FV4ZV','2022-01-28 10:25:45','2022-01-28 10:25:46');
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-05-04  8:27:02
