CREATE TABLE IF NOT EXISTS `student` (
	`login`	TEXT UNIQUE NOT NULL,
	`class`	TEXT NOT NULL,
	`team`	TEXT NOT NULL,
	`balance` INTEGER NOT NULL,
	`coolcoins` INTEGER NOT NULL,
	FOREIGN KEY (`login`) REFERENCES `user`(`login`) ON DELETE SET NULL
);
INSERT INTO `student` VALUES ('anna','KRK.2017.1','A1',0,0);
INSERT INTO `student` VALUES ('jan','KRK.2017.2','A2',0,0);

