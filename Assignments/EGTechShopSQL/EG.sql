create database TechShop;
use TechShop;
create table Customers (
    CustomerID int primary key auto_increment,
    FirstName varchar(50) not null,
    LastName varchar(50) not null,
    Email varchar(100) unique not null,
    Phone varchar(15) unique not null,
    Address text
);
create table Products (
    ProductID int primary key auto_increment,
    ProductName varchar(100) not null,
    Description text,
    Price decimal(10,2) not null
);
create table Orders (
    OrderID int primary key auto_increment,
    CustomerID int not null,
    OrderDate datetime default current_timestamp,
    TotalAmount decimal(10,2) not null,
    foreign key (CustomerID) references Customers(CustomerID) on delete cascade
);
create table OrderDetails (
    OrderDetailID int primary key auto_increment,
    OrderID int not null,
    ProductID int not null,
    Quantity int not null check (Quantity > 0),
    foreign key (OrderID) references Orders(OrderID) on delete cascade,
    foreign key (ProductID) references Products(ProductID) on delete cascade
);
create table Inventory (
    InventoryID int primary key auto_increment,
    ProductID int not null,
    QuantityInStock int not null check (QuantityInStock >= 0),
    LastStockUpdate datetime default current_timestamp,
    foreign key (ProductID) references Products(ProductID) on delete cascade
);

insert into Customers (FirstName, LastName, Email, Phone, Address) values
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

insert into Products (ProductName, Description, Price) values
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

insert into Orders (CustomerID, TotalAmount) values
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

insert into OrderDetails (OrderID, ProductID, Quantity) values
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

insert into Inventory (ProductID, QuantityInStock) values
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

select FirstName, LastName, Email from Customers;
select o.OrderID, o.OrderDate, c.FirstName, c.LastName 
from Orders o
join Customers c on o.CustomerID = c.CustomerID;

insert into Customers (FirstName, LastName, Email, Phone, Address) 
values ('Gokul', 'Krishnan', 'gokul.krishnan@example.com', '9876543220', '908, MG Road, Chennai');

set sql_safe_updates = 0;

update Products
set Price = round(Price * 1.10, 2)
where Price > 0;

delete from OrderDetails where OrderID = 2;
delete from Orders where OrderID = 2;

insert into Orders (CustomerID, OrderDate, TotalAmount) 
values (3, '2025-03-17', 4500.00);

update Customers
set Email = 'new.email@example.com', Address = 'New Address, Chennai'
where CustomerID = 3;

update Orders o
join (
    select od.OrderID, sum(p.Price * od.Quantity) as TotalCost
    from OrderDetails od
    join Products p on od.ProductID = p.ProductID
    group by od.OrderID
) as OrderTotals
on o.OrderID = OrderTotals.OrderID
set o.TotalAmount = OrderTotals.TotalCost;

delete from OrderDetails where OrderID in (select OrderID from Orders where CustomerID = 1);
delete from Orders where CustomerID = 1;

insert into Products (ProductName, Description, Price) 
values ('Wireless Earbuds', 'Bluetooth 5.0 Earbuds with Noise Cancellation', 2999.00);

alter table Orders add column Status varchar(20) default 'Pending';

update Orders
set Status = 'Shipped'
where OrderID = 2;

alter table Customers add column OrderCount int default 0;
SELECT o.OrderID, o.OrderDate, c.FirstName, c.LastName, o.Status
FROM Orders o
JOIN Customers c ON o.CustomerID = c.CustomerID
WHERE o.OrderID = 5;

update Customers c
set OrderCount = (
    select count(*)
    from Orders o
    where o.CustomerID = c.CustomerID
);

select o.OrderID, o.OrderDate, c.FirstName, c.LastName, c.Email, c.Phone
from Orders o
join Customers c on o.CustomerID = c.CustomerID;

select p.ProductName, sum(od.Quantity * p.Price) as TotalRevenue
from OrderDetails od
join Products p on od.ProductID = p.ProductID
group by p.ProductName
order by TotalRevenue desc;

create table Categories (
    CategoryID int primary key auto_increment,
    CategoryName varchar(100) not null
);

alter table Products add column CategoryID int;
alter table Products add foreign key (CategoryID) references Categories(CategoryID);

insert into Categories (CategoryName) values
('Smartphones'),
('Laptops'),
('Tablets'),
('Wearables'),
('Audio Devices'),
('Accessories');

update Products set CategoryID = 1 where ProductName = 'Smartphone';
update Products set CategoryID = 2 where ProductName = 'Laptop';
update Products set CategoryID = 3 where ProductName = 'Tablet';
update Products set CategoryID = 4 where ProductName = 'Smartwatch';
update Products set CategoryID = 5 where ProductName = 'Headphones';
update Products set CategoryID = 6 where ProductName in ('Keyboard', 'Mouse', 'Monitor', 'Power Bank', 'Speaker');

select p.ProductName, sum(od.Quantity) as TotalQuantityOrdered
from OrderDetails od
join Products p on od.ProductID = p.ProductID
group by p.ProductName
order by TotalQuantityOrdered desc
limit 1;

select sum(TotalAmount) as TotalRevenue
from Orders
where OrderDate between '2025-01-01' and '2025-03-17';


--  find customers who have not placed any orders
select c.FirstName, c.LastName, c.Email 
from Customers c
where c.CustomerID not in (select distinct CustomerID from Orders);

--  find the total number of products available for sale
select count(*) as TotalProducts from Products;

--  calculate the total revenue generated by TechShop
select sum(TotalAmount) as TotalRevenue from Orders;

-- calculate the average quantity ordered for products in a specific category
select avg(od.Quantity) as avg_quantity_ordered
from OrderDetails od
join Products p on od.ProductID = p.ProductID
join Categories c on p.CategoryID = c.CategoryID
where c.CategoryName = 'YourCategoryName';


--  calculate the total revenue generated by a specific customer
select sum(o.TotalAmount) as CustomerTotalRevenue
from Orders o
where o.CustomerID = 3;

--  find the customers who have placed the most orders
select c.FirstName, c.LastName, count(o.OrderID) as OrderCount
from Customers c
join Orders o on c.CustomerID = o.CustomerID
group by c.CustomerID
order by OrderCount desc
limit 1;

--  find the most popular product category (highest total quantity ordered)
select c.CategoryName, sum(od.Quantity) as TotalQuantityOrdered
from OrderDetails od
join Products p on od.ProductID = p.ProductID
join Categories c on p.CategoryID = c.CategoryID
group by c.CategoryName
order by TotalQuantityOrdered desc
limit 1;

--  find the customer who has spent the most money on electronic gadgets
select c.FirstName, c.LastName, sum(o.TotalAmount) as TotalSpent
from Customers c
join Orders o on c.CustomerID = o.CustomerID
where c.CustomerID in (
    select distinct o.CustomerID
    from Orders o
    join OrderDetails od on o.OrderID = od.OrderID
    join Products p on od.ProductID = p.ProductID
    join Categories cat on p.CategoryID = cat.CategoryID
    where cat.CategoryName = 'Electronics'
)
group by c.CustomerID
order by TotalSpent desc
limit 1;
CREATE TABLE Payments (
    PaymentID INT PRIMARY KEY AUTO_INCREMENT,
    OrderID INT NOT NULL,
    PaymentMethod VARCHAR(50) NOT NULL CHECK (PaymentMethod IN ('Credit Card', 'Debit Card', 'UPI', 'Net Banking', 'Cash')),
    Amount DECIMAL(10,2) NOT NULL CHECK (Amount > 0),
    PaymentDate DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (OrderID) REFERENCES Orders(OrderID) ON DELETE CASCADE
);

-- calculate the average order value (total revenue / total orders)
select avg(TotalAmount) as AverageOrderValue from Orders;

-- find the total number of orders placed by each customer
select c.FirstName, c.LastName, count(o.OrderID) as OrderCount
from Customers c
left join Orders o on c.CustomerID = o.CustomerID
group by c.CustomerID;
