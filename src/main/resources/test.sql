-- MariaDB dump 10.18  Distrib 10.5.8-MariaDB, for osx10.16 (x86_64)
--
-- Host: localhost    Database: fibudb
-- ------------------------------------------------------
-- Server version	10.5.8-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Account`
--

DROP TABLE IF EXISTS `Account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Account` (
  `accountID` int(11) NOT NULL AUTO_INCREMENT,
  `accountNumber` varchar(45) DEFAULT NULL,
  `accountName` varchar(45) NOT NULL,
  PRIMARY KEY (`accountID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Account`
--

LOCK TABLES `Account` WRITE;
/*!40000 ALTER TABLE `Account` DISABLE KEYS */;
/*!40000 ALTER TABLE `Account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `AccountChart`
--

DROP TABLE IF EXISTS `AccountChart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `AccountChart` (
  `accountChartID` int(11) NOT NULL AUTO_INCREMENT,
  `acName` varchar(45) NOT NULL,
  PRIMARY KEY (`accountChartID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `AccountChart`
--

LOCK TABLES `AccountChart` WRITE;
/*!40000 ALTER TABLE `AccountChart` DISABLE KEYS */;
/*!40000 ALTER TABLE `AccountChart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Account_AccountChart`
--

DROP TABLE IF EXISTS `Account_AccountChart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Account_AccountChart` (
  `aacID` int(11) NOT NULL AUTO_INCREMENT,
  `accountID` int(11) NOT NULL,
  `accountChartID` int(11) NOT NULL,
  PRIMARY KEY (`aacID`),
  KEY `aac-Account-FK_idx` (`accountID`),
  KEY `aac-AccountChart-FK_idx` (`accountChartID`),
  CONSTRAINT `aac-Account-FK` FOREIGN KEY (`accountID`) REFERENCES `Account` (`accountID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `aac-AccountChart-FK` FOREIGN KEY (`accountChartID`) REFERENCES `AccountChart` (`accountChartID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Account_AccountChart`
--

LOCK TABLES `Account_AccountChart` WRITE;
/*!40000 ALTER TABLE `Account_AccountChart` DISABLE KEYS */;
/*!40000 ALTER TABLE `Account_AccountChart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `BookEntry`
--

DROP TABLE IF EXISTS `BookEntry`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `BookEntry` (
  `bookEntryID` int(11) NOT NULL AUTO_INCREMENT,
  `accountSoll` int(11) NOT NULL,
  `accountHaben` int(11) NOT NULL,
  `amount` decimal(11,2) NOT NULL,
  `subquestionID` int(11) NOT NULL,
  `userID` int(11) NOT NULL,
  PRIMARY KEY (`bookEntryID`),
  KEY `BookEntry-Subquestion-FK_idx` (`subquestionID`),
  KEY `BookEntry-User-FK_idx` (`userID`),
  KEY `BookEntrySoll-Account-FK_idx` (`accountSoll`),
  KEY `BookEntryHaben-Account-FK_idx` (`accountHaben`),
  CONSTRAINT `BookEntry-Subquestion-FK` FOREIGN KEY (`subquestionID`) REFERENCES `Subquestion` (`subquestionID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `BookEntry-User-FK` FOREIGN KEY (`userID`) REFERENCES `User` (`userID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `BookEntryHaben-Account-FK` FOREIGN KEY (`accountHaben`) REFERENCES `Account` (`accountID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `BookEntrySoll-Account-FK` FOREIGN KEY (`accountSoll`) REFERENCES `Account` (`accountID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `BookEntry`
--

LOCK TABLES `BookEntry` WRITE;
/*!40000 ALTER TABLE `BookEntry` DISABLE KEYS */;
/*!40000 ALTER TABLE `BookEntry` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Class`
--

DROP TABLE IF EXISTS `Class`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Class` (
  `classID` int(11) NOT NULL AUTO_INCREMENT,
  `className` varchar(45) NOT NULL,
  PRIMARY KEY (`classID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Class`
--

LOCK TABLES `Class` WRITE;
/*!40000 ALTER TABLE `Class` DISABLE KEYS */;
/*!40000 ALTER TABLE `Class` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `EGResult`
--

DROP TABLE IF EXISTS `EGResult`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `EGResult` (
  `egResultID` int(11) NOT NULL AUTO_INCREMENT,
  `userID` int(11) NOT NULL,
  `exerciseGroupID` int(11) NOT NULL,
  `solved` decimal(3,1) NOT NULL,
  `correct` decimal(3,1) NOT NULL,
  PRIMARY KEY (`egResultID`),
  KEY `EGResult-User-FK_idx` (`userID`),
  KEY `EGResult-ExerciseGroup-FK_idx` (`exerciseGroupID`),
  CONSTRAINT `EGResult-ExerciseGroup-FK` FOREIGN KEY (`exerciseGroupID`) REFERENCES `ExerciseGroup` (`exerciseGroupID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `EGResult-User-FK` FOREIGN KEY (`userID`) REFERENCES `User` (`userID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `EGResult`
--

LOCK TABLES `EGResult` WRITE;
/*!40000 ALTER TABLE `EGResult` DISABLE KEYS */;
/*!40000 ALTER TABLE `EGResult` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ExerciseGroup`
--

DROP TABLE IF EXISTS `ExerciseGroup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ExerciseGroup` (
  `exerciseGroupID` int(11) NOT NULL AUTO_INCREMENT,
  `exGroupName` varchar(45) NOT NULL,
  `subjectID` int(11) NOT NULL,
  `accountChartID` int(11) NOT NULL,
  `questionAmount` int(11) NOT NULL,
  PRIMARY KEY (`exerciseGroupID`),
  KEY `ExerciseGroup-Subject-FK_idx` (`subjectID`),
  KEY `ExerciseGroup-AccountChart-FK_idx` (`accountChartID`),
  CONSTRAINT `ExerciseGroup-AccountChart-FK` FOREIGN KEY (`accountChartID`) REFERENCES `AccountChart` (`accountChartID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `ExerciseGroup-Subject-FK` FOREIGN KEY (`subjectID`) REFERENCES `Subject` (`subjectID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ExerciseGroup`
--

LOCK TABLES `ExerciseGroup` WRITE;
/*!40000 ALTER TABLE `ExerciseGroup` DISABLE KEYS */;
/*!40000 ALTER TABLE `ExerciseGroup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `MCAnswer`
--

DROP TABLE IF EXISTS `MCAnswer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `MCAnswer` (
  `mcAnswerID` int(11) NOT NULL AUTO_INCREMENT,
  `mcOptionID` int(11) NOT NULL,
  `userID` int(11) NOT NULL,
  PRIMARY KEY (`mcAnswerID`),
  KEY `MCAnswer-User-FK_idx` (`userID`),
  KEY `MCAnswer-MCOption-FK_idx` (`mcOptionID`),
  CONSTRAINT `MCAnswer-MCOption-FK` FOREIGN KEY (`mcOptionID`) REFERENCES `MCOption` (`mcOptionID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `MCAnswer-User-FK` FOREIGN KEY (`userID`) REFERENCES `User` (`userID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MCAnswer`
--

LOCK TABLES `MCAnswer` WRITE;
/*!40000 ALTER TABLE `MCAnswer` DISABLE KEYS */;
/*!40000 ALTER TABLE `MCAnswer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `MCOption`
--

DROP TABLE IF EXISTS `MCOption`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `MCOption` (
  `mcOptionID` int(11) NOT NULL AUTO_INCREMENT,
  `mcoName` varchar(200) NOT NULL,
  `subquestionID` int(11) NOT NULL,
  PRIMARY KEY (`mcOptionID`),
  KEY `MCOption-Subquestion-FK_idx` (`subquestionID`),
  CONSTRAINT `MCOption-Subquestion-FK` FOREIGN KEY (`subquestionID`) REFERENCES `Subquestion` (`subquestionID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MCOption`
--

LOCK TABLES `MCOption` WRITE;
/*!40000 ALTER TABLE `MCOption` DISABLE KEYS */;
/*!40000 ALTER TABLE `MCOption` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Question`
--

DROP TABLE IF EXISTS `Question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Question` (
  `questionID` int(11) NOT NULL AUTO_INCREMENT,
  `qNumber` int(11) NOT NULL,
  `qText` varchar(750) NOT NULL,
  `qDisclaimer` varchar(200) DEFAULT NULL,
  `exerciseGroupID` int(11) NOT NULL,
  PRIMARY KEY (`questionID`),
  KEY `Question-ExerciseGroup-FK_idx` (`exerciseGroupID`),
  CONSTRAINT `Question-ExerciseGroup-FK` FOREIGN KEY (`exerciseGroupID`) REFERENCES `ExerciseGroup` (`exerciseGroupID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Question`
--

LOCK TABLES `Question` WRITE;
/*!40000 ALTER TABLE `Question` DISABLE KEYS */;
/*!40000 ALTER TABLE `Question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Subject`
--

DROP TABLE IF EXISTS `Subject`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Subject` (
  `subjectID` int(11) NOT NULL AUTO_INCREMENT,
  `subjectName` varchar(45) NOT NULL,
  PRIMARY KEY (`subjectID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Subject`
--

LOCK TABLES `Subject` WRITE;
/*!40000 ALTER TABLE `Subject` DISABLE KEYS */;
/*!40000 ALTER TABLE `Subject` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Subquestion`
--

DROP TABLE IF EXISTS `Subquestion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Subquestion` (
  `subquestionID` int(11) NOT NULL AUTO_INCREMENT,
  `sqText` varchar(200) NOT NULL,
  `sqLetter` varchar(1) DEFAULT NULL,
  `sqType` varchar(45) NOT NULL,
  `questionID` int(11) NOT NULL,
  PRIMARY KEY (`subquestionID`),
  KEY `Subquestion-Question-FK_idx` (`questionID`),
  CONSTRAINT `Subquestion-Question-FK` FOREIGN KEY (`questionID`) REFERENCES `Question` (`questionID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Subquestion`
--

LOCK TABLES `Subquestion` WRITE;
/*!40000 ALTER TABLE `Subquestion` DISABLE KEYS */;
/*!40000 ALTER TABLE `Subquestion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `User` (
  `userID` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `passwordHash` varchar(45) NOT NULL,
  `type` varchar(45) NOT NULL,
  `classID` int(11) DEFAULT NULL,
  PRIMARY KEY (`userID`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  KEY `User-Class-FK_idx` (`classID`),
  CONSTRAINT `User-Class-FK` FOREIGN KEY (`classID`) REFERENCES `Class` (`classID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `User`
--

LOCK TABLES `User` WRITE;
/*!40000 ALTER TABLE `User` DISABLE KEYS */;
/*!40000 ALTER TABLE `User` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-02-25 17:27:09
