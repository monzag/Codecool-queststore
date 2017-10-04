CREATE TABLE IF NOT EXISTS `student` (
	`login`	TEXT NOT NULL UNIQUE,
	`group_tag`	TEXT NOT NULL,
	`teamtag`	INTEGER NOT NULL,
	`balance` INTEGER NOT NULL,
	FOREIGN KEY (`login`) REFERENCES `user`(`login`) ON DELETE SET NULL
);

INSERT INTO `student` VALUES ('student','2017.1');

