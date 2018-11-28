BEGIN TRANSACTION;
DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
	`username`	TEXT NOT NULL UNIQUE,
	`firstname`	TEXT NOT NULL,
	`lastname`	TEXT NOT NULL,
	`address`	TEXT NOT NULL,
	`phoneNumber`	TEXT NOT NULL,
	`profileImage`	TEXT NOT NULL,
	`balance`	REAL NOT NULL,
	PRIMARY KEY(`username`)
);
DROP TABLE IF EXISTS `transactions`;
CREATE TABLE IF NOT EXISTS `transactions` (
	`id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	`username`	TEXT NOT NULL,
	`resourceId`	INTEGER NOT NULL,
	`copyId`	INTEGER NOT NULL,
	`dateTaken`	TEXT NOT NULL,
	`dueDate`	TEXT,
	`status`	INTEGER NOT NULL
);
DROP TABLE IF EXISTS `librarians`;
CREATE TABLE IF NOT EXISTS `librarians` (
	`username`	TEXT NOT NULL UNIQUE,
	`firstname`	TEXT NOT NULL,
	`lastname`	TEXT NOT NULL,
	`address`	TEXT NOT NULL,
	`phoneNumber`	TEXT,
	`profileImage`	TEXT NOT NULL,
	`balance`	REAL NOT NULL,
	`staffNumber`	TEXT NOT NULL UNIQUE,
	`employmentDate`	TEXT NOT NULL,
	PRIMARY KEY(`username`)
);
INSERT INTO `librarians` (username,firstname,lastname,address,phoneNumber,profileImage,balance,staffNumber,employmentDate) VALUES ('ashaibani','Mohamed','Ashaibani','89 Hanover Street;SA16BQ;Swansea;United Kingdom','07432092995','1.png',0.0,'teststaffnumber','2018-11-22');
DROP TABLE IF EXISTS `laptops`;
CREATE TABLE IF NOT EXISTS `laptops` (
	`id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	`title`	TEXT NOT NULL,
	`year`	INTEGER NOT NULL,
	`model`	TEXT NOT NULL,
	`os`	TEXT NOT NULL,
	`manufacturer`	TEXT NOT NULL,
	`thumbnail`	TEXT NOT NULL,
	`copies`	TEXT NOT NULL,
	`reserveQueue`	TEXT NOT NULL
);
INSERT INTO `laptops` (id,title,year,model,os,manufacturer,thumbnail,copies,reserveQueue) VALUES (10001,'test laptop',2018,'test model','test os','test manufacturer','3.png','1-1-0@2-7-0@3-14-0@','testuser 1;test user 2;');
DROP TABLE IF EXISTS `dvds`;
CREATE TABLE IF NOT EXISTS `dvds` (
	`id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	`title`	TEXT NOT NULL,
	`year`	INTEGER NOT NULL,
	`director`	TEXT NOT NULL,
	`runtime`	INTEGER NOT NULL,
	`language`	TEXT NOT NULL,
	`subtitles`	TEXT NOT NULL,
	`thumbnail`	TEXT NOT NULL,
	`copies`	TEXT NOT NULL,
	`reserveQueue`	TEXT NOT NULL
);
INSERT INTO `dvds` (id,title,year,director,runtime,language,subtitles,thumbnail,copies,reserveQueue) VALUES (5000,'test dvd',2018,'test director',200,'English','testsubtitle3;testsubtitle2;','1.png','1-1-0@2-7-0@3-14-0@','testuser 1;test user 2;');
DROP TABLE IF EXISTS `books`;
CREATE TABLE IF NOT EXISTS `books` (
	`id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
	`title`	TEXT NOT NULL,
	`year`	INTEGER NOT NULL,
	`author`	TEXT NOT NULL,
	`genre`	TEXT NOT NULL,
	`isbn`	TEXT NOT NULL,
	`language`	TEXT NOT NULL,
	`publisher`	TEXT NOT NULL,
	`thumbnail`	TEXT NOT NULL,
	`copies`	TEXT NOT NULL,
	`reserveQueue`	TEXT NOT NULL
);
INSERT INTO `books` (id,title,year,author,genre,isbn,language,publisher,thumbnail,copies,reserveQueue) VALUES (1,'test book',2018,'test author','test genre','testisbn','English','test publisher','2.png','1-1-0@2-7-0@3-14-0@','testuser 1;test user 2;');
COMMIT;
