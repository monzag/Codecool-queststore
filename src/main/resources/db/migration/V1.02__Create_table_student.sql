CREATE TABLE IF NOT EXISTS `student` (
	`login`	TEXT NOT NULL UNIQUE,
	`group_id`	INTEGER,
	`team_tag`	INTEGER NOT NULL,
	`balance` INTEGER NOT NULL,
	FOREIGN KEY (`login`) REFERENCES `user`(`login`) ON DELETE SET NULL,
	FOREIGN KEY (`group_id`) REFERENCES `group`(`id`) ON DELETE SET NULL
);

INSERT INTO `student` VALUES ('student', 1, 'Guwniaks','0');

