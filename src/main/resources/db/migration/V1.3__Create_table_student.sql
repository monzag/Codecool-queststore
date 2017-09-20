CREATE TABLE IF NOT EXISTS `student` (
	`login`	TEXT PRIMARY KEY UNIQUE NOT NULL,
	`password`	TEXT NOT NULL,
	`email`	TEXT NOT NULL,
	`name`	TEXT NOT NULL,
	`surname`	TEXT NOT NULL,
	`class`	TEXT NOT NULL,
	`team`	TEXT NOT NULL
);
INSERT INTO `student` VALUES ('anna','nowak','anna@gmail.com','Anna','Nowak','KRK.2017.1','A1');
INSERT INTO `student` VALUES ('jan','kowalski','jan@gmail.com','Jan','Kowalski','KRK.2017.2','A2');

