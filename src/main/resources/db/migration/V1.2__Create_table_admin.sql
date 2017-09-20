CREATE TABLE IF NOT EXISTS `admin` (
	`login`	TEXT PRIMARY KEY UNIQUE NOT NULL,
	`password`	TEXT NOT NULL, 
	`email`	TEXT NOT NULL,
	`name`	TEXT NOT NULL,
	`surname`	TEXT NOT NULL
);
INSERT INTO `admin` VALUES ('admin','admin','admin@admin.com','Jerzy','Mardaus');

