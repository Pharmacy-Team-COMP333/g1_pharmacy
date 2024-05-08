drop database g1_pharmcy;
CREATE DATABASE g1_pharmcy;
USE g1_pharmcy;

CREATE TABLE Company (
    CompanyID INT AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(100) NOT NULL,
    PhoneNumber VARCHAR(20),
    Address VARCHAR(200)
);

CREATE TABLE InsuranceCompany (
    CompanyID INT PRIMARY KEY,
    DiscountPercentage DECIMAL(5, 2),
    FOREIGN KEY (CompanyID) REFERENCES Company(CompanyID)
);

CREATE TABLE category (
    cat_id INT AUTO_INCREMENT PRIMARY KEY,
    categores_name VARCHAR(100) NOT NULL,
    Description TEXT
);

INSERT INTO Category (categores_name, Description)
VALUES
    ('Category 1', 'Description for Category 1'),
    ('Category 2', 'Description for Category 2'),
    ('Category 3', 'Description for Category 3');
