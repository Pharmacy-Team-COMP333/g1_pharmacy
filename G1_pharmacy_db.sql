-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: g1_pharmcy
-- ------------------------------------------------------
-- Server version	8.0.37

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
-- Table structure for table `bill`
--

DROP TABLE IF EXISTS `bill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bill` (
  `billID` int NOT NULL AUTO_INCREMENT,
  `totalPrice` decimal(10,2) NOT NULL,
  `profit` decimal(10,2) NOT NULL,
  `billType` varchar(50) NOT NULL,
  `orderID` int NOT NULL,
  `paymentMethodID` int NOT NULL,
  PRIMARY KEY (`billID`),
  UNIQUE KEY `orderID` (`orderID`),
  KEY `paymentMethodID` (`paymentMethodID`),
  CONSTRAINT `bill_ibfk_1` FOREIGN KEY (`orderID`) REFERENCES `order_table` (`orderID`),
  CONSTRAINT `bill_ibfk_2` FOREIGN KEY (`paymentMethodID`) REFERENCES `payment_method` (`paymentMethodID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bill`
--

LOCK TABLES `bill` WRITE;
/*!40000 ALTER TABLE `bill` DISABLE KEYS */;
INSERT INTO `bill` VALUES (1,50.00,10.00,'Prescription',1,3),(3,75.00,15.00,'Prescription',3,2);
/*!40000 ALTER TABLE `bill` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `cat_id` int NOT NULL AUTO_INCREMENT,
  `categores_name` varchar(255) NOT NULL,
  `Description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`cat_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'Antibiotics','Medications used to treat bacterial infections'),(2,'Pain Relievers','Medications used to relieve pain'),(3,'Antidepressants','Medications used to treat depression');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `company`
--

DROP TABLE IF EXISTS `company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `company` (
  `companyID` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`companyID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `company`
--

LOCK TABLES `company` WRITE;
/*!40000 ALTER TABLE `company` DISABLE KEYS */;
INSERT INTO `company` VALUES (1,'Pfizer','1234567890','123 Main St, New York, NY 10001'),(2,'Johnson & Johnson','9876543210','456 Oak Rd, New Brunswick, NJ 08901'),(3,'Novartis','5551234567','789 Maple Ave, Basel, Switzerland');
/*!40000 ALTER TABLE `company` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contrect_employee`
--

DROP TABLE IF EXISTS `contrect_employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `contrect_employee` (
  `id` int NOT NULL,
  `amount_paid` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `contrect_employee_ibfk_1` FOREIGN KEY (`id`) REFERENCES `employee` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contrect_employee`
--

LOCK TABLES `contrect_employee` WRITE;
/*!40000 ALTER TABLE `contrect_employee` DISABLE KEYS */;
INSERT INTO `contrect_employee` VALUES (3,5000);
/*!40000 ALTER TABLE `contrect_employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer` (
  `customerID` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `contactInfo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`customerID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (1,'John Doe','123 Main St, Anytown, CA 12345'),(2,'Jane Smith','jsmith@email.com'),(3,'Bob Johnson','555-1234');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `disease`
--

DROP TABLE IF EXISTS `disease`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `disease` (
  `diseaseID` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `treatment` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`diseaseID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `disease`
--

LOCK TABLES `disease` WRITE;
/*!40000 ALTER TABLE `disease` DISABLE KEYS */;
INSERT INTO `disease` VALUES (1,'Hypertension','High blood pressure','Lifestyle changes, medications'),(2,'Diabetes','Chronic condition affecting blood sugar levels','Insulin, oral medications'),(3,'Asthma','Chronic lung disease','Inhalers, corticosteroids');
/*!40000 ALTER TABLE `disease` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `doctor`
--

DROP TABLE IF EXISTS `doctor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `doctor` (
  `doctorID` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `specialization` varchar(255) NOT NULL,
  `phoneNumber` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`doctorID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `doctor`
--

LOCK TABLES `doctor` WRITE;
/*!40000 ALTER TABLE `doctor` DISABLE KEYS */;
INSERT INTO `doctor` VALUES (1,'Dr. Smith','Cardiology','555-1234'),(2,'Dr. Johnson','Endocrinology','555-5678'),(3,'Dr. Williams','Pulmonology','555-9012');
/*!40000 ALTER TABLE `doctor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dosage_form`
--

DROP TABLE IF EXISTS `dosage_form`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dosage_form` (
  `dosageFormID` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`dosageFormID`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dosage_form`
--

LOCK TABLES `dosage_form` WRITE;
/*!40000 ALTER TABLE `dosage_form` DISABLE KEYS */;
INSERT INTO `dosage_form` VALUES (2,'Capsule'),(3,'Syrup'),(1,'Tablet');
/*!40000 ALTER TABLE `dosage_form` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee` (
  `id` int NOT NULL AUTO_INCREMENT,
  `employee_name` varchar(255) NOT NULL,
  `birthday` date NOT NULL,
  `date_of_employment` date NOT NULL,
  `emp_password` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES (1,'sami','1990-05-15','2018-03-01','password123'),(2,'Bob Thompson','2002-05-28','2020-07-04','sami1234'),(3,'malak','1992-07-03','2020-02-01','mypassword');
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hourly_employee`
--

DROP TABLE IF EXISTS `hourly_employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hourly_employee` (
  `id` int NOT NULL,
  `work_hours` double DEFAULT NULL,
  `hour_price` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `hourly_employee_ibfk_1` FOREIGN KEY (`id`) REFERENCES `employee` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hourly_employee`
--

LOCK TABLES `hourly_employee` WRITE;
/*!40000 ALTER TABLE `hourly_employee` DISABLE KEYS */;
INSERT INTO `hourly_employee` VALUES (1,40,25.5),(2,40,33);
/*!40000 ALTER TABLE `hourly_employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `insurance_company`
--

DROP TABLE IF EXISTS `insurance_company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `insurance_company` (
  `insuranceCompanyID` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `discountPercentage` decimal(5,2) NOT NULL,
  PRIMARY KEY (`insuranceCompanyID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `insurance_company`
--

LOCK TABLES `insurance_company` WRITE;
/*!40000 ALTER TABLE `insurance_company` DISABLE KEYS */;
INSERT INTO `insurance_company` VALUES (1,'Blue Cross',0.20),(2,'Aetna',0.15),(3,'UnitedHealthcare',0.25);
/*!40000 ALTER TABLE `insurance_company` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `insurance_customer`
--

DROP TABLE IF EXISTS `insurance_customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `insurance_customer` (
  `customerID` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `identificationNumber` varchar(50) NOT NULL,
  `insuranceCompanyID` int NOT NULL,
  PRIMARY KEY (`customerID`),
  UNIQUE KEY `identificationNumber` (`identificationNumber`),
  KEY `insuranceCompanyID` (`insuranceCompanyID`),
  CONSTRAINT `insurance_customer_ibfk_1` FOREIGN KEY (`insuranceCompanyID`) REFERENCES `insurance_company` (`insuranceCompanyID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `insurance_customer`
--

LOCK TABLES `insurance_customer` WRITE;
/*!40000 ALTER TABLE `insurance_customer` DISABLE KEYS */;
INSERT INTO `insurance_customer` VALUES (1,'John Doe','123456789',1),(2,'Jane Smith','987654321',2),(3,'Bob Johnson','456789123',3);
/*!40000 ALTER TABLE `insurance_customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item`
--

DROP TABLE IF EXISTS `item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `item` (
  `itemID` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `barcode` varchar(50) DEFAULT NULL,
  `quantity` int NOT NULL DEFAULT '0',
  `description` varchar(255) DEFAULT NULL,
  `salePrice` decimal(10,2) NOT NULL,
  `originalPrice` decimal(10,2) NOT NULL,
  `expiryDate` date DEFAULT NULL,
  `companyID` int NOT NULL,
  `cat_id` int NOT NULL,
  `storageMethodID` int NOT NULL,
  `dosageFormID` int NOT NULL,
  PRIMARY KEY (`itemID`),
  UNIQUE KEY `barcode` (`barcode`),
  KEY `companyID` (`companyID`),
  KEY `cat_id` (`cat_id`),
  KEY `storageMethodID` (`storageMethodID`),
  KEY `dosageFormID` (`dosageFormID`),
  CONSTRAINT `item_ibfk_1` FOREIGN KEY (`companyID`) REFERENCES `company` (`companyID`),
  CONSTRAINT `item_ibfk_2` FOREIGN KEY (`cat_id`) REFERENCES `category` (`cat_id`),
  CONSTRAINT `item_ibfk_3` FOREIGN KEY (`storageMethodID`) REFERENCES `storage_method` (`storageMethodID`),
  CONSTRAINT `item_ibfk_4` FOREIGN KEY (`dosageFormID`) REFERENCES `dosage_form` (`dosageFormID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item`
--

LOCK TABLES `item` WRITE;
/*!40000 ALTER TABLE `item` DISABLE KEYS */;
INSERT INTO `item` VALUES (1,'Amoxicillin','123456789012',100,'Antibiotic for bacterial infections',10.99,8.99,'2025-12-31',1,1,1,1),(2,'Ibuprofen','234567890123',50,'Pain reliever',5.99,4.99,'2024-06-30',2,2,2,2),(3,'Fluoxetine','345678901234',75,'Antidepressant medication',20.00,15.00,'2026-03-31',3,3,1,1);
/*!40000 ALTER TABLE `item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item_side_effect`
--

DROP TABLE IF EXISTS `item_side_effect`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `item_side_effect` (
  `itemID` int NOT NULL,
  `sideEffectID` int NOT NULL,
  PRIMARY KEY (`itemID`,`sideEffectID`),
  KEY `sideEffectID` (`sideEffectID`),
  CONSTRAINT `item_side_effect_ibfk_1` FOREIGN KEY (`itemID`) REFERENCES `item` (`itemID`),
  CONSTRAINT `item_side_effect_ibfk_2` FOREIGN KEY (`sideEffectID`) REFERENCES `side_effect` (`sideEffectID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item_side_effect`
--

LOCK TABLES `item_side_effect` WRITE;
/*!40000 ALTER TABLE `item_side_effect` DISABLE KEYS */;
INSERT INTO `item_side_effect` VALUES (1,1),(3,1),(1,2),(2,3);
/*!40000 ALTER TABLE `item_side_effect` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_item`
--

DROP TABLE IF EXISTS `order_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_item` (
  `orderID` int NOT NULL,
  `itemID` int NOT NULL,
  `quantity` int NOT NULL DEFAULT '1',
  `fullSalePrice` decimal(10,2) NOT NULL,
  `fullOriginalPrice` decimal(10,2) NOT NULL,
  PRIMARY KEY (`orderID`,`itemID`),
  KEY `itemID` (`itemID`),
  CONSTRAINT `order_item_ibfk_1` FOREIGN KEY (`orderID`) REFERENCES `order_table` (`orderID`),
  CONSTRAINT `order_item_ibfk_2` FOREIGN KEY (`itemID`) REFERENCES `item` (`itemID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_item`
--

LOCK TABLES `order_item` WRITE;
/*!40000 ALTER TABLE `order_item` DISABLE KEYS */;
INSERT INTO `order_item` VALUES (1,1,30,329.70,269.70),(2,2,10,59.90,49.90),(3,3,60,1200.00,900.00);
/*!40000 ALTER TABLE `order_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_table`
--

DROP TABLE IF EXISTS `order_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_table` (
  `orderID` int NOT NULL AUTO_INCREMENT,
  `orderDate` date NOT NULL,
  `orderType` varchar(50) NOT NULL,
  `employeeID` int NOT NULL,
  `customerID` int NOT NULL,
  PRIMARY KEY (`orderID`),
  KEY `employeeID` (`employeeID`),
  KEY `customerID` (`customerID`),
  CONSTRAINT `order_table_ibfk_1` FOREIGN KEY (`employeeID`) REFERENCES `employee` (`id`),
  CONSTRAINT `order_table_ibfk_2` FOREIGN KEY (`customerID`) REFERENCES `customer` (`customerID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_table`
--

LOCK TABLES `order_table` WRITE;
/*!40000 ALTER TABLE `order_table` DISABLE KEYS */;
INSERT INTO `order_table` VALUES (1,'2023-04-01','Prescription',1,1),(2,'2023-04-15','Over-the-counter',2,2),(3,'2023-05-01','Prescription',3,3);
/*!40000 ALTER TABLE `order_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment_method`
--

DROP TABLE IF EXISTS `payment_method`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payment_method` (
  `paymentMethodID` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`paymentMethodID`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment_method`
--

LOCK TABLES `payment_method` WRITE;
/*!40000 ALTER TABLE `payment_method` DISABLE KEYS */;
INSERT INTO `payment_method` VALUES (1,'Cash'),(2,'Credit Card'),(3,'Insurance');
/*!40000 ALTER TABLE `payment_method` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prescription`
--

DROP TABLE IF EXISTS `prescription`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `prescription` (
  `prescriptionID` int NOT NULL AUTO_INCREMENT,
  `prescriptionDate` date NOT NULL,
  `dosage` varchar(255) NOT NULL,
  `instructions` varchar(255) NOT NULL,
  `doctorID` int NOT NULL,
  `customerID` int NOT NULL,
  PRIMARY KEY (`prescriptionID`),
  KEY `doctorID` (`doctorID`),
  KEY `customerID` (`customerID`),
  CONSTRAINT `prescription_ibfk_1` FOREIGN KEY (`doctorID`) REFERENCES `doctor` (`doctorID`),
  CONSTRAINT `prescription_ibfk_2` FOREIGN KEY (`customerID`) REFERENCES `customer` (`customerID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prescription`
--

LOCK TABLES `prescription` WRITE;
/*!40000 ALTER TABLE `prescription` DISABLE KEYS */;
INSERT INTO `prescription` VALUES (1,'2023-04-01','1 tablet twice daily','Take with food',1,1),(2,'2023-04-15','10 mL every 6 hours','Shake well before use',2,2),(3,'2023-05-01','1 capsule daily','Take on an empty stomach',3,3);
/*!40000 ALTER TABLE `prescription` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prescription_item`
--

DROP TABLE IF EXISTS `prescription_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `prescription_item` (
  `prescriptionID` int NOT NULL,
  `itemID` int NOT NULL,
  `dosage` varchar(255) NOT NULL,
  `duration` varchar(255) NOT NULL,
  PRIMARY KEY (`prescriptionID`,`itemID`),
  KEY `itemID` (`itemID`),
  CONSTRAINT `prescription_item_ibfk_1` FOREIGN KEY (`prescriptionID`) REFERENCES `prescription` (`prescriptionID`),
  CONSTRAINT `prescription_item_ibfk_2` FOREIGN KEY (`itemID`) REFERENCES `item` (`itemID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prescription_item`
--

LOCK TABLES `prescription_item` WRITE;
/*!40000 ALTER TABLE `prescription_item` DISABLE KEYS */;
INSERT INTO `prescription_item` VALUES (1,1,'1 tablet twice daily','10 days'),(2,2,'10 mL every 6 hours','5 days'),(3,3,'1 capsule daily','30 days');
/*!40000 ALTER TABLE `prescription_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `side_effect`
--

DROP TABLE IF EXISTS `side_effect`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `side_effect` (
  `sideEffectID` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `severity` varchar(50) NOT NULL,
  PRIMARY KEY (`sideEffectID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `side_effect`
--

LOCK TABLES `side_effect` WRITE;
/*!40000 ALTER TABLE `side_effect` DISABLE KEYS */;
INSERT INTO `side_effect` VALUES (1,'Headache','Mild'),(2,'Nausea','Moderate'),(3,'Rash','Severe');
/*!40000 ALTER TABLE `side_effect` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `storage_method`
--

DROP TABLE IF EXISTS `storage_method`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `storage_method` (
  `storageMethodID` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`storageMethodID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `storage_method`
--

LOCK TABLES `storage_method` WRITE;
/*!40000 ALTER TABLE `storage_method` DISABLE KEYS */;
INSERT INTO `storage_method` VALUES (1,'Refrigerated','Store between 2-8°C'),(2,'Room Temperature','Store below 25°C'),(3,'Frozen','Store below 0°C');
/*!40000 ALTER TABLE `storage_method` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-05-14 12:01:36
