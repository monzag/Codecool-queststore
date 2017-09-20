CREATE TABLE IF NOT EXISTS `Mentor` (
	`login`	TEXT NOT NULL UNIQUE,
	`password`	TEXT NOT NULL,
	`name`	TEXT NOT NULL,
	`surname`	TEXT NOT NULL,
	`email`	TEXT NOT NULL,
	`class_tag`	TEXT NOT NULL,
	PRIMARY KEY(`login`)
);
