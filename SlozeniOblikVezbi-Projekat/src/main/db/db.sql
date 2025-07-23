CREATE DATABASE  IF NOT EXISTS `webprojekat` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `webprojekat`;
-- MySQL dump 10.13  Distrib 8.4.5, for Linux (x86_64)
--
-- Host: localhost    Database: webprojekat
-- ------------------------------------------------------
-- Server version	8.4.5

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `aerodromi`
--

DROP TABLE IF EXISTS `aerodromi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `aerodromi` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `oznaka` varchar(3) NOT NULL,
  `lokacijaId` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `lokacijaId` (`lokacijaId`),
  CONSTRAINT `aerodromi_ibfk_1` FOREIGN KEY (`lokacijaId`) REFERENCES `lokacije` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `aerodromi`
--

LOCK TABLES `aerodromi` WRITE;
/*!40000 ALTER TABLE `aerodromi` DISABLE KEYS */;
INSERT INTO `aerodromi` VALUES (1,'BEG',1),(2,'JFK',2),(3,'FKN',2),(4,'HND',3),(5,'SYD',4),(6,'CAI',5),(7,'EZE',6),(8,'CDG',7),(9,'CPT',8),(10,'AKL',9),(11,'MCM',10),(12,'WSG',11);
/*!40000 ALTER TABLE `aerodromi` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `avioni`
--

DROP TABLE IF EXISTS `avioni`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `avioni` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `naziv` varchar(100) NOT NULL,
  `brojKolona` int DEFAULT NULL,
  `brojRedova` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `avioni`
--

LOCK TABLES `avioni` WRITE;
/*!40000 ALTER TABLE `avioni` DISABLE KEYS */;
INSERT INTO `avioni` VALUES (1,'Boeing 747',4,8),(2,'Airbus A330',5,10),(3,'Cessna C52',2,5);
/*!40000 ALTER TABLE `avioni` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `karte`
--

DROP TABLE IF EXISTS `karte`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `karte` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `letId` bigint DEFAULT NULL,
  `brojSedista` varchar(10) NOT NULL,
  `cena` int NOT NULL,
  `imeIPrezimePutnika` varchar(100) NOT NULL,
  `brojPasosa` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `letId` (`letId`),
  CONSTRAINT `karte_ibfk_1` FOREIGN KEY (`letId`) REFERENCES `letovi` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `karte`
--

LOCK TABLES `karte` WRITE;
/*!40000 ALTER TABLE `karte` DISABLE KEYS */;
INSERT INTO `karte` VALUES (1,4,'2-5',40000,'Petar Petrovic','SRB123456');
/*!40000 ALTER TABLE `karte` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `korisnici`
--

DROP TABLE IF EXISTS `korisnici`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `korisnici` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `korisnickoIme` varchar(20) NOT NULL,
  `lozinka` varchar(20) NOT NULL,
  `email` varchar(50) NOT NULL,
  `ime` varchar(20) NOT NULL,
  `prezime` varchar(20) NOT NULL,
  `datumRodjenja` date NOT NULL,
  `datumIVremeRegistracije` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `uloga` enum('PUTNIK','ADMIN') DEFAULT NULL,
  `blokiran` tinyint(1) DEFAULT NULL,
  `loyaltyBodovi` int DEFAULT NULL,
  `zahtevaLoyalty` tinyint(1) DEFAULT NULL,
  `paraPotroseno` int DEFAULT NULL,
  `listaZelja` varchar(85) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `korisnici`
--

LOCK TABLES `korisnici` WRITE;
/*!40000 ALTER TABLE `korisnici` DISABLE KEYS */;
INSERT INTO `korisnici` VALUES (1,'pera','pera123','petar.petrovic@gmail.com','Petar','Petrović','1976-06-24','2023-11-05 00:32:48','ADMIN',0,5,0,0,''),(2,'marko','marko123','marko.petrovic@example.com','Marko','Petrović','1990-03-15','2024-12-21 16:34:22','PUTNIK',0,6,0,0,''),(3,'nikola','nikola123','nikola.tesla@example.com','Nikola','Tesla','1976-01-07','2024-12-21 22:45:30','PUTNIK',0,-1,0,0,''),(4,'ana.j','securepass','ana.jovanovic@example.com','Ana','Jovanović','1985-07-10','2024-12-21 17:45:30','PUTNIK',0,-1,0,0,''),(5,'ivan.n','mypassword','ivan.nikolic@example.com','Ivan','Nikolić','2000-01-25','2024-12-21 18:12:10','PUTNIK',0,-1,0,0,''),(6,'milica.s','qwerty123','milica.stankovic@example.com','Milica','Stanković','1995-11-05','2024-12-21 19:05:50','PUTNIK',0,-1,0,0,''),(7,'stefan.p','abc12345','stefan.popovic@example.com','Stefan','Popović','1988-09-12','2024-12-21 20:30:15','PUTNIK',0,-1,0,0,''),(8,'jelena.k','jelena2024','jelena.kovac@example.com','Jelena','Kovač','1993-06-18','2024-12-21 21:10:05','PUTNIK',0,-1,0,0,''),(9,'dragana.b','dragon789','dragana.bogdanovic@example.com','Dragana','Bogdanović','1992-04-22','2024-12-21 23:15:40','PUTNIK',1,-1,0,0,''),(10,'aleksandar.v','alex123','aleksandar.vukovsic@example.com','Aleksandar','Vuković','1998-11-30','2024-12-22 00:30:00','PUTNIK',1,-1,0,0,''),(11,'milos','milos123','aleksandar.vukovsic@example.com','Aleksandar','Vuković','1998-11-30','2024-12-22 00:30:00','PUTNIK',1,-1,0,0,''),(12,'katarina.m','katy2024','katarina.milosevic@example.com','Katarina','Milošević','1994-08-15','2024-12-22 01:20:50','PUTNIK',0,-1,0,0,'');
/*!40000 ALTER TABLE `korisnici` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `letovi`
--

DROP TABLE IF EXISTS `letovi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `letovi` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `oznaka` varchar(100) NOT NULL,
  `polazisteId` bigint NOT NULL,
  `odredisteId` bigint NOT NULL,
  `avionId` bigint NOT NULL,
  `terminPolaska` datetime NOT NULL,
  `trajanjeLeta` int NOT NULL,
  `cena` int NOT NULL,
  `naAkciji` tinyint(1) NOT NULL,
  `brojMesta` int NOT NULL,
  `razlogOtkaza` varchar(100) NOT NULL,
  `datumVazenjaAkcije` date NOT NULL,
  `staraCena` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `polazisteId` (`polazisteId`),
  KEY `odredisteId` (`odredisteId`),
  KEY `avionId` (`avionId`),
  CONSTRAINT `letovi_ibfk_1` FOREIGN KEY (`polazisteId`) REFERENCES `aerodromi` (`id`),
  CONSTRAINT `letovi_ibfk_2` FOREIGN KEY (`odredisteId`) REFERENCES `aerodromi` (`id`),
  CONSTRAINT `letovi_ibfk_3` FOREIGN KEY (`avionId`) REFERENCES `avioni` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `letovi`
--

LOCK TABLES `letovi` WRITE;
/*!40000 ALTER TABLE `letovi` DISABLE KEYS */;
INSERT INTO `letovi` VALUES (1,'FL002',2,7,2,'2025-07-16 12:00:00',840,55000,1,50,'','2035-11-29',80000),(2,'FL003',3,4,3,'2025-07-17 05:45:00',540,38000,1,10,'','2035-11-29',80000),(3,'FL004',4,5,1,'2025-07-18 19:20:00',420,30000,0,32,'','1970-01-01',30000),(4,'FL005',5,6,2,'2025-07-19 07:10:00',720,47000,1,49,'','2035-11-29',80000),(5,'FL006',8,2,1,'2025-07-15 08:30:00',600,40000,0,32,'','1970-01-01',40000),(6,'FL007',9,3,2,'2025-07-16 12:00:00',840,55000,0,50,'','1970-01-01',55000),(7,'FL008',10,4,3,'2025-07-17 05:45:00',540,38000,0,10,'','1970-01-01',38000),(8,'FL009',2,4,1,'2025-07-18 19:20:00',420,30000,0,32,'','1970-01-01',30000),(9,'FL010',1,9,2,'2025-07-19 07:10:00',720,47000,0,50,'','1970-01-01',47000),(11,'FLT',4,9,2,'2027-04-26 13:15:00',555,5000,0,50,'','1970-01-01',5000),(12,'FL011',1,8,1,'2025-07-31 06:00:00',15,17850,0,32,'','1970-01-01',17850),(13,'FL012',8,12,2,'2025-07-31 10:00:00',15,55000,0,50,'','1970-01-01',55000),(14,'FL013',12,2,1,'2025-07-31 15:00:00',15,47500,0,32,'','1970-01-01',47500);
/*!40000 ALTER TABLE `letovi` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lokacije`
--

DROP TABLE IF EXISTS `lokacije`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lokacije` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `Grad` varchar(100) NOT NULL,
  `Drzava` varchar(100) NOT NULL,
  `Kontinent` enum('Evropa','Amerika','Azija','Australija','Afrika','Antartika','Okeanija') DEFAULT NULL,
  `putanjaDoSlike` varchar(1000) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lokacije`
--

LOCK TABLES `lokacije` WRITE;
/*!40000 ALTER TABLE `lokacije` DISABLE KEYS */;
INSERT INTO `lokacije` VALUES (1,'Beograd','Srbija','Evropa','turizam1.jpg'),(2,'New York','Sjedinjene Američke Države','Amerika','turizam2.jpg'),(3,'Tokio','Japan','Azija','turizam9.jpg'),(4,'Sidnej','Australija','Australija','turizam4.jpg'),(5,'Kairo','Egipat','Afrika','turizam5.jpg'),(6,'Buenos Aires','Argentina','Amerika','turizam6.jpg'),(7,'Pariz','Francuska','Evropa','turizam7.jpg'),(8,'Kejp Taun','Južnoafrička Republika','Afrika','turizam8.jpg'),(9,'Auckland','Novi Zeland','Okeanija','turizam9.jpg'),(10,'McMurdo Station','Antarktik','Antartika','turizam10.jpg'),(11,'Washington','Sjedinjene Američke Države','Amerika','turizam11.jpg');
/*!40000 ALTER TABLE `lokacije` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rezervacija_karta`
--

DROP TABLE IF EXISTS `rezervacija_karta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rezervacija_karta` (
  `rezervacijaId` bigint NOT NULL,
  `kartaId` bigint NOT NULL,
  PRIMARY KEY (`rezervacijaId`,`kartaId`),
  KEY `kartaId` (`kartaId`),
  CONSTRAINT `rezervacija_karta_ibfk_1` FOREIGN KEY (`rezervacijaId`) REFERENCES `rezervacije` (`id`),
  CONSTRAINT `rezervacija_karta_ibfk_2` FOREIGN KEY (`kartaId`) REFERENCES `karte` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rezervacija_karta`
--

LOCK TABLES `rezervacija_karta` WRITE;
/*!40000 ALTER TABLE `rezervacija_karta` DISABLE KEYS */;
INSERT INTO `rezervacija_karta` VALUES (1,1);
/*!40000 ALTER TABLE `rezervacija_karta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rezervacije`
--

DROP TABLE IF EXISTS `rezervacije`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rezervacije` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `idKorisnika` bigint NOT NULL,
  `datumIVremeKreiranja` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `ukupnaCena` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idKorisnika` (`idKorisnika`),
  CONSTRAINT `rezervacije_ibfk_1` FOREIGN KEY (`idKorisnika`) REFERENCES `korisnici` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rezervacije`
--

LOCK TABLES `rezervacije` WRITE;
/*!40000 ALTER TABLE `rezervacije` DISABLE KEYS */;
INSERT INTO `rezervacije` VALUES (1,1,'2025-07-22 04:55:00',40000);
/*!40000 ALTER TABLE `rezervacije` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-07-22  7:23:50
