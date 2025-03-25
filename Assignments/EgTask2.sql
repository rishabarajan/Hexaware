SELECT FirstName, LastName, Email FROM Customers;
SELECT o.OrderID, o.OrderDate, c.FirstName, c.LastName 
FROM Orders o
JOIN Customers c ON o.CustomerID = c.CustomerID;
INSERT INTO Customers (FirstName, LastName, Email, Phone, Address) 
VALUES ('Gokul', 'Krishnan', 'gokul.krishnan@example.com', '9876543220', '908, MG Road, Chennai');
SET SQL_SAFE_UPDATES = 0;
UPDATE Products
SET Price = ROUND(Price * 1.10, 2)
WHERE Price > 0;
DELETE FROM OrderDetails WHERE OrderID = 2;
DELETE FROM Orders WHERE OrderID = 2;
INSERT INTO Orders (CustomerID, OrderDate, TotalAmount) 
VALUES (3, '2025-03-17', 4500.00);
UPDATE Customers
SET Email = 'new.email@example.com', Address = 'New Address, Chennai'
WHERE CustomerID = 3;
UPDATE Orders o
JOIN (
    SELECT od.OrderID, SUM(p.Price * od.Quantity) AS TotalCost
    FROM OrderDetails od
    JOIN Products p ON od.ProductID = p.ProductID
    GROUP BY od.OrderID
) AS OrderTotals
ON o.OrderID = OrderTotals.OrderID
SET o.TotalAmount = OrderTotals.TotalCost;
DELETE FROM OrderDetails WHERE OrderID IN (SELECT OrderID FROM Orders WHERE CustomerID = 1);
DELETE FROM Orders WHERE CustomerID = 1;
INSERT INTO Products (ProductName, Description, Price) 
VALUES ('Wireless Earbuds', 'Bluetooth 5.0 Earbuds with Noise Cancellation', 2999.00);
ALTER TABLE Orders ADD COLUMN Status VARCHAR(20) DEFAULT 'Pending';
UPDATE Orders
SET Status = 'Shipped'
WHERE OrderID = 2  ;
ALTER TABLE Customers ADD COLUMN OrderCount INT DEFAULT 0;
UPDATE Customers c
SET OrderCount = (
    SELECT COUNT(*)
    FROM Orders o
    WHERE o.CustomerID = c.CustomerID
);

