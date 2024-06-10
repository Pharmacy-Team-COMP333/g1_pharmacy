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
  `order_id` int NOT NULL,
  `order_date` date DEFAULT NULL,
  `full_price` double DEFAULT NULL,
  `profits` double DEFAULT NULL,
  `bill_type` varchar(32) DEFAULT NULL,
  `emp_id` int DEFAULT NULL,
  PRIMARY KEY (`order_id`),
  KEY `emp_id` (`emp_id`),
  CONSTRAINT `bill_ibfk_1` FOREIGN KEY (`emp_id`) REFERENCES `employee` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `bill_ibfk_2` FOREIGN KEY (`order_id`) REFERENCES `orderes` (`order_id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bill`
--

LOCK TABLES `bill` WRITE;
/*!40000 ALTER TABLE `bill` DISABLE KEYS */;
INSERT INTO `bill` VALUES (1,'2023-04-01',329.7,60,'Prescription',1),(2,'2023-04-15',59.9,10,'Over-the-counter',2),(3,'2023-05-01',1200,300,'Prescription',3);
/*!40000 ALTER TABLE `bill` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cashorder`
--

DROP TABLE IF EXISTS `cashorder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cashorder` (
  `order_id` int NOT NULL,
  `order_date` date DEFAULT NULL,
  PRIMARY KEY (`order_id`),
  CONSTRAINT `cashorder_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orderes` (`order_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cashorder`
--

LOCK TABLES `cashorder` WRITE;
/*!40000 ALTER TABLE `cashorder` DISABLE KEYS */;
INSERT INTO `cashorder` VALUES (2,'2023-04-15');
/*!40000 ALTER TABLE `cashorder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categories` (
  `cat_id` int NOT NULL AUTO_INCREMENT,
  `categories_name` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`cat_id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (1,'Antibiotics','Medications used to treat bacterial infections'),(2,'Pain Relievers','Medications used to relieve pain'),(3,'Antidepressants','Medications used to treat depression'),(4,'Antibiotics','Medications used to treat bacterial infections'),(5,'Pain Relievers','Medications used to relieve pain'),(6,'Antidepressants','Medications used to treat depression'),(7,'Antihistamines','Medications used to treat allergies'),(8,'Antihypertensives','Medications used to treat highblood pressure'),(9,'Antidiabetics','Medications used to treat diabetes'),(10,'Anticoagulants','Medications used to prevent blood clots'),(11,'Antiepileptics','Medications used to treat seizures'),(12,'Antifungals','Medications used to treat fungal infections'),(13,'Antivirals','Medications used to treat viral infections'),(14,'Vitamins and Supplements','Dietary supplements for various purposes'),(15,'Herbals and Botanicals','Plant-based remedies and supplements'),(16,'Skincare','Products for skin care and treatment'),(17,'Baby Care','Products for infant and baby care'),(18,'Personal Care','Products for personal hygiene and grooming'),(19,'Dental Care','Products for oral and dental care'),(20,'Home Health Care','Products for home health care and monitoring'),(21,'Pet Care','Products for pet health and wellness'),(22,'Sports Nutrition','Supplements and products for athletic performance'),(23,'Weight Management','Products for weight loss or gain');
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contract_employee`
--

DROP TABLE IF EXISTS `contract_employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `contract_employee` (
  `id` int NOT NULL,
  `amount_paid` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `contract_employee_ibfk_1` FOREIGN KEY (`id`) REFERENCES `employee` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contract_employee`
--

LOCK TABLES `contract_employee` WRITE;
/*!40000 ALTER TABLE `contract_employee` DISABLE KEYS */;
INSERT INTO `contract_employee` VALUES (3,5000),(4,6500),(7,4800),(10,7200);
/*!40000 ALTER TABLE `contract_employee` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (1,'SAMI','123 Main St, Anytown, CA 12345'),(2,'Jane Smith','jsmith@email.com'),(3,'Bob Johnson','555-1234'),(4,'John Doe','123 Main St, Anytown, CA 12345'),(5,'Jane Smith','jsmith@email.com'),(6,'Bob Johnson','555-1234'),(7,'Michael Brown','456 Oak Rd, Sometown, NY 67890'),(8,'Emily Davis','edavis@email.com'),(9,'David Wilson','789-0123'),(10,'Jessica Thompson','246 Maple Ln, Anothertown, TX 24680'),(11,'Christopher Taylor','ctaylor@email.com'),(12,'Ashley Anderson','159-7530'),(13,'Matthew Thomas','753 Pine Ave, Mytown, FL 19283'),(14,'Sarah Johnson','456 Oak St, Cityville, CA 54321'),(15,'Michael Lee','mlee@email.com'),(16,'Emily Davis','789-0123'),(17,'David Thompson','159 Maple Ln, Townsville, TX 67890'),(18,'Jessica Wilson','jwilson@email.com'),(19,'Christopher Brown','246-5678'),(20,'Ashley Taylor','753 Pine Ave, Villagetown, FL 24680'),(21,'Matthew Anderson','manderson@email.com'),(22,'Jennifer Thomas','159-7890'),(23,'Daniel Moore','987 Elm Rd, Countyville, NY 09876');
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
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `disease`
--

LOCK TABLES `disease` WRITE;
/*!40000 ALTER TABLE `disease` DISABLE KEYS */;
INSERT INTO `disease` VALUES (1,'Hypertension','High blood pressure','Lifestyle changes, medications'),(3,'Asthma','Chronic lung disease','Inhalers, corticosteroids'),(4,'Hypertension','High blood pressure','Lifestyle changes, medications'),(5,'Diabetes','Chronic condition affecting blood sugar levels','Insulin, oral medications'),(6,'Asthma','Chronic lung disease','Inhalers, corticosteroids'),(7,'Arthritis','Joint inflammation and pain','Pain relievers, anti-inflammatory drugs'),(8,'Depression','Mood disorder','Antidepressants, therapy'),(9,'Anxiety','Excessive worry and fear','Therapy, anti-anxiety medications'),(10,'Migraine','Severe headache','Pain relievers, preventive medications'),(11,'Allergies','Hypersensitivity reactions','Antihistamines, avoidance of triggers'),(12,'Epilepsy','Neurological disorder causing seizures','Anticonvulsants, lifestyle changes'),(13,'Insomnia','Difficulty falling or staying asleep','Sleep medications, lifestyle changes');
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
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `doctor`
--

LOCK TABLES `doctor` WRITE;
/*!40000 ALTER TABLE `doctor` DISABLE KEYS */;
INSERT INTO `doctor` VALUES (1,'Dr. Smith','Cardiology','555-1234'),(2,'Dr. Johnson','Endocrinology','555-5678'),(3,'Dr. Williams','Pulmonology','555-9012'),(4,'sami','FDFDSFSDF','4342'),(5,'Dr. Smith','Cardiology','555-1234'),(6,'Dr. Johnson','Endocrinology','555-5678'),(7,'Dr. Williams','Pulmonology','555-9012'),(8,'Dr. Brown','Dermatology','555-3456'),(9,'Dr. Davis','Gastroenterology','555-7890'),(10,'Dr. Wilson','Neurology','555-2345'),(11,'Dr. Thompson','Oncology','555-6789'),(12,'Dr. Taylor','Pediatrics','555-0123'),(13,'Dr. Anderson','Obstetrics/Gynecology','555-4567'),(14,'Dr. Thomas','Orthopedics','555-8901');
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
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dosage_form`
--

LOCK TABLES `dosage_form` WRITE;
/*!40000 ALTER TABLE `dosage_form` DISABLE KEYS */;
INSERT INTO `dosage_form` VALUES (2,'Capsule'),(26,'Cream'),(23,'Injection'),(27,'Ointment'),(25,'Patch'),(28,'Solution'),(24,'Suppository'),(29,'Suspension'),(3,'Syrup'),(1,'Tablet');
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
  `employee_name` varchar(60) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `date_of_employment` date DEFAULT NULL,
  `emp_password` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES (1,'Alice Williams','1990-05-15','2018-03-01','password123'),(2,'Bob Thompson','1985-11-22','2016-09-15','securepass'),(3,'sami','2001-06-21','2016-09-15','sami'),(4,'Charlie Roberts','1992-07-03','2020-02-01','mypassword'),(5,'Nathera Alwan','1985-01-15','2010-05-16','admin'),(6,'Ahlam Eldeen Asfour','1992-01-15','2018-05-16','root'),(7,'Mera Ahmad Shekh','1997-08-26','2020-08-14','root1'),(8,'Ahmad Said Ammar','1995-04-21','2021-08-14','root2'),(9,'Lama Naser Hammad','1990-05-26','2019-01-30','root3'),(10,'Malak Raed Hannon','1980-08-26','2012-12-14','root4'),(11,'Manal Awwad Salem','1992-07-04','2018-01-25','root5'),(12,'Raed Mohammad Ayman','1995-08-26','2020-08-14','root6'),(13,'Ibrahim Ahmad Asfour','1989-12-26','2015-02-14','root7'),(14,'Ahmad Malik Alwan','1991-08-26','2018-07-14','root8'),(15,'Alice Williams','1990-05-15','2018-03-01','password123'),(16,'Bob Thompson','1985-11-22','2016-09-15','securepass'),(17,'sami','2001-06-21','2016-09-15','admin'),(18,'Charlie Roberts','1992-07-03','2020-02-01','mypassword'),(19,'Emily Johnson','1988-03-12','2015-06-01','emily123'),(20,'David Lee','1982-09-27','2010-01-15','david456'),(21,'Sarah Thompson','1995-05-20','2019-08-01','sarah789'),(22,'Michael Davis','1990-11-03','2017-03-15','michael456'),(23,'Jessica Wilson','1993-07-18','2021-02-01','jessica123'),(24,'Christopher Brown','1987-12-25','2014-05-01','chris456'),(25,'Nathera Alwan','1985-01-15','2010-05-16','admin'),(26,'Ahlam Eldeen Asfour','1992-01-15','2018-05-16','root'),(27,'Mera Ahmad Shekh','1997-08-26','2020-08-14','root1'),(28,'Ahmad Said Ammar','1995-04-21','2021-08-14','root2'),(29,'Lama Naser Hammad','1990-05-26','2019-01-30','root3'),(30,'Malak Raed Hannon','1980-08-26','2012-12-14','root4'),(31,'Manal Awwad Salem','1992-07-04','2018-01-25','root5'),(32,'Raed Mohammad Ayman','1995-08-26','2020-08-14','root6'),(33,'Ibrahim Ahmad Asfour','1989-12-26','2015-02-14','root7'),(34,'Ahmad Malik Alwan','1991-08-26','2018-07-14','root8');
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `entity_manager`
--

DROP TABLE IF EXISTS `entity_manager`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `entity_manager` (
  `managerID` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `password` varchar(30) NOT NULL,
  `date_created` date NOT NULL,
  PRIMARY KEY (`managerID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `entity_manager`
--

LOCK TABLES `entity_manager` WRITE;
/*!40000 ALTER TABLE `entity_manager` DISABLE KEYS */;
INSERT INTO `entity_manager` VALUES (1,'sami','admin','2024-06-04'),(2,'nathera alwan','admin','2024-06-04');
/*!40000 ALTER TABLE `entity_manager` ENABLE KEYS */;
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
INSERT INTO `hourly_employee` VALUES (1,40,25.5),(2,30,20.75),(5,35,18),(6,45,22.5),(8,32,19.75),(9,40,24);
/*!40000 ALTER TABLE `hourly_employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `insurance`
--

DROP TABLE IF EXISTS `insurance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `insurance` (
  `customerID` int NOT NULL,
  `customerName` varchar(32) DEFAULT NULL,
  `insurance_companyName` varchar(32) NOT NULL,
  PRIMARY KEY (`customerID`),
  KEY `insurance_companyName` (`insurance_companyName`),
  CONSTRAINT `insurance_ibfk_1` FOREIGN KEY (`insurance_companyName`) REFERENCES `insurance_company` (`insurance_companyName`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `insurance`
--

LOCK TABLES `insurance` WRITE;
/*!40000 ALTER TABLE `insurance` DISABLE KEYS */;
INSERT INTO `insurance` VALUES (1,'sami','Blue Cross'),(2,'Jane Smith','Aetna'),(3,'Bob Johnson','UnitedHealthcare'),(4,'sami','Aetna'),(5,'sami','Blue Cross'),(6,'David Wilson','Kaiser Permanente'),(7,'Jessica Thompson','Blue Cross'),(8,'Christopher Taylor','Aetna'),(9,'Ashley Anderson','UnitedHealthcare'),(10,'Matthew Thomas','Cigna');
/*!40000 ALTER TABLE `insurance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `insurance_company`
--

DROP TABLE IF EXISTS `insurance_company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `insurance_company` (
  `insurance_companyName` varchar(32) NOT NULL,
  `insurance_companyDiscount` int DEFAULT NULL,
  `numberOfCustomer` int DEFAULT '0',
  PRIMARY KEY (`insurance_companyName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `insurance_company`
--

LOCK TABLES `insurance_company` WRITE;
/*!40000 ALTER TABLE `insurance_company` DISABLE KEYS */;
INSERT INTO `insurance_company` VALUES ('Aetna',15,3),('Blue Cross',20,1),('Cigna',18,0),('Humana',22,0),('Kaiser Permanente',20,0),('UnitedHealthcare',25,0);
/*!40000 ALTER TABLE `insurance_company` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `insuranceorder`
--

DROP TABLE IF EXISTS `insuranceorder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `insuranceorder` (
  `customer_insurance_id` int NOT NULL,
  `order_date` date DEFAULT NULL,
  `order_id` int NOT NULL,
  PRIMARY KEY (`customer_insurance_id`,`order_id`),
  KEY `order_id` (`order_id`),
  CONSTRAINT `insuranceorder_ibfk_1` FOREIGN KEY (`customer_insurance_id`) REFERENCES `insurance` (`customerID`) ON UPDATE CASCADE,
  CONSTRAINT `insuranceorder_ibfk_2` FOREIGN KEY (`order_id`) REFERENCES `orderes` (`order_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `insuranceorder`
--

LOCK TABLES `insuranceorder` WRITE;
/*!40000 ALTER TABLE `insuranceorder` DISABLE KEYS */;
INSERT INTO `insuranceorder` VALUES (1,'2023-04-01',1),(3,'2023-05-01',3);
/*!40000 ALTER TABLE `insuranceorder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invoice`
--

DROP TABLE IF EXISTS `invoice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `invoice` (
  `quantity` int DEFAULT NULL,
  `full_sale_price` double DEFAULT NULL,
  `full_original_price` double DEFAULT NULL,
  `par_code` int NOT NULL,
  `order_id` int NOT NULL,
  PRIMARY KEY (`par_code`,`order_id`),
  KEY `order_id` (`order_id`),
  CONSTRAINT `invoice_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orderes` (`order_id`) ON UPDATE CASCADE,
  CONSTRAINT `invoice_ibfk_2` FOREIGN KEY (`par_code`) REFERENCES `item` (`par_code`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoice`
--

LOCK TABLES `invoice` WRITE;
/*!40000 ALTER TABLE `invoice` DISABLE KEYS */;
INSERT INTO `invoice` VALUES (30,329.7,269.7,123456,1),(4,10.99,8.99,123456,2),(5,10.99,8.99,123456,3),(60,1200,900,345678,3);
/*!40000 ALTER TABLE `invoice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item`
--

DROP TABLE IF EXISTS `item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `item` (
  `item_name` varchar(80) DEFAULT NULL,
  `par_code` int NOT NULL,
  `quantity` int DEFAULT NULL,
  `description` varchar(700) DEFAULT NULL,
  `sale_price` double DEFAULT NULL,
  `original_price` double DEFAULT NULL,
  `provide_company_name` varchar(70) NOT NULL,
  `cat_id` int NOT NULL,
  `exp_date` date DEFAULT NULL,
  PRIMARY KEY (`par_code`,`provide_company_name`,`cat_id`),
  KEY `provide_company_name` (`provide_company_name`),
  KEY `cat_id` (`cat_id`),
  CONSTRAINT `item_ibfk_1` FOREIGN KEY (`provide_company_name`) REFERENCES `provide_company` (`company_name`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `item_ibfk_2` FOREIGN KEY (`cat_id`) REFERENCES `categories` (`cat_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item`
--

LOCK TABLES `item` WRITE;
/*!40000 ALTER TABLE `item` DISABLE KEYS */;
INSERT INTO `item` VALUES ('Acyclovir',12345,40,'Antiviral for viral infections',19.99,16.5,'Roche',10,'2025-07-01'),('Weight Loss Supplement',101010,90,'Natural herbal supplement for weight loss',19.5,15,'Abbott',20,'2025-03-01'),('Multivitamin',111111,200,'Daily multivitamin supplement',9.99,7.99,'Pfizer',11,'2026-01-31'),('Amoxicillin',123456,91,'Antibiotic for bacterial infections',10.99,8.99,'Pfizer',1,'2025-12-31'),('Ginkgo Biloba',222222,150,'Herbal supplement for cognitive function',14.5,11.5,'Novartis',12,'2025-09-30'),('Some Medication',234567,50,'Description of the medication',15.99,12.99,'Pfizer',2,'2025-11-30'),('Retinol Cream',333333,80,'Anti-aging cream with retinol',24.99,19.99,'Bayer',13,'2024-12-31'),('Fluoxetine',345678,75,'Antidepressant medication',20,15,'Novartis',3,'2026-03-31'),('Baby Powder',444444,120,'Talc-free baby powder',6.5,5,'Merck',14,'2027-06-30'),('Cetirizine',456789,60,'Antihistamine for allergies',12.5,10,'Bayer',4,'2024-09-15'),('Shampoo',555555,180,'Gentle shampoo for daily use',8.75,6.5,'GlaxoSmithKline',15,'2026-03-31'),('Lisinopril',567890,90,'Antihypertensive for high blood pressure',18.75,15,'Merck',5,'2027-01-31'),('Toothpaste',666666,300,'Fluoride toothpaste for cavity protection',4.25,3,'Sanofi',16,'2025-12-31'),('Metformin',678901,120,'Antidiabetic for type 2 diabetes',22.99,18.99,'GlaxoSmithKline',6,'2026-06-30'),('Thermometer',777777,100,'Digital thermometer for home use',12.99,9.99,'AstraZeneca',17,'2028-01-01'),('Warfarin',789012,80,'Anticoagulant for blood clots',16.25,12.5,'Sanofi',7,'2025-03-01'),('Flea Collar',888888,75,'Flea and tick collar for pets',10.5,7.5,'Johnson & Johnson',18,'2025-07-15'),('Lamotrigine',890123,70,'Antiepileptic for seizures',24.99,20,'AstraZeneca',8,'2024-12-15'),('Fluconazole',901234,50,'Antifungal for fungal infections',14.5,11,'Johnson & Johnson',9,'2026-08-31'),('Protein Powder',999999,120,'Whey protein powder for muscle building',29.99,24.99,'Roche',19,'2024-11-30');
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
  CONSTRAINT `item_side_effect_ibfk_1` FOREIGN KEY (`itemID`) REFERENCES `item` (`par_code`),
  CONSTRAINT `item_side_effect_ibfk_2` FOREIGN KEY (`sideEffectID`) REFERENCES `side_effect` (`sideEffectID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item_side_effect`
--

LOCK TABLES `item_side_effect` WRITE;
/*!40000 ALTER TABLE `item_side_effect` DISABLE KEYS */;
INSERT INTO `item_side_effect` VALUES (345678,1),(123456,2),(234567,3),(456789,4),(567890,5),(678901,6),(789012,7),(890123,8),(901234,9),(12345,10);
/*!40000 ALTER TABLE `item_side_effect` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orderes`
--

DROP TABLE IF EXISTS `orderes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orderes` (
  `order_id` int NOT NULL AUTO_INCREMENT,
  `id` int DEFAULT NULL,
  PRIMARY KEY (`order_id`),
  KEY `id` (`id`),
  CONSTRAINT `orderes_ibfk_1` FOREIGN KEY (`id`) REFERENCES `employee` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orderes`
--

LOCK TABLES `orderes` WRITE;
/*!40000 ALTER TABLE `orderes` DISABLE KEYS */;
INSERT INTO `orderes` VALUES (1,1),(7,1),(9,1),(10,1),(17,1),(18,1),(19,1),(29,1),(44,1),(45,1),(2,2),(20,2),(30,2),(3,3),(21,3),(31,3),(12,4),(13,4),(14,4),(15,4),(16,4),(22,4),(32,4),(23,5),(33,5),(24,6),(34,6),(25,7),(35,7),(26,8),(36,8),(27,9),(37,9),(28,10),(38,10),(39,11),(40,12),(41,13),(42,14),(43,15);
/*!40000 ALTER TABLE `orderes` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
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
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prescription`
--

LOCK TABLES `prescription` WRITE;
/*!40000 ALTER TABLE `prescription` DISABLE KEYS */;
INSERT INTO `prescription` VALUES (1,'2023-04-01','1 tablet twice daily','Take with food',1,1),(2,'2023-04-15','10 mL every 6 hours','Shake well before use',2,2),(3,'2023-05-01','1 capsule daily','Take on an empty stomach',3,3),(4,'2023-04-01','1 tablet twice daily','Take with food',1,1),(5,'2023-04-15','10 mL every 6 hours','Shake well before use',2,2),(6,'2023-05-01','1 capsule daily','Take on an empty stomach',3,3),(7,'2023-05-10','2 tablets once daily','Take with a full glass of water',4,4),(8,'2023-05-20','5 mg injection weekly','Inject subcutaneously',5,5),(9,'2023-06-01','500 mg twice daily','Take with meals',6,6),(10,'2023-06-10','2.5 mg once daily','Take at bedtime',7,7),(11,'2023-06-20','20 mL every 8 hours','Shake well before use',8,8),(12,'2023-07-01','1 suppository daily','Insert rectally before bedtime',9,9),(13,'2023-07-10','1 patch every 24 hours','Apply to clean, dry, hairless skin',10,10);
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
  CONSTRAINT `prescription_item_ibfk_2` FOREIGN KEY (`itemID`) REFERENCES `item` (`par_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prescription_item`
--

LOCK TABLES `prescription_item` WRITE;
/*!40000 ALTER TABLE `prescription_item` DISABLE KEYS */;
INSERT INTO `prescription_item` VALUES (1,123456,'1 tablet twice daily','10 days'),(2,234567,'10 mL every 6 hours','5 days'),(3,345678,'1 capsule daily','30 days'),(4,456789,'2 tablets once daily','14 days'),(5,567890,'5 mg injection weekly','4 weeks'),(6,678901,'500 mg twice daily','60 days'),(7,789012,'2.5 mg once daily','90 days'),(8,890123,'20 mL every 8 hours','7 days'),(9,901234,'1 suppository daily','21 days'),(10,12345,'1 patch every 24 hours','28 days');
/*!40000 ALTER TABLE `prescription_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `provide_company`
--

DROP TABLE IF EXISTS `provide_company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `provide_company` (
  `company_name` varchar(70) NOT NULL,
  `phone` bigint DEFAULT NULL,
  `address` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`company_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `provide_company`
--

LOCK TABLES `provide_company` WRITE;
/*!40000 ALTER TABLE `provide_company` DISABLE KEYS */;
INSERT INTO `provide_company` VALUES ('Abbott',23456789,'100 Abbott Park Rd, Abbott Park, IL 60064'),('AL_Quds',22406550,'Nablus Street - Al Baloua - Al-Bireh - Ramallah / Palestine'),('Al_Sharqea',22812586,'Al_Bera - Ramallah / Palestine'),('AstraZeneca',23456789,'1 Cambridge Biomedical Campus, Cambridge, UK'),('Bayer',98765432,'456 Oak St, Leverkusen, Germany'),('Beit_Jalla',22890447,'Beit_Jalla - hadera street'),('Birzeit',22987572,'Shatilla Street - Ramallah / Palestine'),('Dar_Alshefa',22478916,'Betonea - Ramallah / Palestine'),('GlaxoSmithKline',12345678,'980 Elm Ave, Brentford, UK'),('Johnson & Johnson',98765432,'1 Johnson & Johnson Plaza, New Brunswick, NJ 08933'),('Merck',87654321,'789 Pine Rd, Kenilworth, NJ 07033'),('Novartis',34545343,'789 Maple Ave, Basel, Switzerland'),('Pfizer',435435,'123 Main St, New York, NY 10001'),('Roche',45678912,'124 Grenzacherstrasse, Basel, Switzerland'),('Sanofi',87654321,'54 Maple Blvd, Paris, France');
/*!40000 ALTER TABLE `provide_company` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `side_effect`
--

LOCK TABLES `side_effect` WRITE;
/*!40000 ALTER TABLE `side_effect` DISABLE KEYS */;
INSERT INTO `side_effect` VALUES (1,'Headache','Mild'),(2,'Nausea','Moderate'),(3,'Rash','Severe'),(4,'Headache','Mild'),(5,'Nausea','Moderate'),(6,'Rash','Severe'),(7,'Drowsiness','Mild'),(8,'Diarrhea','Moderate'),(9,'Dizziness','Mild'),(10,'Constipation','Mild'),(11,'Dry Mouth','Mild'),(12,'Insomnia','Moderate'),(14,'sami','Mild');
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
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `storage_method`
--

LOCK TABLES `storage_method` WRITE;
/*!40000 ALTER TABLE `storage_method` DISABLE KEYS */;
INSERT INTO `storage_method` VALUES (1,'Refrigerated','Store between 2-8°C'),(2,'Room Temperature','Store below 25°C'),(3,'Frozen','Store below 0°C'),(4,'Refrigerated','Store between 2-8°C'),(5,'Room Temperature','Store below 25°C'),(6,'Frozen','Store below 0°C'),(7,'Controlled Room Temperature','Store between 20-25°C'),(8,'Protected from Light','Store in a light-resistant container'),(9,'Humid Environment','Store in a humid environment'),(10,'Dry Environment','Store in a dry environment'),(11,'Inert Atmosphere','Store in an inert atmosphere'),(12,'Sterile Conditions','Store under sterile conditions'),(13,'Cool and Dry','Store in a cool and dry place'),(14,'Avoid Sunlight','Store away from direct sunlight'),(15,'Tightly Sealed','Keep the container tightly sealed'),(16,'Upright Position','Store in an upright position'),(17,'Childproof Cap','Container should have a childproof cap'),(18,'Tamper-Evident Seal','Container should have a tamper-evident seal'),(19,'Fragile','Handle with care, as the contents are fragile'),(20,'Flammable','Contents are flammable, store away from heat sources'),(21,'Refrigerate After Opening','Refrigerate after opening the container'),(22,'Keep Frozen','Store in a frozen state');
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

-- Dump completed on 2024-06-10 21:26:02
