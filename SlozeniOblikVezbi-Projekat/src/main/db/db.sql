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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `aerodromi`
--

LOCK TABLES `aerodromi` WRITE;
/*!40000 ALTER TABLE `aerodromi` DISABLE KEYS */;
INSERT INTO `aerodromi` VALUES (1,'BEG',1),(2,'JFK',2),(3,'HND',3),(4,'SYD',4),(5,'CAI',5),(6,'EZE',6),(7,'CDG',7),(8,'CPT',8),(9,'AKL',9),(10,'MCM',10);
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
INSERT INTO `avioni` VALUES (1,'Boeing 747',8,19),(2,'Airbus A330',4,10),(3,'Cessna C52',3,7);
/*!40000 ALTER TABLE `avioni` ENABLE KEYS */;
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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `korisnici`
--

LOCK TABLES `korisnici` WRITE;
/*!40000 ALTER TABLE `korisnici` DISABLE KEYS */;
INSERT INTO `korisnici` VALUES (1,'pera','pera123','petar.petrovic@gmail.com','Petar','Petrović','1976-06-24','2023-11-05 00:32:48','ADMIN',0),(2,'marko','marko123','marko.petrovic@example.com','Marko','Petrović','1990-03-15','2024-12-21 16:34:22','PUTNIK',0),(3,'ana.j','securepass','ana.jovanovic@example.com','Ana','Jovanović','1985-07-10','2024-12-21 17:45:30','PUTNIK',0),(4,'ivan.n','mypassword','ivan.nikolic@example.com','Ivan','Nikolić','2000-01-25','2024-12-21 18:12:10','PUTNIK',0),(5,'milica.s','qwerty123','milica.stankovic@example.com','Milica','Stanković','1995-11-05','2024-12-21 19:05:50','PUTNIK',0),(6,'stefan.p','abc12345','stefan.popovic@example.com','Stefan','Popović','1988-09-12','2024-12-21 20:30:15','PUTNIK',0),(7,'jelena.k','jelena2024','jelena.kovac@example.com','Jelena','Kovač','1993-06-18','2024-12-21 21:10:05','PUTNIK',0),(8,'nikola.t','tesla987','nikola.tesla@example.com','Nikola','Tesla','1976-01-07','2024-12-21 22:45:30','PUTNIK',0),(9,'dragana.b','dragon789','dragana.bogdanovic@example.com','Dragana','Bogdanović','1992-04-22','2024-12-21 23:15:40','PUTNIK',1),(10,'aleksandar.v','alex123','aleksandar.vukovsic@example.com','Aleksandar','Vuković','1998-11-30','2024-12-22 00:30:00','PUTNIK',1),(11,'milos','milos123','aleksandar.vukovsic@example.com','Aleksandar','Vuković','1998-11-30','2024-12-22 00:30:00','PUTNIK',1),(12,'katarina.m','katy2024','katarina.milosevic@example.com','Katarina','Milošević','1994-08-15','2024-12-22 01:20:50','PUTNIK',0);
/*!40000 ALTER TABLE `korisnici` ENABLE KEYS */;
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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lokacije`
--

LOCK TABLES `lokacije` WRITE;
/*!40000 ALTER TABLE `lokacije` DISABLE KEYS */;
INSERT INTO `lokacije` VALUES (1,'Beograd','Srbija','Evropa'),(2,'New York','Sjedinjene Američke Države','Amerika'),(3,'Tokio','Japan','Azija'),(4,'Sidnej','Australija','Australija'),(5,'Kairo','Egipat','Afrika'),(6,'Buenos Aires','Argentina','Amerika'),(7,'Pariz','Francuska','Evropa'),(8,'Kejp Taun','Južnoafrička Republika','Afrika'),(9,'Auckland','Novi Zeland','Okeanija'),(10,'McMurdo Station','Antarktik','Antartika');
/*!40000 ALTER TABLE `lokacije` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-07-09  3:17:32
