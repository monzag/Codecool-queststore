CREATE TABLE IF NOT EXISTS `mentor` (
	`login`	TEXT NOT NULL UNIQUE,
	`group_tag` TEXT NOT NULL,
	FOREIGN KEY (`group_tag`) REFERENCES `group`(`group_tag`) ON DELETE SET NULL
);
INSERT INTO `mentor` VALUES ('mentor','2017.1');
