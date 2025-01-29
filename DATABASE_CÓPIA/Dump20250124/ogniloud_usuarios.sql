CREATE DATABASE  IF NOT EXISTS `ogniloud` /*!40100 DEFAULT CHARACTER SET utf8mb3 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `ogniloud`;
-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: localhost    Database: ogniloud
-- ------------------------------------------------------
-- Server version	8.0.40

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
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios` (
  `idusuarios` int NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `senha` varchar(255) NOT NULL,
  `tipo` enum('aluno','professor') NOT NULL,
  `id_aluno` int DEFAULT NULL,
  `id_professor` int DEFAULT NULL,
  `foto_perfil` varchar(255) DEFAULT NULL,
  `foto_fundo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idusuarios`),
  UNIQUE KEY `email` (`email`),
  KEY `fk_usuarios_alunos` (`id_aluno`),
  KEY `fk_usuarios_professor` (`id_professor`),
  CONSTRAINT `fk_usuarios_alunos` FOREIGN KEY (`id_aluno`) REFERENCES `alunos` (`idalunos`) ON DELETE CASCADE,
  CONSTRAINT `fk_usuarios_professor` FOREIGN KEY (`id_professor`) REFERENCES `professor` (`idprofessor`) ON DELETE CASCADE,
  CONSTRAINT `usuarios_chk_1` CHECK ((((`tipo` = _utf8mb3'aluno') and (`id_aluno` is not null) and (`id_professor` is null)) or ((`tipo` = _utf8mb3'professor') and (`id_professor` is not null) and (`id_aluno` is null))))
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (1,'pharel@gmail.com','123456','aluno',4,NULL,'C:/Users/JeanM/OneDrive/Documentos/OGNILOUD/OGNILOUD/src/resources/images/default_profile.png','C:/Users/JeanM/OneDrive/Documentos/OGNILOUD/OGNILOUD/src/resources/images/Profile/Pata_profile.png'),(2,'Jake@gmail.com','123456','professor',NULL,1,'C:/Users/JeanM/OneDrive/Documentos/OGNILOUD/OGNILOUD/src/resources/images/default_profile.png','C:/Users/JeanM/OneDrive/Documentos/OGNILOUD/OGNILOUD/src/resources/images/Profile_Professor/Duck_profile.png'),(5,'diogobatista@gmail.com','neymar','aluno',7,NULL,'C:/Users/JeanM/OneDrive/Documentos/OGNILOUD/OGNILOUD/src/resources/images/default_profile.png','C:/Users/JeanM/OneDrive/Documentos/OGNILOUD/OGNILOUD/src/resources/images/background_aluno.png'),(6,'leticia@gmail.com','123456','aluno',8,NULL,'C:/Users/JeanM/OneDrive/Documentos/OGNILOUD/OGNILOUD/src/resources/images/default_profile.png','C:/Users/JeanM/OneDrive/Documentos/OGNILOUD/OGNILOUD/src/resources/images/background_aluno.png'),(7,'Apsikald@gmail.com','123456','aluno',9,NULL,NULL,NULL);
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-01-28 23:20:15
