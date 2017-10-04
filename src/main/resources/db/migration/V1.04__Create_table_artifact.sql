CREATE TABLE IF NOT EXISTS `artifact` (
	`name`	TEXT NOT NULL UNIQUE,
	`price`	INTEGER NOT NULL,
	`description`	TEXT NOT NULL,
	PRIMARY KEY(`name`)
);

CREATE TABLE IF NOT EXISTS `owned_artifact` (
  `artifact_name`	TEXT,
  `owner_name`	TEXT,
	FOREIGN KEY(`artifact_name`) REFERENCES `artifact`(`name`) ON DELETE SET NULL,
  FOREIGN KEY(`owner_name`) REFERENCES `student`(`name`) ON DELETE SET NULL
);
