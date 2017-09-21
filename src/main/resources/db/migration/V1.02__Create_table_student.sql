CREATE TABLE IF NOT EXISTS `student` (
	`login`	TEXT UNIQUE NOT NULL,
	`class`	TEXT NOT NULL,
	`team`	TEXT NOT NULL,
	`balance` INTEGER NOT NULL,
	`coolcoins` INTEGER NOT NULL,
	FOREIGN KEY (`login`) REFERENCES `user`(`login`) ON DELETE SET NULL
);

