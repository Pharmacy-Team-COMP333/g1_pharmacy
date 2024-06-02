DROP DATABASE IF EXISTS g1_pharmcy;
CREATE DATABASE g1_pharmcy;
USE g1_pharmcy;

CREATE TABLE provide_company (
    company_name VARCHAR(70) PRIMARY KEY NOT NULL,
    phone VARCHAR(20),
    address VARCHAR(150)
);

CREATE TABLE categories (
    cat_id INT PRIMARY KEY AUTO_INCREMENT,
    categories_name VARCHAR(255) NOT NULL,
    description VARCHAR(255)
);

CREATE TABLE storage_method (
    storageMethodID INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255)
);

CREATE TABLE customer (
    customerID INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    contactInfo VARCHAR(255)
);

CREATE TABLE employee (
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    employee_name VARCHAR(60),
    birthday DATE,
    date_of_employment DATE,
    emp_password VARCHAR(30) -- The column name is emp_password, not emp_password
);

CREATE TABLE hourly_employee (
    id INT PRIMARY KEY,
    work_hours REAL,
    hour_price REAL,
    FOREIGN KEY (id)
        REFERENCES employee (id)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE contract_employee (
    id INT PRIMARY KEY,
    amount_paid REAL,
    FOREIGN KEY (id)
        REFERENCES employee (id)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE item (
    item_name VARCHAR(80),
    par_code INT NOT NULL,
    quantity INT,
    description VARCHAR(700),
    sale_price REAL,
    original_price REAL,
    provide_company_name VARCHAR(70),
    cat_id INT,
    exp_date DATE,
    PRIMARY KEY (par_code, provide_company_name, cat_id),
    FOREIGN KEY (provide_company_name)
        REFERENCES provide_company (company_name)
        ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (cat_id)
        REFERENCES categories (cat_id)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE orderes (
    order_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    id INT,
    FOREIGN KEY (id)
        REFERENCES employee (id)
        ON DELETE NO ACTION ON UPDATE CASCADE
);

CREATE TABLE invoice (
    quantity INT,
    full_sale_price REAL,
    full_original_price REAL,
    par_code INT NOT NULL,
    order_id INT NOT NULL,
    PRIMARY KEY (par_code, order_id),
    FOREIGN KEY (order_id)
        REFERENCES orderes (order_id)
        ON DELETE NO ACTION ON UPDATE CASCADE,
    FOREIGN KEY (par_code)
        REFERENCES item (par_code)
        ON DELETE NO ACTION ON UPDATE CASCADE
);

CREATE TABLE payment_method (
    paymentMethodID INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE bill (
    order_id INT PRIMARY KEY NOT NULL,
    order_date DATE,
    full_price REAL,
    profits REAL,
    bill_type VARCHAR(32),
    emp_id INT,
    FOREIGN KEY (emp_id)
        REFERENCES employee (id)
        ON DELETE NO ACTION ON UPDATE CASCADE,
    FOREIGN KEY (order_id)
        REFERENCES orderes (order_id)
        ON DELETE NO ACTION ON UPDATE CASCADE
);

CREATE TABLE insurance_company (
    insurance_companyName VARCHAR(32) PRIMARY KEY,
    insurance_companyDiscount INT,
    numberOfCustomer INT DEFAULT 0
);

CREATE TABLE insurance (
    customerID INT PRIMARY KEY,
    customerName VARCHAR(32),
    insurance_companyName VARCHAR(32) NOT NULL,
    FOREIGN KEY (insurance_companyName)
        REFERENCES insurance_company (insurance_companyName)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE insuranceOrder (
    customer_insurance_id INT NOT NULL,
    order_date DATE,
    order_id INT NOT NULL,
    PRIMARY KEY (customer_insurance_id, order_id),
    FOREIGN KEY (customer_insurance_id)
        REFERENCES insurance (customerID)
        ON DELETE NO ACTION ON UPDATE CASCADE,
    FOREIGN KEY (order_id)
        REFERENCES orderes (order_id)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE dosage_form (
    dosageFormID INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE side_effect (
    sideEffectID INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    severity VARCHAR(50) NOT NULL
);

CREATE TABLE disease (
    diseaseID INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    treatment VARCHAR(255)
);

CREATE TABLE doctor (
    doctorID INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    specialization VARCHAR(255) NOT NULL,
    phoneNumber VARCHAR(20)
);

CREATE TABLE prescription (
    prescriptionID INT PRIMARY KEY AUTO_INCREMENT,
    prescriptionDate DATE NOT NULL,
    dosage VARCHAR(255) NOT NULL,
    instructions VARCHAR(255) NOT NULL,
    doctorID INT NOT NULL,
    customerID INT NOT NULL,
    FOREIGN KEY (doctorID) REFERENCES doctor(doctorID),
    FOREIGN KEY (customerID) REFERENCES customer(customerID)
);

CREATE TABLE cashOrder (
    order_id INT PRIMARY KEY,
    order_date DATE,
    FOREIGN KEY (order_id)
        REFERENCES orderes (order_id)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE item_side_effect (
    itemID INT NOT NULL,
    sideEffectID INT NOT NULL,
    PRIMARY KEY (itemID, sideEffectID),
    FOREIGN KEY (itemID) REFERENCES item(par_code),
    FOREIGN KEY (sideEffectID) REFERENCES side_effect(sideEffectID)
);

CREATE TABLE prescription_item (
    prescriptionID INT NOT NULL,
    itemID INT NOT NULL,
    dosage VARCHAR(255) NOT NULL,
    duration VARCHAR(255) NOT NULL,
    PRIMARY KEY (prescriptionID, itemID),
    FOREIGN KEY (prescriptionID) REFERENCES prescription(prescriptionID),
    FOREIGN KEY (itemID) REFERENCES item(par_code)
);


-- Insert data into provide_company table
INSERT INTO provide_company (company_name, phone, address) VALUES
('Pfizer', '1234567890', '123 Main St, New York, NY 10001'),
('Johnson & Johnson', '9876543210', '456 Oak Rd, New Brunswick, NJ 08901'),
('Novartis', '5551234567', '789 Maple Ave, Basel, Switzerland');

-- Insert data into categories table
INSERT INTO categories (categories_name, description) VALUES
('Antibiotics', 'Medications used to treat bacterial infections'),
('Pain Relievers', 'Medications used to relieve pain'),
('Antidepressants', 'Medications used to treat depression');

-- Insert data into storage_method table
INSERT INTO storage_method (name, description) VALUES
('Refrigerated', 'Store between 2-8°C'),
('Room Temperature', 'Store below 25°C'),
('Frozen', 'Store below 0°C');

-- Insert data into customer table
INSERT INTO customer (name, contactInfo) VALUES
('John Doe', '123 Main St, Anytown, CA 12345'),
('Jane Smith', 'jsmith@email.com'),
('Bob Johnson', '555-1234');

-- Insert data into employee table
INSERT INTO employee (employee_name, birthday, date_of_employment, emp_password) VALUES
('Alice Williams', '1990-05-15', '2018-03-01', 'password123'),
('Bob Thompson', '1985-11-22', '2016-09-15', 'securepass'),
('sami', '2001-06-21', '2016-09-15', 'sami'),
('Charlie Roberts', '1992-07-03', '2020-02-01', 'mypassword');

-- Insert data into hourly_employee table
INSERT INTO hourly_employee (id, work_hours, hour_price) VALUES
(1, 40, 25.50),
(2, 30, 20.75);

-- Insert data into contract_employee table
INSERT INTO contract_employee (id, amount_paid) VALUES
(3, 5000.00);

-- Insert data into item table
INSERT INTO item (item_name, par_code, quantity, description, sale_price, original_price, provide_company_name, cat_id, exp_date) VALUES
('Amoxicillin', 123456, 100, 'Antibiotic for bacterial infections', 10.99, 8.99, 'Pfizer', 1, '2025-12-31'),
('Ibuprofen', 234567, 50, 'Pain reliever', 5.99, 4.99, 'Johnson & Johnson', 2, '2024-06-30'),
('Fluoxetine', 345678, 75, 'Antidepressant medication', 20.00, 15.00, 'Novartis', 3, '2026-03-31');

-- Insert data into orders table
INSERT INTO orderes (id) VALUES
(1), (2), (3);

-- Insert data into payment_method table
INSERT INTO payment_method (name) VALUES
('Cash'), ('Credit Card'), ('Insurance');

-- Insert data into insurance_company table
INSERT INTO insurance_company (insurance_companyName, insurance_companyDiscount) VALUES
('Blue Cross', 20), ('Aetna', 15), ('UnitedHealthcare', 25);

-- Insert data into insurance table
INSERT INTO insurance (customerID, customerName, insurance_companyName) VALUES
(1, 'John Doe', 'Blue Cross'),
(2, 'Jane Smith', 'Aetna'),
(3, 'Bob Johnson', 'UnitedHealthcare');

-- Insert data into doctor table
INSERT INTO doctor (name, specialization, phoneNumber) VALUES
('Dr. Smith', 'Cardiology', '555-1234'),
('Dr. Johnson', 'Endocrinology', '555-5678'),
('Dr. Williams', 'Pulmonology', '555-9012');

-- Insert data into prescription table
INSERT INTO prescription (prescriptionDate, dosage, instructions, doctorID, customerID) VALUES
('2023-04-01', '1 tablet twice daily', 'Take with food', 1, 1),
('2023-04-15', '10 mL every 6 hours', 'Shake well before use', 2, 2),
('2023-05-01', '1 capsule daily', 'Take on an empty stomach', 3, 3);

-- Insert data into dosage_form table
INSERT INTO dosage_form (name) VALUES
('Tablet'), ('Capsule'), ('Syrup');

-- Insert data into side_effect table
INSERT INTO side_effect (name, severity) VALUES
('Headache', 'Mild'), ('Nausea', 'Moderate'), ('Rash', 'Severe');

-- Insert data into disease table
INSERT INTO disease (name, description, treatment) VALUES
('Hypertension', 'High blood pressure', 'Lifestyle changes, medications'),
('Diabetes', 'Chronic condition affecting blood sugar levels', 'Insulin, oral medications'),
('Asthma', 'Chronic lung disease', 'Inhalers, corticosteroids');

-- Insert data into invoice table
INSERT INTO invoice (quantity, full_sale_price, full_original_price, par_code, order_id) VALUES
(30, 329.70, 269.70, 123456, 1),
(10, 59.90, 49.90, 234567, 2),
(60, 1200.00, 900.00, 345678, 3);

-- Insert data into bill table
INSERT INTO bill (order_id, order_date, full_price, profits, bill_type, emp_id) VALUES
(1, '2023-04-01', 329.70, 60.00, 'Prescription', 1),
(2, '2023-04-15', 59.90, 10.00, 'Over-the-counter', 2),
(3, '2023-05-01', 1200.00, 300.00, 'Prescription', 3);

-- Insert data into cashOrder table
INSERT INTO cashOrder (order_id, order_date) VALUES
(2, '2023-04-15');

-- Insert data into insuranceOrder table
INSERT INTO insuranceOrder (customer_insurance_id, order_date, order_id) VALUES
(1, '2023-04-01', 1),
(3, '2023-05-01', 3);

-- Insert data into item_side_effect table
INSERT INTO item_side_effect (itemID, sideEffectID) VALUES
(123456, 1), (123456, 2), (234567, 3), (345678, 1);

-- Insert data into prescription_item table
INSERT INTO prescription_item (prescriptionID, itemID, dosage, duration) VALUES
(1, 123456, '1 tablet twice daily', '10 days'),
(2, 234567, '10 mL every 6 hours', '5 days'),
(3, 345678, '1 capsule daily', '30 days');


insert into employee (employee_name,birthday,date_of_employment,emp_password) value ("Nathera Alwan",'1985-01-15','2010-05-16',"admin");


insert into provide_company values ("AL_Quds",022406550,"Nablus Street - Al Baloua - Al-Bireh - Ramallah / Palestine"),
("Birzeit",022987572,"Shatilla Street - Ramallah / Palestine"),
("Beit_Jalla",022890447,"Beit_Jalla - hadera street"),("Al_Sharqea",022812586,"Al_Bera - Ramallah / Palestine"),
("Jama",022746892,"Betonea - Ramallah / Palestine"),("Dar_Alshefa",022478916,"Betonea - Ramallah / Palestine");

 insert into employee (employee_name,birthday,date_of_employment,emp_password) values ("Ahlam Eldeen Asfour",'1992-01-15','2018-05-16',"root"),
 ("Mera Ahmad Shekh",'1997-08-26','2020-08-14',"root1"),("Ahmad Said Ammar",'1995-04-21','2021-08-14',"root2"),
 ("Lama Naser Hammad",'1990-05-26','2019-01-30',"root3"),("Malak Raed Hannon",'1980-08-26','2012-12-14',"root4"),
 ("Manal Awwad Salem",'1992-07-04','2018-01-25',"root5"),("Raed Mohammad Ayman",'1995-08-26','2020-08-14',"root6"),
 ("Ibrahim Ahmad Asfour",'1989-12-26','2015-02-14',"root7"),("Ahmad Malik Alwan",'1991-08-26','2018-07-14',"root8"); 

ALTER TABLE provide_company MODIFY COLUMN phone BIGINT;

