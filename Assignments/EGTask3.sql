SELECT o.OrderID, o.OrderDate, c.FirstName, c.LastName, c.Email, c.Phone
FROM Orders o
JOIN Customers c ON o.CustomerID = c.CustomerID;

SELECT p.ProductName, SUM(od.Quantity * p.Price) AS TotalRevenue
FROM OrderDetails od
JOIN Products p ON od.ProductID = p.ProductID
GROUP BY p.ProductName
ORDER BY TotalRevenue DESC;

SELECT DISTINCT c.FirstName, c.LastName, c.Email, c.Phone
FROM Customers c
JOIN Orders o ON c.CustomerID = o.CustomerID;
CREATE TABLE Categories (
    CategoryID INT PRIMARY KEY AUTO_INCREMENT,
    CategoryName VARCHAR(100) NOT NULL
);
ALTER TABLE Products ADD COLUMN CategoryID INT;
ALTER TABLE Products ADD FOREIGN KEY (CategoryID) REFERENCES Categories(CategoryID);

INSERT INTO Categories (CategoryName) VALUES
('Smartphones'),
('Laptops'),
('Tablets'),
('Wearables'),
('Audio Devices'),
('Accessories');

UPDATE Products SET CategoryID = 1 WHERE ProductName = 'Smartphone';
UPDATE Products SET CategoryID = 2 WHERE ProductName = 'Laptop';
UPDATE Products SET CategoryID = 3 WHERE ProductName = 'Tablet';
UPDATE Products SET CategoryID = 4 WHERE ProductName = 'Smartwatch';
UPDATE Products SET CategoryID = 5 WHERE ProductName = 'Headphones';
UPDATE Products SET CategoryID = 6 WHERE ProductName IN ('Keyboard', 'Mouse', 'Monitor', 'Power Bank', 'Speaker');


SELECT p.ProductName, SUM(od.Quantity) AS TotalQuantityOrdered
FROM OrderDetails od
JOIN Products p ON od.ProductID = p.ProductID
GROUP BY p.ProductName
ORDER BY TotalQuantityOrdered DESC
LIMIT 1;
SELECT p.ProductName, c.CategoryName
FROM Products p
JOIN Categories c ON p.CategoryID = c.CategoryID;
SELECT c.FirstName, c.LastName, AVG(o.TotalAmount) AS AverageOrderValue
FROM Orders o
JOIN Customers c ON o.CustomerID = c.CustomerID
GROUP BY c.CustomerID
ORDER BY AverageOrderValue DESC;

SELECT o.OrderID, c.FirstName, c.LastName, c.Email, o.TotalAmount
FROM Orders o
JOIN Customers c ON o.CustomerID = c.CustomerID
ORDER BY o.TotalAmount DESC
LIMIT 1;

SELECT p.ProductName, COUNT(od.OrderID) AS TimesOrdered
FROM OrderDetails od
JOIN Products p ON od.ProductID = p.ProductID
GROUP BY p.ProductName
ORDER BY TimesOrdered DESC;

SELECT DISTINCT c.FirstName, c.LastName, c.Email, c.Phone
FROM Customers c
JOIN Orders o ON c.CustomerID = o.CustomerID
JOIN OrderDetails od ON o.OrderID = od.OrderID
JOIN Products p ON od.ProductID = p.ProductID
WHERE p.ProductName = 'Wireless Earbuds'; 
SELECT SUM(TotalAmount) AS TotalRevenue
FROM Orders
WHERE OrderDate BETWEEN '2025-01-01' AND '2025-03-17'; 


