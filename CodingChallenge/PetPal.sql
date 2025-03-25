create database petpals;
use petpals;

-- create table: pets
create table pets (
    petid int primary key,
    name varchar(255),
    age int,
    breed varchar(255),
    type varchar(100),
    availableforadoption bit
);


-- create table: shelters
create table shelters (
    shelterid int primary key,
    name varchar(255),
    location varchar(500)
);

-- create table: donations
create table donations (
    donationid int primary key,
    donorname varchar(255),
    donationtype varchar(100),
    donationamount decimal(10,2),
    donationitem varchar(255),
    donationdate datetime
);

-- create table: adoptionevents
create table adoptionevents (
    eventid int primary key,
    eventname varchar(255),
    eventdate datetime,
    location varchar(500)
);


-- create table: participants
create table participants (
    participantid int primary key,
    participantname varchar(255),
    participanttype varchar(100),
    eventid int,
    foreign key (eventid) references adoptionevents(eventid)
);

create table adoptions (
    adoptionid int primary key auto_increment,
    petid int,
    adoptername varchar(255),
    adoptiondate datetime,
    foreign key (petid) references pets(petid)
);

insert into adoptions (petid, adoptername, adoptiondate) values
(3, 'Arun Kumar', '2025-02-20 14:30:00'),
(5, 'Meera Iyer', '2025-02-22 10:15:00'),
(9, 'Vikram Singh', '2025-02-25 16:45:00');


-- insert data into pets
insert into pets values
(1, 'buddy', 3, 'labrador', 'dog', 1),
(2, 'whiskers', 2, 'persian', 'cat', 1),
(3, 'rocky', 4, 'beagle', 'dog', 0),
(4, 'misty', 1, 'siamese', 'cat', 1),
(5, 'charlie', 5, 'pug', 'dog', 0),
(6, 'bella', 2, 'golden retriever', 'dog', 1),
(7, 'tiger', 3, 'maine coon', 'cat', 1),
(8, 'max', 4, 'doberman', 'dog', 1),
(9, 'snowy', 2, 'british shorthair', 'cat', 0),
(10, 'duke', 3, 'bulldog', 'dog', 1);

-- insert data into shelters
insert into shelters values
(1, 'chennai pet care', 'chennai'),
(2, 'coimbatore rescue center', 'coimbatore'),
(3, 'madurai pet shelter', 'madurai'),
(4, 'trichy animal rescue', 'trichy'),
(5, 'salem stray home', 'salem'),
(6, 'vellore pet haven', 'vellore'),
(7, 'erode paws shelter', 'erode'),
(8, 'thanjavur pet aid', 'thanjavur'),
(9, 'tirunelveli pet rescue', 'tirunelveli'),
(10, 'pondicherry pet support', 'pondicherry');

-- insert data into donations
insert into donations values
(1, 'raman', 'cash', 5000.00, null, '2025-03-01 10:00:00'),
(2, 'suresh', 'item', null, 'dog food', '2025-03-02 11:00:00'),
(3, 'priya', 'cash', 3000.00, null, '2025-03-03 12:00:00'),
(4, 'ganesh', 'cash', 7000.00, null, '2025-03-04 13:00:00'),
(5, 'lakshmi', 'item', null, 'cat litter', '2025-03-05 14:00:00'),
(6, 'bharath', 'cash', 4500.00, null, '2025-03-06 15:00:00'),
(7, 'revathi', 'item', null, 'dog toys', '2025-03-07 16:00:00'),
(8, 'karuna', 'cash', 8000.00, null, '2025-03-08 17:00:00'),
(9, 'murali', 'item', null, 'pet beds', '2025-03-09 18:00:00'),
(10, 'keerthi', 'cash', 6500.00, null, '2025-03-10 19:00:00');

-- insert data into adoptionevents
insert into adoptionevents values
(1, 'chennai adoption drive', '2025-04-01 10:00:00', 'chennai'),
(2, 'coimbatore pet fair', '2025-05-15 12:00:00', 'coimbatore');

-- insert data into participants
insert into participants values
(1, 'chennai pet care', 'shelter', 1),
(2, 'priya', 'adopter', 1),
(3, 'coimbatore rescue center', 'shelter', 2),
(4, 'ganesh', 'adopter', 2);

-- query: retrieve available pets
select name, age, breed, type from pets where availableforadoption = 1;

-- query: retrieve participants for a specific event
select p.participantname, p.participanttype
from participants p
join adoptionevents a on p.eventid = a.eventid
where p.eventid = 1;

-- stored procedure to update shelter info
delimiter //
create procedure update_shelter_info(
    in shelter_id int,
    in new_name varchar(255),
    in new_location varchar(500)
)
begin
    update shelters
    set name = new_name, location = new_location
    where shelterid = shelter_id;
end //
delimiter ;

-- query: total donation per shelter
select s.name, coalesce(sum(d.donationamount), 0) as total_donation
from shelters s
left join donations d on s.shelterid = d.donationid
group by s.name;


alter table pets add column ownerid int null;
update pets set ownerid = 1 where petid in (3, 5, 9); 
update pets set ownerid = null where petid in (1, 2, 4, 6, 7, 8, 10);
 
-- query: pets without owners
select name, age, breed, type from pets where ownerid is null;

-- query: total donation per month-year
select 
    date_format(donationdate, '%M %Y') as month_year, 
    sum(donationamount) as total_donation
from donations
group by month_year
order by min(donationdate);

-- 11. retrieve a list of distinct breeds for all pets that are either aged between 1 and 3 years or older than 5 years.
select distinct breed from pets where (age between 1 and 3) or (age > 5);

-- 12. retrieve a list of pets and their respective shelters where the pets are currently available for adoption.
select p.name as pet_name, s.name as shelter_name 
from pets p 
join shelters s on p.petid = s.shelterid 
where p.availableforadoption = 1;

-- 13. find the total number of participants in events organized by shelters located in specific city.
select count(p.participantid) as total_participants 
from participants p 
join adoptionevents a on p.eventid = a.eventid 
join shelters s on p.participantname = s.name 
where s.location = 'chennai';

-- 14. retrieve a list of unique breeds for pets with ages between 1 and 5 years.
select distinct breed from pets where age between 1 and 5;

-- 15. find the pets that have not been adopted by selecting their information from the 'pets' table.
select name, age, breed, type from pets where ownerid is null;

-- 16. retrieve the names of all adopted pets along with the adopter's name from the 'adoption' and 'user' tables.
select p.name as pet_name, a.adoptername
from pets p
join adoptions a on p.petid = a.petid;


-- 17. retrieve a list of all shelters along with the count of pets currently available for adoption in each shelter.
select s.name as shelter_name, count(p.petid) as available_pets 
from shelters s 
left join pets p on s.shelterid = p.petid 
where p.availableforadoption = 1 
group by s.name;

-- 18. find pairs of pets from the same shelter that have the same breed.
select p1.name as pet1, p2.name as pet2, p1.breed, s.name as shelter_name
from pets p1 
join pets p2 on p1.breed = p2.breed and p1.petid < p2.petid
join shelters s on p1.petid = s.shelterid and p2.petid = s.shelterid;

-- 19. list all possible combinations of shelters and adoption events.
select s.name as shelter_name, a.eventname 
from shelters s 
cross join adoptionevents a;

-- 20. determine the shelter that has the highest number of adopted pets.
select s.name as shelter_name, count(p.petid) as adopted_pets 
from shelters s 
join pets p on s.shelterid = p.ownerid 
group by s.name 
order by adopted_pets desc 
limit 1;

