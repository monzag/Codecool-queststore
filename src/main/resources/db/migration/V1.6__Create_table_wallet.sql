CREATE TABLE IF NOT EXISTS `wallet` (
    `student_login` TEXT NOT NULL,
	`coolcoins` INTEGER NOT NULL,
	`balance`	INTEGER NOT NULL,
	FOREIGN KEY(`student_login`) REFERENCES `student`(`login`) ON DELETE SET NULL
);
