-- MySQL dump 10.13  Distrib 9.7.1, for Win64 (x86_64)
--
-- Host: localhost    Database: skillhub
-- ------------------------------------------------------
-- Server version	9.7.1

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
-- Table structure for table `books`
--

DROP TABLE IF EXISTS `books`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `books` (
  `book_id` bigint NOT NULL AUTO_INCREMENT,
  `author` varchar(255) NOT NULL,
  `availability` varchar(255) NOT NULL,
  `book_condition` varchar(255) NOT NULL,
  `subject` varchar(255) NOT NULL,
  `title` varchar(255) NOT NULL,
  `student_id` bigint DEFAULT NULL,
  PRIMARY KEY (`book_id`),
  KEY `FKogot0942ocpdhd9c3bi1fbtbk` (`student_id`),
  CONSTRAINT `FKogot0942ocpdhd9c3bi1fbtbk` FOREIGN KEY (`student_id`) REFERENCES `students` (`student_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `books`
--

LOCK TABLES `books` WRITE;
/*!40000 ALTER TABLE `books` DISABLE KEYS */;
INSERT INTO `books` VALUES (1,'Herbert Schildt','Available','Good','Programming','Java Programming',2);
/*!40000 ALTER TABLE `books` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `learning_requests`
--

DROP TABLE IF EXISTS `learning_requests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `learning_requests` (
  `request_id` bigint NOT NULL AUTO_INCREMENT,
  `message` varchar(300) DEFAULT NULL,
  `status` enum('ACCEPTED','COMPLETED','PENDING','REJECTED') DEFAULT NULL,
  `learner_id` bigint DEFAULT NULL,
  `mentor_id` bigint DEFAULT NULL,
  `skill_id` bigint DEFAULT NULL,
  PRIMARY KEY (`request_id`),
  KEY `FK9l143mj9cnhx7vikwddhvo3t0` (`learner_id`),
  KEY `FK36m2qdxthsfrr8agn7ef5qoof` (`mentor_id`),
  KEY `FKtnqk6xe41vumjb1834ido9yvh` (`skill_id`),
  CONSTRAINT `FK36m2qdxthsfrr8agn7ef5qoof` FOREIGN KEY (`mentor_id`) REFERENCES `students` (`student_id`),
  CONSTRAINT `FK9l143mj9cnhx7vikwddhvo3t0` FOREIGN KEY (`learner_id`) REFERENCES `students` (`student_id`),
  CONSTRAINT `FKtnqk6xe41vumjb1834ido9yvh` FOREIGN KEY (`skill_id`) REFERENCES `skills` (`skill_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `learning_requests`
--

LOCK TABLES `learning_requests` WRITE;
/*!40000 ALTER TABLE `learning_requests` DISABLE KEYS */;
INSERT INTO `learning_requests` VALUES (1,'Can you teach me Java?','COMPLETED',4,2,2);
/*!40000 ALTER TABLE `learning_requests` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ratings`
--

DROP TABLE IF EXISTS `ratings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ratings` (
  `rating_id` bigint NOT NULL AUTO_INCREMENT,
  `feedback` varchar(500) DEFAULT NULL,
  `rating` int NOT NULL,
  `learner_id` bigint DEFAULT NULL,
  `mentor_id` bigint DEFAULT NULL,
  `request_id` bigint DEFAULT NULL,
  PRIMARY KEY (`rating_id`),
  UNIQUE KEY `UKg3stydc9blpso0ehkkrw36tnx` (`request_id`),
  KEY `FK460gk2ny4ecndpp8y5yn56gpa` (`learner_id`),
  KEY `FK1nqgxeont14d5sn40dy0drdp` (`mentor_id`),
  CONSTRAINT `FK1nqgxeont14d5sn40dy0drdp` FOREIGN KEY (`mentor_id`) REFERENCES `students` (`student_id`),
  CONSTRAINT `FK460gk2ny4ecndpp8y5yn56gpa` FOREIGN KEY (`learner_id`) REFERENCES `students` (`student_id`),
  CONSTRAINT `FK4yw0jrwirhilj445e1prs83uw` FOREIGN KEY (`request_id`) REFERENCES `learning_requests` (`request_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ratings`
--

LOCK TABLES `ratings` WRITE;
/*!40000 ALTER TABLE `ratings` DISABLE KEYS */;
INSERT INTO `ratings` VALUES (1,'Excellent mentor',5,4,2,1);
/*!40000 ALTER TABLE `ratings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `skills`
--

DROP TABLE IF EXISTS `skills`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `skills` (
  `skill_id` bigint NOT NULL AUTO_INCREMENT,
  `category` varchar(255) NOT NULL,
  `skill_name` varchar(255) NOT NULL,
  PRIMARY KEY (`skill_id`),
  UNIQUE KEY `UKqdkr64bxreqdbn6b4k9vumvp3` (`skill_name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `skills`
--

LOCK TABLES `skills` WRITE;
/*!40000 ALTER TABLE `skills` DISABLE KEYS */;
INSERT INTO `skills` VALUES (2,'Programming','Java');
/*!40000 ALTER TABLE `skills` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student_skills`
--

DROP TABLE IF EXISTS `student_skills`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student_skills` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `level` varchar(255) NOT NULL,
  `skill_id` bigint DEFAULT NULL,
  `student_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKdyl6ffxg89qebmlpcg2x3fnbw` (`skill_id`),
  KEY `FKovs9ju1ccjg7kq2m3tct9oh1y` (`student_id`),
  CONSTRAINT `FKdyl6ffxg89qebmlpcg2x3fnbw` FOREIGN KEY (`skill_id`) REFERENCES `skills` (`skill_id`),
  CONSTRAINT `FKovs9ju1ccjg7kq2m3tct9oh1y` FOREIGN KEY (`student_id`) REFERENCES `students` (`student_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student_skills`
--

LOCK TABLES `student_skills` WRITE;
/*!40000 ALTER TABLE `student_skills` DISABLE KEYS */;
INSERT INTO `student_skills` VALUES (2,'Advanced',2,4);
/*!40000 ALTER TABLE `student_skills` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `students`
--

DROP TABLE IF EXISTS `students`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `students` (
  `student_id` bigint NOT NULL AUTO_INCREMENT,
  `department` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `year` int NOT NULL,
  PRIMARY KEY (`student_id`),
  UNIQUE KEY `UKe2rndfrsx22acpq2ty1caeuyw` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `students`
--

LOCK TABLES `students` WRITE;
/*!40000 ALTER TABLE `students` DISABLE KEYS */;
INSERT INTO `students` VALUES (2,'CSE','rahul@gmail.com','Rahul','1234',3),(4,'IT','arun@gmail.com','Arun','1234',2);
/*!40000 ALTER TABLE `students` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-07-12 23:04:03
