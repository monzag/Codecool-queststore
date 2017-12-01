CREATE TABLE IF NOT EXISTS `level` (
	`name`	TEXT NOT NULL,
	`score`	INTEGER,
	PRIMARY KEY(`name`)
);
INSERT INTO `level` VALUES ('noob', 1000);
INSERT INTO `level` VALUES ('dupa', 3000);