CREATE TABLE IF NOT EXISTS `mentor` (
	`login`	TEXT NOT NULL UNIQUE,
	`groupTag` TEXT NOT NULL
);
INSERT INTO `mentor` VALUES ('mentor','2017.1');
