create database ProjectManagement;
use ProjectManagement;

create table employee (
    id int auto_increment primary key,
    name varchar(255) not null,
    designation varchar(100),
    gender enum('Male', 'Female', 'Other'),
    salary decimal(10,2),
    project_id int,
    foreign key (project_id) references project(id) on delete set null
);

create table project (
    id int auto_increment primary key,
    project_name varchar(255) not null,
    description text,
    start_date date,
    status enum('started', 'dev', 'build', 'test', 'deployed') not null
);

create table task (
    task_id int auto_increment primary key,
    task_name varchar(255) not null,
    project_id int,
    employee_id int,
    status enum('Assigned', 'Started', 'Completed') not null,
    foreign key (project_id) references project(id) on delete cascade,
    foreign key (employee_id) references employee(id) on delete set null
);

insert into project (project_name, description, start_date, status) 
values 
('AI Development', 'Developing an AI chatbot', '2025-03-25', 'started'),
('E-commerce Website', 'Building a shopping website', '2025-04-10', 'dev'),
('Banking System', 'Developing a core banking app', '2025-05-15', 'build'),
('Healthcare Portal', 'Online medical consultation platform', '2025-06-01', 'test'),
('Inventory Management', 'Warehouse and stock tracking system', '2025-06-15', 'deployed'),
('Social Media App', 'New platform for content creators', '2025-07-01', 'started'),
('HR Management', 'Automating HR processes', '2025-07-15', 'dev'),
('Ride Sharing App', 'Cab booking and ride-sharing app', '2025-08-01', 'build'),
('Online Learning Platform', 'E-learning platform for students', '2025-08-20', 'test'),
('Task Management System', 'Task assignment and tracking system', '2025-09-10', 'deployed');

insert into employee (name, designation, gender, salary, project_id) 
values 
('Arun', 'Software Engineer', 'Male', 75000.00, 1),
('Vignesh', 'Project Manager', 'Male', 90000.00, 2),
('Sundar', 'Backend Developer', 'Male', 85000.00, 3),
('Murugesan', 'Frontend Developer', 'Male', 70000.00, 4),
('Kavitha', 'UI/UX Designer', 'Female', 65000.00, 5),
('Priya', 'HR Manager', 'Female', 72000.00, 6),
('Mohan', 'Data Scientist', 'Male', 80000.00, 7),
('Kumaresan', 'DevOps Engineer', 'Male', 88000.00, 8),
('Chandra', 'Database Administrator', 'Female', 77000.00, 9),
('Vishnu', 'Mobile App Developer', 'Male', 73000.00, 10);

insert into task (task_name, project_id, employee_id, status) 
values 
('Build AI Model', 1, 1, 'Assigned'),
('Design Shopping Cart', 2, 2, 'Started'),
('Implement Loan API', 3, 3, 'Completed'),
('Develop Video Consultation Feature', 4, 4, 'Assigned'),
('Inventory Tracking System', 5, 5, 'Started'),
('Create Social Media Feed', 6, 6, 'Completed'),
('Develop Payroll Module', 7, 7, 'Assigned'),
('Cab Booking Feature', 8, 8, 'Started'),
('Course Upload Feature', 9, 9, 'Completed'),
('Task Notification System', 10, 10, 'Assigned');
