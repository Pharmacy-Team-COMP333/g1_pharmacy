drop database g1_pharmcy;
CREATE DATABASE g1_pharmcy;
USE g1_pharmcy;


CREATE TABLE COMPANY (
    companyID INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    phone VARCHAR(20),
    address VARCHAR(255)
);

CREATE TABLE CATEGORY (
    cat_id INT PRIMARY KEY AUTO_INCREMENT,
    categores_name VARCHAR(255) NOT NULL,
    Description VARCHAR(255)
);

CREATE TABLE STORAGE_METHOD (
    storageMethodID INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255)
);

CREATE TABLE CUSTOMER (
    customerID INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    contactInfo VARCHAR(255)
);


CREATE TABLE EMPLOYEE (
    id INT PRIMARY KEY AUTO_INCREMENT,
    employee_name VARCHAR(255) NOT NULL,
    birthday DATE NOT NULL,
    date_of_employment DATE NOT NULL,
    emp_password VARCHAR(30)
);

CREATE TABLE hourly_employee (
    id INT PRIMARY KEY,
    work_hours REAL,
    hour_price REAL,
    FOREIGN KEY (id)
        REFERENCES employee (id)
        ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE contrect_employee (
    id INT PRIMARY KEY,
    amount_paid REAL,
    FOREIGN KEY (id)
        REFERENCES employee (id)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE ORDER_TABLE (
    orderID INT PRIMARY KEY AUTO_INCREMENT,
    orderDate DATE NOT NULL,
    orderType VARCHAR(50) NOT NULL,
    employeeID INT NOT NULL,
    customerID INT NOT NULL,
    FOREIGN KEY (employeeID) REFERENCES EMPLOYEE(id),
    FOREIGN KEY (customerID) REFERENCES CUSTOMER(customerID)
);


CREATE TABLE PAYMENT_METHOD (
    paymentMethodID INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE BILL (
    billID INT PRIMARY KEY AUTO_INCREMENT,
    totalPrice DECIMAL(10, 2) NOT NULL,
    profit DECIMAL(10, 2) NOT NULL,
    billType VARCHAR(50) NOT NULL,
    orderID INT NOT NULL UNIQUE,
    paymentMethodID INT NOT NULL,
    FOREIGN KEY (orderID) REFERENCES ORDER_TABLE(orderID),
    FOREIGN KEY (paymentMethodID) REFERENCES PAYMENT_METHOD(paymentMethodID)
);

CREATE TABLE INSURANCE_COMPANY (
    insuranceCompanyID INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    discountPercentage DECIMAL(5, 2) NOT NULL
);

CREATE TABLE INSURANCE_CUSTOMER (
    customerID INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    identificationNumber VARCHAR(50) UNIQUE NOT NULL,
    insuranceCompanyID INT NOT NULL,
    FOREIGN KEY (insuranceCompanyID) REFERENCES INSURANCE_COMPANY(insuranceCompanyID)
);





CREATE TABLE DOSAGE_FORM (
    dosageFormID INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE SIDE_EFFECT (
    sideEffectID INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    severity VARCHAR(50) NOT NULL
);


CREATE TABLE DISEASE (
    diseaseID INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    treatment VARCHAR(255)
);

CREATE TABLE DOCTOR (
    doctorID INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    specialization VARCHAR(255) NOT NULL,
    phoneNumber VARCHAR(20)
);


CREATE TABLE PRESCRIPTION (
    prescriptionID INT PRIMARY KEY AUTO_INCREMENT,
    prescriptionDate DATE NOT NULL,
    dosage VARCHAR(255) NOT NULL,
    instructions VARCHAR(255) NOT NULL,
    doctorID INT NOT NULL,
    customerID INT NOT NULL,
    FOREIGN KEY (doctorID) REFERENCES DOCTOR(doctorID),
    FOREIGN KEY (customerID) REFERENCES CUSTOMER(customerID)
);



CREATE TABLE ITEM (
    itemID INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    barcode VARCHAR(50) UNIQUE,
    quantity INT NOT NULL DEFAULT 0,
    description VARCHAR(255),
    salePrice DECIMAL(10, 2) NOT NULL,
    originalPrice DECIMAL(10, 2) NOT NULL,
    expiryDate DATE,
    companyID INT NOT NULL,
    cat_id INT NOT NULL,
    storageMethodID INT NOT NULL,
    dosageFormID INT NOT NULL,
    FOREIGN KEY (companyID) REFERENCES COMPANY(companyID),
    FOREIGN KEY (cat_id) REFERENCES CATEGORY(cat_id),
    FOREIGN KEY (storageMethodID) REFERENCES STORAGE_METHOD(storageMethodID),
    FOREIGN KEY (dosageFormID) REFERENCES DOSAGE_FORM(dosageFormID)
);


CREATE TABLE ORDER_ITEM (
    orderID INT NOT NULL,
    itemID INT NOT NULL,
    quantity INT NOT NULL DEFAULT 1,
    fullSalePrice DECIMAL(10, 2) NOT NULL,
    fullOriginalPrice DECIMAL(10, 2) NOT NULL,
    PRIMARY KEY (orderID, itemID),
    FOREIGN KEY (orderID) REFERENCES ORDER_TABLE(orderID),
    FOREIGN KEY (itemID) REFERENCES ITEM(itemID)
);

CREATE TABLE ITEM_SIDE_EFFECT (
    itemID INT NOT NULL,
    sideEffectID INT NOT NULL,
    PRIMARY KEY (itemID, sideEffectID),
    FOREIGN KEY (itemID) REFERENCES ITEM(itemID),
    FOREIGN KEY (sideEffectID) REFERENCES SIDE_EFFECT(sideEffectID)
);

CREATE TABLE PRESCRIPTION_ITEM (
    prescriptionID INT NOT NULL,
    itemID INT NOT NULL,
    dosage VARCHAR(255) NOT NULL,
    duration VARCHAR(255) NOT NULL,
    PRIMARY KEY (prescriptionID, itemID),
    FOREIGN KEY (prescriptionID) REFERENCES PRESCRIPTION(prescriptionID),
    FOREIGN KEY (itemID) REFERENCES ITEM(itemID)
);


 -- ------------------------------------
-- Insert data into COMPANY table
INSERT INTO COMPANY (name, phone, address) VALUES
('Pfizer', '1234567890', '123 Main St, New York, NY 10001'),
('Johnson & Johnson', '9876543210', '456 Oak Rd, New Brunswick, NJ 08901'),
('Novartis', '5551234567', '789 Maple Ave, Basel, Switzerland');

-- Insert data into CATEGORY table
INSERT INTO CATEGORY (categores_name, Description) VALUES
('Antibiotics', 'Medications used to treat bacterial infections'),
('Pain Relievers', 'Medications used to relieve pain'),
('Antidepressants', 'Medications used to treat depression');

-- Insert data into STORAGE_METHOD table
INSERT INTO STORAGE_METHOD (name, description) VALUES
('Refrigerated', 'Store between 2-8°C'),
('Room Temperature', 'Store below 25°C'),
('Frozen', 'Store below 0°C');

-- Insert data into CUSTOMER table
INSERT INTO CUSTOMER (name, contactInfo) VALUES
('John Doe', '123 Main St, Anytown, CA 12345'),
('Jane Smith', 'jsmith@email.com'),
('Bob Johnson', '555-1234');

-- Insert data into EMPLOYEE table
INSERT INTO EMPLOYEE (employee_name, birthday, date_of_employment, emp_password) VALUES
('Alice Williams', '1990-05-15', '2018-03-01', 'password123'),
('Bob Thompson', '1985-11-22', '2016-09-15', 'securepass'),
('Charlie Roberts', '1992-07-03', '2020-02-01', 'mypassword');

-- Insert data into hourly_employee table
INSERT INTO hourly_employee (id, work_hours, hour_price) VALUES
(1, 40, 25.50),
(2, 30, 20.75);

-- Insert data into contrect_employee table
INSERT INTO contrect_employee (id, amount_paid) VALUES
(3, 5000.00);

-- Insert data into ORDER_TABLE table
INSERT INTO ORDER_TABLE (orderDate, orderType, employeeID, customerID) VALUES
('2023-04-01', 'Prescription', 1, 1),
('2023-04-15', 'Over-the-counter', 2, 2),
('2023-05-01', 'Prescription', 3, 3);

-- Insert data into PAYMENT_METHOD table
INSERT INTO PAYMENT_METHOD (name) VALUES
('Cash'),
('Credit Card'),
('Insurance');

-- Insert data into BILL table
INSERT INTO BILL (totalPrice, profit, billType, orderID, paymentMethodID) VALUES
(50.00, 10.00, 'Prescription', 1, 3),
(25.99, 5.99, 'Over-the-counter', 2, 1),
(75.00, 15.00, 'Prescription', 3, 2);

-- Insert data into INSURANCE_COMPANY table
INSERT INTO INSURANCE_COMPANY (name, discountPercentage) VALUES
('Blue Cross', 0.20),
('Aetna', 0.15),
('UnitedHealthcare', 0.25);

-- Insert data into INSURANCE_CUSTOMER table
INSERT INTO INSURANCE_CUSTOMER (name, identificationNumber, insuranceCompanyID) VALUES
('John Doe', '123456789', 1),
('Jane Smith', '987654321', 2),
('Bob Johnson', '456789123', 3);

-- Insert data into DOSAGE_FORM table
INSERT INTO DOSAGE_FORM (name) VALUES
('Tablet'),
('Capsule'),
('Syrup');

-- Insert data into SIDE_EFFECT table
INSERT INTO SIDE_EFFECT (name, severity) VALUES
('Headache', 'Mild'),
('Nausea', 'Moderate'),
('Rash', 'Severe');

-- Insert data into DISEASE table
INSERT INTO DISEASE (name, description, treatment) VALUES
('Hypertension', 'High blood pressure', 'Lifestyle changes, medications'),
('Diabetes', 'Chronic condition affecting blood sugar levels', 'Insulin, oral medications'),
('Asthma', 'Chronic lung disease', 'Inhalers, corticosteroids');

-- Insert data into DOCTOR table
INSERT INTO DOCTOR (name, specialization, phoneNumber) VALUES
('Dr. Smith', 'Cardiology', '555-1234'),
('Dr. Johnson', 'Endocrinology', '555-5678'),
('Dr. Williams', 'Pulmonology', '555-9012');

-- Insert data into PRESCRIPTION table
INSERT INTO PRESCRIPTION (prescriptionDate, dosage, instructions, doctorID, customerID) VALUES
('2023-04-01', '1 tablet twice daily', 'Take with food', 1, 1),
('2023-04-15', '10 mL every 6 hours', 'Shake well before use', 2, 2),
('2023-05-01', '1 capsule daily', 'Take on an empty stomach', 3, 3);

-- Insert data into ITEM table
INSERT INTO ITEM (name, barcode, quantity, description, salePrice, originalPrice, expiryDate, companyID, cat_id, storageMethodID, dosageFormID) VALUES
('Amoxicillin', '123456789012', 100, 'Antibiotic for bacterial infections', 10.99, 8.99, '2025-12-31', 1, 1, 1, 1),
('Ibuprofen', '234567890123', 50, 'Pain reliever', 5.99, 4.99, '2024-06-30', 2, 2, 2, 2),
('Fluoxetine', '345678901234', 75, 'Antidepressant medication', 20.00, 15.00, '2026-03-31', 3, 3, 1, 1);

-- Insert data into ORDER_ITEM table
INSERT INTO ORDER_ITEM (orderID, itemID, quantity, fullSalePrice, fullOriginalPrice) VALUES
(1, 1, 30, 329.70, 269.70),
(2, 2, 10, 59.90, 49.90),
(3, 3, 60, 1200.00, 900.00);

-- Insert data into ITEM_SIDE_EFFECT table
INSERT INTO ITEM_SIDE_EFFECT (itemID, sideEffectID) VALUES
(1, 1),
(1, 2),
(2, 3),
(3, 1);

-- Insert data into PRESCRIPTION_ITEM table
INSERT INTO PRESCRIPTION_ITEM (prescriptionID, itemID, dosage, duration) VALUES
(1, 1, '1 tablet twice daily', '10 days'),
(2, 2, '10 mL every 6 hours', '5 days'),
(3, 3, '1 capsule daily', '30 days');

