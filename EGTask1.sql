CREATE DATABASE TechShop;
USE TechShop;
CREATE TABLE Customers (
    CustomerID INT PRIMARY KEY AUTO_INCREMENT,
    FirstName VARCHAR(50) NOT NULL,
    LastName VARCHAR(50) NOT NULL,
    Email VARCHAR(100) UNIQUE NOT NULL,
    Phone VARCHAR(15) UNIQUE NOT NULL,
    Address TEXT
);
CREATE TABLE Products (
    ProductID INT PRIMARY KEY AUTO_INCREMENT,
    ProductName VARCHAR(100) NOT NULL,
    Description TEXT,
    Price DECIMAL(10,2) NOT NULL
);
CREATE TABLE Orders (
    OrderID INT PRIMARY KEY AUTO_INCREMENT,
    CustomerID INT NOT NULL,
    OrderDate DATETIME DEFAULT CURRENT_TIMESTAMP,
    TotalAmount DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID) ON DELETE CASCADE
);
CREATE TABLE OrderDetails (
    OrderDetailID INT PRIMARY KEY AUTO_INCREMENT,
    OrderID INT NOT NULL,
    ProductID INT NOT NULL,
    Quantity INT NOT NULL CHECK (Quantity > 0),
    FOREIGN KEY (OrderID) REFERENCES Orders(OrderID) ON DELETE CASCADE,
    FOREIGN KEY (ProductID) REFERENCES Products(ProductID) ON DELETE CASCADE
);
CREATE TABLE Inventory (
    InventoryID INT PRIMARY KEY AUTO_INCREMENT,
    ProductID INT NOT NULL,
    QuantityInStock INT NOT NULL CHECK (QuantityInStock >= 0),
    LastStockUpdate DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (ProductID) REFERENCES Products(ProductID) ON DELETE CASCADE
);
INSERT INTO Customers (FirstName, LastName, Email, Phone, Address) VALUES
('Kishor', 'S', 'kishore@gmail.com', '9876543210', '12, Anna Nagar, Chennai'),
('Priya', 'Subramani', 'priya.subramani@gmail.com', '9876543211', '45, MG Road, Coimbatore'),
('Vikram', 'Raj', 'vikram.raj@gmail.com', '9876543212', '78, Kamarajar Street, Madurai'),
('Divya', 'Lakshmi', 'divya.lakshmi@gmail.com', '9876543213', '101, Gandhi Road, Tiruchirapalli'),
('Senthil', 'Vel', 'senthil.vel@gmail.com', '9876543214', '202, Valluvar Street, Salem'),
('Meena', 'Ravi', 'meena.ravi@gmail.com', '9876543215', '303, Rajaji Nagar, Erode'),
('Sathish', 'Kannan', 'sathish.kannan@gmail.com', '9876543216', '404, Periyar Road, Vellore'),
('Anitha', 'Mohan', 'anitha.mohan@gmail.com', '9876543217', '505, Bharathi Street, Tirunelveli'),
('Karthik', 'Sundar', 'karthik.sundar@gmail.com', '9876543218', '606, Chidambaram Street, Dindigul'),
('Lakshmi', 'Saravanan', 'lakshmi.saravanan@gmail.com', '9876543219', '707, Nehru Nagar, Thanjavur');
INSERT INTO Products (ProductName, Description, Price) VALUES
('Smartphone', 'Latest model with AI features', 699.99),
('Laptop', 'High-performance laptop for gaming', 1299.99),
('Tablet', 'Lightweight and portable tablet', 499.99),
('Smartwatch', 'Fitness tracker with heart rate monitor', 199.99),
('Headphones', 'Noise-canceling wireless headphones', 149.99),
('Keyboard', 'Mechanical keyboard with RGB lighting', 89.99),
('Mouse', 'Wireless ergonomic mouse', 49.99),
('Monitor', '27-inch 4K UHD Monitor', 399.99),
('Speaker', 'Bluetooth portable speaker', 99.99),
('Power Bank', '10000mAh fast-charging power bank', 39.99);
INSERT INTO Orders (CustomerID, TotalAmount) VALUES
(1, 1299.99),
(2, 699.99),
(3, 149.99),
(4, 399.99),
(5, 499.99),
(6, 199.99),
(7, 89.99),
(8, 49.99),
(9, 99.99),
(10, 39.99);
INSERT INTO OrderDetails (OrderID, ProductID, Quantity) VALUES
(1, 2, 1),
(2, 1, 1),
(3, 5, 1),
(4, 8, 1),
(5, 3, 1),
(6, 4, 1),
(7, 6, 1),
(8, 7, 1),
(9, 9, 1),
(10, 10, 1);
INSERT INTO Inventory (ProductID, QuantityInStock) VALUES
(1, 50),
(2, 30),
(3, 40),
(4, 60),
(5, 70),
(6, 20),
(7, 100),
(8, 15),
(9, 35),
(10, 80);



