CREATE TABLE IF NOT EXISTS `team` (
	`id` INTEGER PRIMARY KEY AUTOINCREMENT,
	`name` TEXT
);

INSERT INTO `team` VALUES (null, 'Zgrywusy');

CREATE TABLE IF NOT EXISTS `student` (
	`login`	TEXT NOT NULL UNIQUE,
	`group_id`	INTEGER,
	`team`	INTEGER,
	`balance` INTEGER NOT NULL,
	FOREIGN KEY (`login`) REFERENCES `user`(`login`) ON DELETE SET NULL,
	FOREIGN KEY (`group_id`) REFERENCES `group`(`id`) ON DELETE SET NULL,
	FOREIGN KEY (`team`) REFERENCES `team`(`id`) ON DELETE SET NULL
);

INSERT INTO `student` VALUES ('student', 1, 1, '0');

