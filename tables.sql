-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.0.67-community-nt


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema test
--

CREATE DATABASE IF NOT EXISTS test;
USE test;

--
-- Definition of table `flights`
--


DROP TABLE IF EXISTS `product`;
DROP TABLE IF EXISTS `customer`;
DROP TABLE IF EXISTS `shipments`;
DROP TABLE IF EXISTS `purchases`;

CREATE TABLE `product` (
  productcode int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `company` varchar(45) NOT NULL,
  `description` varchar(255) NOT NULL,
  `price` float NOT NULL,
  `quantity` float NOT NULL,
  PRIMARY KEY  (productcode)
);

insert into product (`name`,`company`,`description`,`price`,`quantity`)
values ('x12','XGODY','alex bought this phone on aliexpress',55,4)
,('x5','dodgee','failed cellphone bought on aliexpress by alex',33,3),
('medium fan','GOLEX','fan that make room windier',11,2),
('SilverLmap','toshiba','lamp that helps you see in the dark',22,1);

CREATE TABLE `customer` (
  `SSN` varchar(45) NOT NULL,
  `phone` varchar(45) NOT NULL,
  `adress` varchar(45) NOT NULL,
  `city` varchar(45) NOT NULL,
  `country` varchar(45) NOT NULL,
  PRIMARY KEY  (`SSN`)
);


insert into customer (`SSN`,`phone`,`adress`,`city`,`country`)
values ('111','04-8448903','kandisnki 9','kiryat ata','israel'),
('222','04-8448903','kandisnki 9','kiryat ata','israel'),
('333','04-8238312','haneviim 33','kiryat ata','israel'),
('444','09-2314872','davinci 8','eilat','israel');

CREATE TABLE `shipments` (
 productcode int NOT NULL,
 `quantity` varchar(45) NOT NULL,
 `duedate` varchar(45) NOT NULL,
 arived int NOT NULL default 0,
 `supllier` varchar(45) NOT NULL,
  PRIMARY KEY  (`productcode`,`duedate`,`supllier`)
);

insert into shipments (`productcode`,`quantity`,`duedate`,`arived`,supllier)
values (1,1,'09/02/1999 16:40',0,'gosha'),
(2,2,'05/02/2017 11:40',1,'david'),
(3,3,'09/02/2018 14:40',1,'god'),
(4,4,'02/02/2018 14:40',0,'goat');

CREATE TABLE `purchases` (
  productcode int NOT NULL,
 `ssn` varchar(45) NOT NULL,
 `date` varchar (45),
 `paypalnumber` varchar(45),
  PRIMARY KEY  (ssn,`productcode`,`date`)
);

insert into purchases (`productcode`,`ssn`,`date`,`paypalnumber`)
values (1,'111','09/02/1999 16:40',0),
(2,'222','05/02/2017 11:40',1),
(3,'333','09/02/2018 14:40',1),
(4,'444','02/02/2018 14:40',0);



