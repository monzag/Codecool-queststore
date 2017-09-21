CREATE TABLE IF NOT EXISTS `users` (
	`login`	TEXT PRIMARY KEY UNIQUE NOT NULL,
	`email`	TEXT NOT NULL,
	`name`	TEXT NOT NULL,
	`surname`	TEXT NOT NULL
);
INSERT INTO `users` VALUES ('admin', 'admin@admin.com','Jerzy','Mardaus');

