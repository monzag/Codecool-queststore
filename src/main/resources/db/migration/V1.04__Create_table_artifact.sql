CREATE TABLE IF NOT EXISTS `artifact` (
	`name`	TEXT NOT NULL UNIQUE,
	`price`	INTEGER NOT NULL,
	`type`	TEXT NOT NULL,
	`description`	TEXT NOT NULL,
	PRIMARY KEY(`name`)
);

CREATE TABLE IF NOT EXISTS `owned_artifact` (
	`id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
	`artifact_name`	TEXT,
	`get_date`	TEXT NOT NULL,
	`use_date`	TEXT,
	FOREIGN KEY(`artifact_name`) REFERENCES `artifact`(`name`) ON DELETE SET NULL
);
