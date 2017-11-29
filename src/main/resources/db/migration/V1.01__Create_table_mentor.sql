CREATE TABLE IF NOT EXISTS `mentor` (
	`login`	TEXT NOT NULL UNIQUE,
	`group_id` INTEGER,
	FOREIGN KEY (`login`) REFERENCES `user`(`login`) ON DELETE SET NULL,
	FOREIGN KEY (`group_id`) REFERENCES `group`(`id`) ON DELETE SET NULL
);
INSERT INTO `mentor` VALUES ('mentor', 1);
