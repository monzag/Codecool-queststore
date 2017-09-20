CREATE TABLE IF NOT EXISTS `Artifact` (
	`name`	TEXT NOT NULL UNIQUE,
	`price`	INTEGER NOT NULL,
	`type`	TEXT NOT NULL,
	`description`	TEXT NOT NULL,
	PRIMARY KEY(`name`)
);

CREATE TABLE IF NOT EXISTS `Owned_Artifact` (
	`id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
	`artifact_name`	TEXT,
	`get_date`	TEXT NOT NULL,
	`use_date`	TEXT,
	FOREIGN KEY(`artifact_name`) REFERENCES `Artifact`(`name`) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS `Mentor` (
	`login`	TEXT NOT NULL UNIQUE,
	`password`	TEXT NOT NULL,
	`name`	TEXT NOT NULL,
	`surname`	TEXT NOT NULL,
	`email`	TEXT NOT NULL,
	`class_tag`	TEXT NOT NULL,
	PRIMARY KEY(`login`)
);

