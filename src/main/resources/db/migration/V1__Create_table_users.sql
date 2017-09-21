CREATE TABLE IF NOT EXISTS `user` (
	`login`	TEXT PRIMARY KEY UNIQUE NOT NULL,
	`email`	TEXT NOT NULL,
	`name`	TEXT NOT NULL,
	`surname`	TEXT NOT NULL
);
INSERT INTO `user` VALUES ('admin', 'admin@admin.com','Jerzy','Mardaus');

